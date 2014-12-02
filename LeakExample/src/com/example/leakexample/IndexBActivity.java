package com.example.leakexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class IndexBActivity extends Activity {
	private static final String TAG = "Index B";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_b);
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(TAG, "onStart");
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(TAG, "onResume");
	}
	
	@Override
	protected void onPause(){
		Log.d(TAG, "onPause");
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


}
