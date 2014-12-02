package com.example.apidemowjy;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import dalvik.system.DexClassLoader;

public class MainActivity extends Activity implements OnClickListener {

	private Button btnAnimation;
	private Button btnManualLayout;
	private Button btnListTest;
	private Button btnFragTest;
	private Button btnFragDyn;
	private Button btnIntentTest;
	private Button btnContactPicker;
	private Button btnEarthquake;
	private Button btnDownload;
	private Button btnContentProvider;
	private Button btnAsyncTask;
	private Button btnHttp;
	private Button btnTerminate;
	private Button btnService;

	public void dload(String filename, String classname) {
		try {
			Context ctx = getApplicationContext();
			String dex_dir = ctx.getDir("dex", 0).getAbsolutePath();
			ClassLoader parent = getClass().getClassLoader();
			DexClassLoader loader = new DexClassLoader(filename, dex_dir, null,
					parent);
			Class c = loader.loadClass(classname);
			Object o = c.newInstance();
			Method m = c.getMethod("func");

			m.invoke(o);
		} catch (Exception e) {
			Log.e(MyApplication.TAG, String.format("DLL failed: %s: %s", e.getClass()
					.getName(), e.getMessage()));
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnAnimation = (Button) findViewById(R.id.btn_animation);
		btnManualLayout = (Button) findViewById(R.id.btn_manual_layout);
		btnListTest = (Button) findViewById(R.id.btn_list_test);
		btnFragTest = (Button) findViewById(R.id.btn_fragment_test);
		btnFragDyn = (Button) findViewById(R.id.btn_fragment_dynamic);
		btnIntentTest = (Button) findViewById(R.id.btn_intent_test);
		btnContactPicker = (Button) findViewById(R.id.btn_contact_picker);
		btnEarthquake = (Button) findViewById(R.id.btn_earthquake_test);
		btnDownload = (Button) findViewById(R.id.btn_download_manager);
		btnContentProvider = (Button) findViewById(R.id.btn_content_provider);
		btnAsyncTask = (Button) findViewById(R.id.btn_asynctask);
		btnHttp = (Button) findViewById(R.id.btn_http);
		btnTerminate = (Button) findViewById(R.id.btn_terminate);
		btnService = (Button) findViewById(R.id.btn_service);
		
		btnAnimation.setOnClickListener(this);
		btnManualLayout.setOnClickListener(this);
		btnListTest.setOnClickListener(this);
		btnFragTest.setOnClickListener(this);
		btnFragDyn.setOnClickListener(this);
		btnIntentTest.setOnClickListener(this);
		btnContactPicker.setOnClickListener(this);
		btnEarthquake.setOnClickListener(this);
		btnDownload.setOnClickListener(this);
		btnContentProvider.setOnClickListener(this);
		btnAsyncTask.setOnClickListener(this);
		btnHttp.setOnClickListener(this);
		btnTerminate.setOnClickListener(this);
		btnService.setOnClickListener(this);

		dload("/mnt/sdcard/Foo.jar", "Foo");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_animation:
			intent = new Intent(MainActivity.this, AnimationActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_manual_layout:
			intent = new Intent(MainActivity.this, ManualLayoutActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_list_test:
			intent = new Intent(MainActivity.this, ToDoListActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_fragment_test:
			intent = new Intent(MainActivity.this, ToDoListFragActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_fragment_dynamic:
			intent = new Intent(MainActivity.this, MyFragmentActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_intent_test:
			intent = new Intent(MainActivity.this, IntentTestActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_contact_picker:
			intent = new Intent(MainActivity.this, ContactPickerTester.class);
			startActivity(intent);
			break;
		case R.id.btn_earthquake_test:
			intent = new Intent(MainActivity.this, EarthquakeActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_download_manager:
			intent = new Intent(MainActivity.this, DownloadTestActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_content_provider:
			intent = new Intent(MainActivity.this,
					ContentProvidersActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_asynctask:
			intent = new Intent(MainActivity.this, AsyncTaskTest.class);
			startActivity(intent);
			break;
		case R.id.btn_http:
			intent = new Intent(MainActivity.this, HttpTestActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_terminate:
			intent = new Intent(MainActivity.this, TerminateActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_service:
			intent = new Intent(MainActivity.this, CounterActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}

/*
Foo.java
import android.util.Log;

public class Foo {
    public static void func() {
        Log.d("se.sdu", "Hello from Foo.func()");
    }
}

javac -classpath /path/to/android.jar Foo.java
dx --dex --output Foo.jar Foo.class
adb push Foo.jar /mnt/sdcard/

*/