package com.example.wangjunyan.myfirstapp.firstlinecode;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.wangjunyan.myfirstapp.MainActivity;
import com.example.wangjunyan.myfirstapp.R;

public class MyService extends Service {
    private DownloadBinder mBinder = new DownloadBinder();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(MainActivity.LOG_TAG, "onBind " + MyService.class.getSimpleName());
        return mBinder;
    }

    @Override
    public void onCreate(){
        Log.d(MainActivity.LOG_TAG, "onCreate " + MyService.class.getSimpleName());
        super.onCreate();
        Notification notification = new Notification(R.drawable.notify, "Notification comes", System.currentTimeMillis());
        Intent notificationIntent = new Intent(MyService.this, ContactTestActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, "This is title", "This is content", pi);
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(MainActivity.LOG_TAG, "onStartCommand " + MyService.class.getSimpleName());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        Log.d(MainActivity.LOG_TAG, "onDestroy " + MyService.class.getSimpleName());
        super.onDestroy();
    }

    class DownloadBinder extends Binder {
        public void startDownload(){
            Log.d(MainActivity.LOG_TAG, "startDownload");
        }

        public int getProgress(){
            Log.d(MainActivity.LOG_TAG, "getProgress");
            return 0;
        }

    }
}
