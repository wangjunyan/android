package com.example.apidemowjy;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IntentTestActivity extends Activity {

	private void implicitStartTest() {
		Intent intent = new Intent(Intent.ACTION_DIAL,
				Uri.parse("tel:555-2368"));
		PackageManager pm = getPackageManager();
		ComponentName cn = intent.resolveActivity(pm);
		if (cn == null) {
			Uri maketUri = Uri
					.parse("market://search?q=pname:com.myapp.packagename");
			Intent marketIntent = new Intent(Intent.ACTION_VIEW)
					.setData(maketUri);
			if (marketIntent.resolveActivity(pm) != null) {
				startActivity(marketIntent);
			} else {
				Log.d(MyApplication.TAG, "Market client not available.");
			}
		} else {
			Log.d(MyApplication.TAG, "ComponentName = " + cn.getClassName());
			startActivity(intent);
		}
	}

	private static final int SHOW_SUBACTIVITY = 1;

	private void startSubActivityTest() {
		Intent intent = new Intent(this, MyOtherActivity.class);
		startActivityForResult(intent, SHOW_SUBACTIVITY);
	}

	private void broadcastTest() {
		String detectedLifeform = "Shanghai";
		double currentLongitude = 2.05;
		double currentLatitude = 6.09;
		Intent intent = new Intent(LifeformDetectedReceiver.NEW_LIFEFORM);
		intent.putExtra(LifeformDetectedReceiver.EXTRA_LIFEFORM_NAME,
				detectedLifeform);
		intent.putExtra(LifeformDetectedReceiver.EXTRA_LONGITUDE,
				currentLongitude);
		intent.putExtra(LifeformDetectedReceiver.EXTRA_LATITUDE,
				currentLatitude);

		sendBroadcast(intent);
	}

	private static final int PICK_CONTACT_SUBACTIVITY = 2;

	private void startSubActivityImplicitly() {
		Uri uri = Uri.parse("content://contacts/people");
		Intent intent = new Intent(Intent.ACTION_PICK, uri);
		startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY);
	}

	private void findThirdPartyActions() {
		PackageManager packageManager = getPackageManager();
		Intent intent = new Intent();
		intent.setType("audio/*");
		intent.addCategory(Intent.CATEGORY_SELECTED_ALTERNATIVE);
		// int flags = PackageManager.MATCH_DEFAULT_ONLY;
		List<ResolveInfo> actions;
		actions = packageManager.queryIntentActivities(intent, 0);
		Log.d(MyApplication.TAG, "action list size = " + actions.size());
		// Resources r = getResources();
		for (ResolveInfo action : actions) {
			Log.d(MyApplication.TAG, action.activityInfo.name);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		Log.d(MyApplication.TAG, "request code = " + requestCode);

		if (resultCode == Activity.RESULT_OK) {
			Log.d(MyApplication.TAG, "Activity.RESULT_OK");
			Log.d(MyApplication.TAG, "data = " + data.getData().toString());
		} else if (resultCode == Activity.RESULT_CANCELED) {
			Log.d(MyApplication.TAG, "Activity.RESULT_CANCELED");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intent_test);

		Button btnImplicitStart = (Button) findViewById(R.id.button1);
		btnImplicitStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				implicitStartTest();
			}

		});

		Button btnSubActivity = (Button) findViewById(R.id.button2);
		btnSubActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startSubActivityTest();
			}

		});

		Button btnSubActivityImplicitly = (Button) findViewById(R.id.button3);
		btnSubActivityImplicitly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startSubActivityImplicitly();
			}

		});

		Button btnBroadcastTest = (Button) findViewById(R.id.button4);
		btnBroadcastTest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				broadcastTest();
			}

		});

		Button btnThirdPartyAct = (Button) findViewById(R.id.button5);
		btnThirdPartyAct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				findThirdPartyActions();
			}

		});
		
	    IntentFilter batIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
	    Intent battery = this.registerReceiver(null, batIntentFilter);
	    int status = battery.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
	    boolean isCharging = 
	      status == BatteryManager.BATTERY_STATUS_CHARGING || 
	      status == BatteryManager.BATTERY_STATUS_FULL;
	    
	    String svcName = Context.CONNECTIVITY_SERVICE;
	    ConnectivityManager cm = (ConnectivityManager)this.getSystemService(svcName);

	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    boolean isConnected = activeNetwork.isConnectedOrConnecting();
	    boolean isMobile = activeNetwork.getType() ==
	                       ConnectivityManager.TYPE_MOBILE;
	    
	    Log.d(MyApplication.TAG, "Is Charging? " + isCharging);
	    Log.d(MyApplication.TAG, "Is Connected? " + isConnected);
	    Log.d(MyApplication.TAG, "Is Mobile? " + isMobile);

	}

	private IntentFilter filter = new IntentFilter(
			LifeformDetectedReceiver.NEW_LIFEFORM);
	private LifeformDetectedReceiver receiver = new LifeformDetectedReceiver();

	@Override
	public synchronized void onResume() {
		super.onResume();

		// Register the broadcast receiver.
		registerReceiver(receiver, filter);
	}

	@Override
	public synchronized void onPause() {
		// Unregister the receiver
		unregisterReceiver(receiver);

		super.onPause();
	}

}
