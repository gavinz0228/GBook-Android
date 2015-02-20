package com.gavinz.gbook;
import com.gavin.gbook.R;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.*;
import android.view.View.*;
//import android.content.DialogInterface;
//import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;

public class BookMarkList extends Activity {
	public BookMark operatingBookmark;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmarkdialog);
		//this.setTitle("Bookmarks");
		loadList();
		// TODO Auto-generated constructor stub
	}
	public void loadList()
	{
		TableLayout tv=(TableLayout)findViewById(R.id.bookmarktl);
		tv.removeAllViews();
		BookMark[] bookMark=PageRepository.getAllBookMarks(getApplicationContext());
		if(bookMark==null)
		{
			TableRow tr=new TableRow(getApplicationContext());
			TextView tView=new TextView(getApplicationContext());
			tView.setText("No bookmark created yet.");
			tr.addView(tView);
			tv.addView(tr);
		}
		else
		{
			for(int i=0;i<bookMark.length;i++)
			{
				TableRow row=new TableRow(getApplicationContext());
				//tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				TextView tView=new TextView(getApplicationContext());
				tView.setText(bookMark[i].title);
				tView.setTextSize(22);
				tView.setHeight(Utility.getPixelByDP(this, 35));
				tView.setTextColor(Color.parseColor("#072938"));
				tView.setTag(bookMark[i]);
				tView.setOnClickListener(new android.view.View.OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						BookMark bm=(BookMark)v.getTag();
						Intent intent=new Intent(BookMarkList.this,BookPage.class);
						intent.putExtra("chapter", Integer.valueOf(bm.chapter).toString());
						intent.putExtra("section", Integer.valueOf(bm.section).toString());
						intent.putExtra("y", Integer.valueOf(bm.y).toString());
						//Log.w("c",list.get(0).toString());
						//Log.w("s",list.get(1).toString());
						//Toast.makeText(BookMarkList.this, Integer.valueOf(bm.chapter).toString()+Integer.valueOf(bm.section).toString()+Integer.valueOf(bm.y).toString(), Toast.LENGTH_LONG).show();
						//BookMarkList.this.startActivity(intent);
						setResult(RESULT_OK,intent);
						finish();
					}
					
					
				});
				tView.setOnLongClickListener(new OnLongClickListener(){

					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						AlertDialog.Builder ad=new AlertDialog.Builder(BookMarkList.this);
						ad.setTitle(R.string.askdeletebookmark);
						BookMarkList.this.operatingBookmark=(BookMark)v.getTag();
						ad.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int whichButton)
							{
								BookMark bm=BookMarkList.this.operatingBookmark;
								PageRepository.deleteBookmark(BookMarkList.this, bm);
								Toast.makeText(BookMarkList.this, R.string.bookmarkdeleted, Toast.LENGTH_LONG).show();
								BookMarkList.this.loadList();
								
							}
						});
						ad.setNegativeButton("No", new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int whichButton)
							{
								
							}
						});
						ad.show();
						return true;
					}});	
			
				row.addView(tView);
				//if(tv==null)
				//	Toast.makeText(context, "tv nothing...", Toast.LENGTH_LONG).show();
				//if(row==null)
				//	Toast.makeText(context, "tr nothing...", Toast.LENGTH_LONG).show();
				tv.addView(row,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			}
			
		}
	}

}
