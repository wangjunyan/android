package com.example.leakexample;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	//static Leaky leak = null;
	static final String TAG = "Leak";
	TextView txtHello;
	
	private Bitmap bm;
	private BitmapDrawable bd;
	private RelativeLayout helloLayout;
//	class Leaky {
//		void doSomething(){
//			System.out.println("Wheeel!!!");
//		}
//		
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		helloLayout = (RelativeLayout) findViewById(R.id.hello_layout);
		
		

		
		txtHello = (TextView) findViewById(R.id.txt_hello);
		txtHello.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, IndexBActivity.class);
				startActivity(i);
				finish();
			}
			
		});
		
//		if(leak == null){
//			leak = new Leaky();
//		}
		ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
		int mem = am.getMemoryClass();
		Log.d(TAG, "heap size = " + mem);
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(TAG, "onStart");
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		loadBitmap();
		Log.d(TAG, "onResume");
	}
	
	@Override
	protected void onPause(){
		Log.d(TAG, "onPause");
		recycleBitmap();
		super.onPause();
	}

	@Override
	protected void onStop(){
		Log.d(TAG, "onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}
	
	private void loadBitmap(){
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		bd = new BitmapDrawable(getResources(), bm);
		Log.d(TAG, "----------------" + bm.getByteCount() + "->" + bm.getHeight() + "x" + bm.getWidth() + "x" + bm.getDensity());
		
		if (Build.VERSION.SDK_INT >= 16) {
			helloLayout.setBackground(bd);
		} else {
			helloLayout.setBackgroundDrawable(bd);
		}
	}
	
	private void recycleBitmap(){
		if (helloLayout != null){
			if (Build.VERSION.SDK_INT >= 16) {
				helloLayout.setBackground(null);
			} else {
				helloLayout.setBackgroundDrawable(null);
			}
			helloLayout = null;
		}
		if (bd != null){
			bm.recycle();
			Log.d(TAG, "free the bitmap manually!");
		}
		bd = null;
		bm = null;
//		if(helloLayout != null){
//			if (Build.VERSION.SDK_INT >= 16) {
//				helloLayout.setBackground(null);
//			} else {
//				helloLayout.setBackgroundDrawable(null);
//			}
//			helloLayout = null;			
//		}
//		Log.d(TAG, "free the bitmap manually!");
//		bm.recycle();
//		
	}
}
