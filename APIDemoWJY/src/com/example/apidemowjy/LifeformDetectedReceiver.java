package com.example.apidemowjy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

public class LifeformDetectedReceiver extends BroadcastReceiver {

	public final static String EXTRA_LIFEFORM_NAME = "EXTRA_LIFEFORM_NAME";
	public final static String EXTRA_LATITUDE = "EXTRA_LATITUDE";
	public final static String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";

	public static final String ACTION_BURN = "com.example.apidemowjy.action.BURN_IT_WITH_FIRE";

	public static final String NEW_LIFEFORM = "com.example.apidemowjy.action.NEW_LIFEFORM";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	    Uri data = intent.getData();
	    String type = intent.getStringExtra(EXTRA_LIFEFORM_NAME);
	    double lat = intent.getDoubleExtra(EXTRA_LATITUDE, 0);
	    double lng = intent.getDoubleExtra(EXTRA_LONGITUDE, 0);
	    Location loc = new Location("gps");
	    loc.setLatitude(lat);
	    loc.setLongitude(lng);
	    Log.d(MyApplication.TAG, "type=" + type + " latitude=" + lat + " longtitude=" + lng);
	}

}
