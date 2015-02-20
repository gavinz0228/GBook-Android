package com.gavinz.gbook;

import com.gavin.gbook.R;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class BookPageFragment extends Fragment implements ScrollViewListener {
	public static String CHAPTER="chapter";
	public static String SECTION="section";
	public String title;
	public String chapter;
	public String section;
	public ScrollViewExt scrollView;
	public int y=0;
	public final static BookPageFragment NewInstance(String chapter,String section,String title,String content)
	{
		Bundle bundle=new Bundle();
		bundle.putString("chapter", chapter);
		bundle.putString("section", section);
		bundle.putString("title", title);
		bundle.putString("content", content);
		BookPageFragment bgf=new BookPageFragment();
		bgf.setArguments(bundle);
		return bgf;
	}
	public final static BookPageFragment NewInstance(String chapter,String section,String title,String content,String y)
	{
		Bundle bundle=new Bundle();
		bundle.putString("chapter", chapter);
		bundle.putString("section", section);
		bundle.putString("title", title);
		bundle.putString("content", content);
		bundle.putString("y", y);
		BookPageFragment bgf=new BookPageFragment();
		bgf.setArguments(bundle);
		return bgf;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{

		super.onCreateView(inflater, container, savedInstanceState);
		title=getArguments().getString("title");
		chapter=getArguments().getString("chapter");
		section=getArguments().getString("section");
		View v=inflater.inflate(R.layout.bookpagefragment, container, false);
		LinearLayout pll=(LinearLayout) v.findViewById(R.id.bookpagerll);
		TextView titleView=new TextView(getActivity());
		titleView.setGravity(Gravity.CENTER_HORIZONTAL);
		titleView.setText(chapter+"."+section+" "+title);
		titleView.setTextSize(23);
		titleView.setTypeface(null, Typeface.BOLD);
		TextView tv=new TextView(getActivity());
		tv.setTextSize(18);
		tv.setText(getArguments().getString("content"));

		//scrollView=(ScrollView)v.findViewById(R.id.bookpagesv);
		//y=Integer.valueOf((int)sv.getScaleY()).toString();
		pll.addView(titleView);
		pll.addView(tv);
		String yValue=getArguments().getString("y");
		scrollView=(ScrollViewExt)v.findViewById(R.id.bookpagesv);
		
		if(yValue!=null)
		{
			this.y=Integer.valueOf(yValue);
			scrollView.postDelayed(new Runnable() {
			    @Override
			    public void run() {
			        scrollView.scrollTo(0,BookPageFragment.this.y);
			    } 
			},250);
		}
		scrollView.setScrollViewListener(this);
		//Utility.showAds(v);
		return v;
	}
	@Override
	public void onScrollChanged(ScrollViewExt scrollView, int x, int y,
			int oldx, int oldy) {
	    View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
	    int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

	    // if diff is zero, then the bottom has been reached
	    if (diff == 0) {
	        // do stuff
	    	BookPage.currentY=y;
	    }
		
	}
	

	/*
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		//scrollView=(ScrollView)getView().findViewById(R.id.bookpagesv);
		scrollView=(ScrollView)this.view.findViewById(R.id.bookpagesv);
		if(scrollView==null)
		{
			Toast.makeText(this.getActivity(), "Scrollview is null", Toast.LENGTH_LONG).show();
		}
		scrollView.postDelayed(new Runnable() {
		    @Override
		    public void run() {
		        scrollView.scrollTo(0,50);
		    } 
		},1000);
	}
	*/


}
