package com.example.apidemowjy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TerminateActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.terminate_test);
		
		findViewById(R.id.btn_terminate1).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				TerminateActivity.this.finish();
			}
		});
		
		findViewById(R.id.btn_terminate2).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				TerminateActivity.this.moveTaskToBack(true);
				System.exit(0);
			}
		});
		
		findViewById(R.id.btn_terminate3).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				TerminateActivity.this.startActivity(intent);
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		
	}
}
