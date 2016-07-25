package com.example.wangjunyan.myfirstapp.firstlinecode;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.wangjunyan.myfirstapp.MainActivity;

public class LongrunService extends Service {
    public LongrunService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(MainActivity.LOG_TAG, "onBind " + LongrunService.class.getSimpleName());
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent){
        Log.d(MainActivity.LOG_TAG, "onUnbind " + LongrunService.class.getSimpleName());
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent){
        Log.d(MainActivity.LOG_TAG, "onRebind " + LongrunService.class.getSimpleName());
        super.onRebind(intent);
    }

    @Override
    public void onCreate(){
        Log.d(MainActivity.LOG_TAG, "onCreate " + LongrunService.class.getSimpleName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(MainActivity.LOG_TAG, "onStartCommand " + LongrunService.class.getSimpleName() + " " + new Date().toString());
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour= 5*1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent intent1 = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent1, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        */
        //return super.onStartCommand(intent, flags, startId);
        if(intent != null){
            Log.d(MainActivity.LOG_TAG, "onStartCommand " + LongrunService.class.getSimpleName() + " string=" + intent.getStringExtra("TEST"));
        }else{
            Log.d(MainActivity.LOG_TAG, "onStartCommand " + LongrunService.class.getSimpleName() + " intent = null");
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy(){
        Log.d(MainActivity.LOG_TAG, "onDestroy " + LongrunService.class.getSimpleName());
        super.onDestroy();
    }
}
