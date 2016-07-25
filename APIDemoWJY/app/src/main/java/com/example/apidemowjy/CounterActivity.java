package com.example.apidemowjy;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends Activity implements OnClickListener, ICounterCallback{

	private Button btnStart;
	private Button btnStop;
	private TextView txtCount;
	private ICounterService counterService = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.counter_activity);
		btnStart = (Button)findViewById(R.id.btn_start);
		btnStop = (Button)findViewById(R.id.btn_stop);
		txtCount = (TextView)findViewById(R.id.txt_counter);
		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		Intent bindIntent = new Intent(CounterActivity.this, CounterService.class);
		bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);
		Log.i(MyApplication.TAG, "Counter Activity Created.");
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		unbindService(serviceConnection);
	}
	
	@Override
	public void count(int val) {
		// TODO Auto-generated method stub
		String text = String.valueOf(val);
		txtCount.setText(text);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(btnStart)){
			if(counterService != null){
				counterService.startCounter(0,  this);
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
			}
		}else if(v.equals(btnStop)){
			if(counterService != null){
				counterService.stopCounter();
				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
			}
		}
	}
		
	private ServiceConnection serviceConnection = new ServiceConnection(){
		public void onServiceConnected(ComponentName className, IBinder service){
			counterService = ((CounterService.CounterBinder)service).getService();
			Log.i(MyApplication.TAG, "Counter Service Connected.");
		}
		public void onServiceDisconnected(ComponentName className){
			counterService = null;
			Log.i(MyApplication.TAG, "Counter Service Disconnected.");
		}
	};

}
