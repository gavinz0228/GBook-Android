package com.gavinz.gbook;
import java.util.ArrayList;

import com.gavin.gbook.R;

import android.util.Log;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import android.annotation.SuppressLint;
import android.app.*;
import android.graphics.Color;
import android.os.*;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.content.Intent;

public class BookOutline extends Activity {
	String ident="••••••";
	@SuppressLint("ResourceAsColor")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.outline);
		LinearLayout mainll=(LinearLayout)findViewById(R.id.mainll);

		Chapter[] chapters=PageRepository.getAllChapterInfo(getApplicationContext());
		if(chapters!=null&&chapters.length>0)
		{
			for(int i=0;i<chapters.length;i++)
			{
				TextView tv=new TextView(getApplicationContext());
				tv.setTextColor(Color.BLUE);
				tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				tv.setTextSize(25);
				tv.setText(Integer.valueOf(chapters[i].id) +" -  "+chapters[i].title);
				ArrayList<Integer> clist=new ArrayList<Integer>();
				clist.add(Integer.valueOf(i+1));
				tv.setTag(clist);
				tv.setOnClickListener(new OnClickListener(){
					public void onClick(View arg)
					{
						@SuppressWarnings("unchecked")
						ArrayList<Integer> list=(ArrayList<Integer>)arg.getTag();
						Intent intent=new Intent(getApplicationContext(),BookPage.class);
						intent.putExtra("chapter", list.get(0).toString());
						startActivity(intent);
						
					}
				});
				mainll.addView(tv);
				if(chapters[i].sections!=null)
					for(int j=0;j<chapters[i].sections.length;j++)
					{

						TextView stv=new TextView(getApplicationContext());
						stv.setTextColor(Color.parseColor("#CC00CC"));
						stv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
						stv.setTextSize(20);
						stv.setText(ident+Integer.valueOf(chapters[i].id)+"."+Integer.valueOf(chapters[i].sections[j].id)+" -  "+chapters[i].sections[j].title);
						ArrayList<Integer> slist=new ArrayList<Integer>();
						slist.add(Integer.valueOf(i+1));
						slist.add(Integer.valueOf(j+1));
						stv.setTag(slist);
						stv.setOnClickListener(new OnClickListener(){
							public void onClick(View arg)
							{
								@SuppressWarnings("unchecked")
								ArrayList<Integer> list=(ArrayList<Integer>)arg.getTag();
								Intent intent=new Intent(getApplicationContext(),BookPage.class);
								intent.putExtra("chapter", list.get(0).toString());
								intent.putExtra("section", list.get(1).toString());
								//Log.w("c",list.get(0).toString());
								//Log.w("s",list.get(1).toString());
								startActivity(intent);
								
							}
						});
						mainll.addView(stv);
					}
			}
		}
		//show ads
		Utility.showAds(this);
		
	}
}
