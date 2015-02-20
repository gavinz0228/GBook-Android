package com.gavinz.gbook;
import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.widget.*;
import android.app.AlertDialog;
import android.support.v7.app.ActionBar;
//import android.util.Log;
import android.view.*;

import com.gavin.gbook.R;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View.OnClickListener;
import java.util.*;
public class BookPage extends ActionBarActivity {
	public int currentChapter=1;
	public int currentSection=1;
	public Map<String,Integer> pageMap=new HashMap<String,Integer>();
	private ScrollView pagesv;
	public static HashMap<Integer,BookPageFragment> viewPagerMap=new HashMap<Integer,BookPageFragment>(); 
	private int y=0;//it's used when a activity is started
	public static int currentY=0;//when a book mark is being added
	MyPageAdapter pageAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookpage);
		//pagesv=(ScrollView)findViewById(R.id.pagesv);
		Bundle params=getIntent().getExtras();
		
		if(params !=null)
		{
			String ch=params.getString("chapter");
			String se=params.getString("section");
			String yValue=params.getString("y");
			if(ch!=null)
			{
				currentChapter=Integer.valueOf(ch);
				//Log.w("Received",ch);
			}
			if(se!=null)
			{
				currentSection=Integer.valueOf(se);
				//Log.w("Received",se);
			}
			if(yValue!=null)
			{
				y=Integer.valueOf(yValue);

			}
		}
		List<Fragment> fragments = getFragments(currentChapter,currentSection);
		int pageid=pageMap.get(Integer.valueOf(currentChapter).toString()+"."+Integer.valueOf(currentSection).toString());
		pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
		ViewPager pager =(ViewPager)findViewById(R.id.bookpagevp);
		pager.setAdapter(pageAdapter);
		pager.setOffscreenPageLimit(10);
		//Toast.makeText(this,Integer.valueOf(currentChapter).toString()+"."+Integer.valueOf(currentSection).toString() , Toast.LENGTH_LONG).show();

		pager.setCurrentItem(pageid);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
		    public void onPageScrollStateChanged(int state) {}
		    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

		    public void onPageSelected(int position) {
		        BookPageFragment f=(BookPageFragment) pageAdapter.getItem(position);
		        setActionBar(Integer.valueOf(f.chapter).toString()+"."+PageRepository.getChapterName(BookPage.this, Integer.valueOf(f.chapter)));
		        BookPage.this.currentSection=Integer.valueOf(f.section);
		        BookPage.this.currentChapter=Integer.valueOf(f.chapter);
		        //BookPage.this.y=Integer.valueOf(f.y);
		        
		    }
		});
		
		setActionBar(Integer.valueOf(currentChapter).toString()+"."+PageRepository.getChapterName(this, Integer.valueOf(currentChapter)));
		//BookPageFragment bpf =(BookPageFragment)getSupportFragmentManager().findFragmentById(R.id.bookpagevp);
		//Fragment bpf = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.bookpagevp + ":" + pager.getCurrentItem());
		//BookPageFragment bpf=(BookPageFragment)pageAdapter.instantiateItem(pager, pager.getCurrentItem());
		//BookPageFragment bpf=(BookPageFragment)pageAdapter.getItem(pager.getCurrentItem());
		BookPageFragment bpf=getFragmentByPosition(pager.getCurrentItem());
		if (bpf==null)
			Toast.makeText(getApplicationContext(),"failed to get the fragment", Toast.LENGTH_SHORT).show();
		//bpf.setY(y);
		/*
		Page page=PageRepository.getPage(getApplicationContext(), currentChapter, currentSection);
		if (page!=null)
		{	
			setPage(page);
		}
		else
		{
			Toast.makeText(getApplicationContext(), R.string.nobook, Toast.LENGTH_SHORT).show();
			finish();
		}
		
		//if it's from a bookmark
		if(y!=null)
			setLocation(Integer.valueOf(y));
		
		
		Button nextbt=(Button)findViewById(R.id.nextbt);
		nextbt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Page page=PageRepository.getNextPage(arg0.getContext(), currentChapter, currentSection);
				if (page!=null)
					setPage(page);
				else
					Toast.makeText(getApplicationContext(), R.string.endofbook, Toast.LENGTH_SHORT).show();
			}
			
		});
		Button prebt=(Button)findViewById(R.id.prebt);
		prebt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Page page=PageRepository.getPrePage(arg0.getContext(), currentChapter, currentSection);
				if (page!=null)
					setPage(page);
				else
					Toast.makeText(getApplicationContext(), R.string.startofbook, Toast.LENGTH_SHORT).show();
			}
			
		});
		*/
		/*
		BookPageFragment bpf=(BookPageFragment)pageAdapter.getItem(pageid);
		if(bpf==null)
		{
			Toast.makeText(this, "Scrollview is null", Toast.LENGTH_LONG).show();
		}
		*/
		//bpf.setY(50);
		// show the ads
		Utility.showAds(this);
		/*
		 			<include
			    android:layout_width="match_parent"
			    android:layout_height="wrap_content"
			    layout="@layout/adslayout"
			    android:id="@+id/bookpageads"
			     />
		 */
				
	}
	public List<Fragment> getFragments(int startChapter,int startSection)
	{
		List<Fragment> list=new ArrayList<Fragment>();
		int n=0;
		Page page=PageRepository.getPage(this,1,1);
		while (page!=null)
		{
			//set y value for the target startpage
			BookPageFragment bpf;
			if(page.chapter==startChapter&&page.section==startSection)
				bpf=BookPageFragment.NewInstance(Integer.valueOf(page.chapter).toString(),Integer.valueOf(page.section).toString(),page.title,page.content,Integer.valueOf(y).toString());
			else
				bpf=BookPageFragment.NewInstance(Integer.valueOf(page.chapter).toString(),Integer.valueOf(page.section).toString(),page.title,page.content);
			list.add(bpf);
			viewPagerMap.put(n, bpf);
			pageMap.put(Integer.valueOf(page.chapter).toString()+"."+Integer.valueOf(page.section).toString(), Integer.valueOf(n));

			page=PageRepository.getNextPage(this, page.chapter, page.section);
			n++;
		}
		return list;
		
	}
	public BookPageFragment getFragmentByPosition(int id)
	{
		return viewPagerMap.get(id);
	}
	//inflate the menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{		
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mainmenu, menu);
	    return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()==R.id.bookmarkmi)
        {
			Intent intent=new Intent(getApplicationContext(),BookMarkList.class);
			startActivityForResult(intent,Utility.BookmarkRequestCode);
        }
        else if(item.getItemId()==R.id.addbookmarkmi)
        {
        	AlertDialog.Builder ad=new AlertDialog.Builder(this);
        	final EditText et=new EditText(this);
        	et.setHint(R.string.addbookmarkhint);
        	ad.setView(et);
        	ad.setTitle(R.string.addabookmark);
        	ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int whichButton) {
        		  String value = et.getText().toString();
        		  if(value=="")
        			  return;
        		  BookMark bm=new BookMark();
        		  bm.title=value;
        		  bm.chapter=BookPage.this.currentChapter;
        		  bm.section=BookPage.this.currentSection;
        		  bm.y=BookPage.this.currentY;
        		  PageRepository.addABookmark(BookPage.this, bm);
        		  Toast.makeText(BookPage.this, R.string.bookmarkadded, Toast.LENGTH_SHORT).show();
        		  // Do something with value!
        		  }
        		});

        		ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		  public void onClick(DialogInterface dialog, int whichButton) {
        		    // Canceled.
        		  }
        		});
        	ad.show();
        	
        }
        else
        {
        	System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }
 
    /*
	public void setPage(Page page)
	{
		TextView textView=(TextView)findViewById(R.id.pagecontent);
		TextView sectiontitle=(TextView)findViewById(R.id.sectiontitle);
		//sectiontitle.setText(Integer.valueOf(page.chapter).toString()+"."+Integer.valueOf(page.section).toString()+" "+page.title);
		setActionBar(Integer.valueOf(page.chapter).toString()+"."+Integer.valueOf(page.section).toString()+" "+page.title);
		textView.setText(page.content);
		this.currentChapter=page.chapter;
		this.currentSection=page.section;
	}
	*/ 

	
	public void setActionBar(String title)
	{
		android.support.v7.app.ActionBar ab=this.getSupportActionBar();
		//ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbg));
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#25E6E6")));
		ab.setTitle(title);

	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == Utility.BookmarkRequestCode) {
	        if(resultCode == RESULT_OK){
	            Intent intent=new Intent(this,BookPage.class);
	            intent.putExtra("chapter", data.getStringExtra("chapter"));
	            intent.putExtra("section", data.getStringExtra("section"));
	            intent.putExtra("y", data.getStringExtra("y"));
	            startActivity(intent);
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	}
}
