package com.gavinz.gbook;

import java.io.IOException;

import com.gavin.gbook.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.app.*;
import android.content.*;
import android.view.View;

import java.io.*;
public class Utility {
	public static void showAds(Activity act)
	{
	    AdView adView = (AdView) act.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("AC8C42EF03B809A6E4DCF7114382B39D")
        .build();
	    adView.loadAd(adRequest);
	}
	public static void showAds(View view)
	{
	    AdView adView = (AdView) view.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("AC8C42EF03B809A6E4DCF7114382B39D")
        .build();
	    adView.loadAd(adRequest);
	}
	public static InputStream getDatabase(Context context)
	{
		InputStream is=null;
		try {
			is=context.getAssets().open("gbook.db");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}
	public static int getPixelByDP(Context context,int dps)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		int pixels = (int) (dps * scale + 0.5f);
		return pixels;
	}
	public static float getPixelBySP(Context context, float ds) {
	    float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
	    return ds*scaledDensity;
	}
	public static int BookmarkRequestCode=1;

}
