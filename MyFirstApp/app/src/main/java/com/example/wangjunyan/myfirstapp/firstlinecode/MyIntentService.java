package com.example.wangjunyan.myfirstapp.firstlinecode;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.wangjunyan.myfirstapp.MainActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        Log.d(MainActivity.LOG_TAG, "onHandleIntent, Thread id = " + Thread.currentThread().getId());
        for(int i = 0; i < 5; i++){
            try {
                Thread.sleep(1000);
                Log.d(MainActivity.LOG_TAG, "working...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy(){
        Log.d(MainActivity.LOG_TAG, "onDestroy " + MyIntentService.class.getSimpleName());
        super.onDestroy();
    }

}
