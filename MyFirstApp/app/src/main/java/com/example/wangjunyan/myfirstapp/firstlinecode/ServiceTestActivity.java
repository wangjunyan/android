package com.example.wangjunyan.myfirstapp.firstlinecode;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wangjunyan.myfirstapp.MainActivity;
import com.example.wangjunyan.myfirstapp.R;

public class ServiceTestActivity extends Activity {

    Button btnStart;
    Button btnStop;
    Button btnBind;
    Button btnUnbind;
    Button btnForeground;
    Button btnLongrun;

    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(MainActivity.LOG_TAG, "onServiceConnected " + ServiceTestActivity.class.getSimpleName());
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(MainActivity.LOG_TAG, "onServiceDisconnected " + ServiceTestActivity.class.getSimpleName());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);

        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(ServiceTestActivity.this, MyService.class);
                startService(startIntent);
            }
        });

        btnStop = (Button) findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(ServiceTestActivity.this, MyService.class);
                stopService(stopIntent);
            }
        });

        btnBind = (Button) findViewById(R.id.btn_bind);
        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindIntent = new Intent(ServiceTestActivity.this, MyService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
            }
        });

        btnUnbind = (Button) findViewById(R.id.btn_unbind);
        btnUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);
            }
        });

        btnForeground = (Button) findViewById(R.id.btn_foreground);
        btnForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.LOG_TAG, "Main Thread id =  " + Thread.currentThread().getId());
                Intent intentService = new Intent(ServiceTestActivity.this, MyIntentService.class);
                startService(intentService);
            }
        });

        btnLongrun = (Button) findViewById(R.id.btn_longrun);
        btnLongrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(ServiceTestActivity.this, LongrunService.class);
                intentService.putExtra("TEST", "test_string");
                startService(intentService);
            }
        });
    }
}
