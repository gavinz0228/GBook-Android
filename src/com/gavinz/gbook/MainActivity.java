package com.gavinz.gbook;
import android.view.*;
import android.app.*;
import android.widget.*;
import com.gavin.gbook.R;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View.OnClickListener;
import com.google.android.gms.ads.*;
public class MainActivity extends Activity {
	//ads

	private static final String AD_UNIT_ID = "ca-app-pub-8539131517427924/3225601292";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView titleView=(TextView)findViewById(R.id.booktitle);
		//display title
		titleView.setText(BookInfo.getTitle(getApplicationContext()));
		
		Button aboutbt=(Button)findViewById(R.id.aboutbt);
		aboutbt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) 
			{
				Dialog aboutdg=new Dialog(v.getContext());
				aboutdg.setTitle("About GBook");
				aboutdg.setContentView(R.layout.dialog);
				TextView dgtv=(TextView)aboutdg.findViewById(R.id.dialogtv);
				dgtv.setText(R.string.signature);
				aboutdg.show();
				
			}});
		Button introbt=(Button)findViewById(R.id.introbt);
		introbt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) 
			{
				Dialog aboutdg=new Dialog(v.getContext());
				aboutdg.setTitle("Book Introduction");
				aboutdg.setContentView(R.layout.dialog);
				TextView dgtv=(TextView)aboutdg.findViewById(R.id.dialogtv);
				dgtv.setText(BookInfo.getIntro(v.getContext()));
				aboutdg.show();
				
			}});
		Button readbt=(Button)findViewById(R.id.readbt);
		readbt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent startRead=new Intent(getApplicationContext(),BookPage.class);
				startActivity(startRead);
				
			}
		} );
		Button obt=(Button)findViewById(R.id.outlinebt);
		obt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent startRead=new Intent(getApplicationContext(),BookOutline.class);
				startActivity(startRead);
				
			}
		} );
		Button bmbt=(Button)findViewById(R.id.bookmarkbt);
		bmbt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(),BookMarkList.class);
				//startActivity(intent);
				startActivityForResult(intent,Utility.BookmarkRequestCode);
				
			}
		} );
		Button exitbt=(Button)findViewById(R.id.exitbt);
		exitbt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				System.exit(0);
			}
		} );
		
		
		//////////ads.....
		Utility.showAds(this);
		

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
