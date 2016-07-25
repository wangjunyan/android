package com.example.wangjunyan.myfirstapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by wangjunyan on 16/1/18.
 */
public class MyApplication extends Application {
    private final Application.ActivityLifecycleCallbacks lifecycleCallbacks =
            new Application.ActivityLifecycleCallbacks() {
                @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    Log.d(MainActivity.LOG_TAG, activity.getClass().getName() + " created");
                }

                @Override public void onActivityStarted(Activity activity) {
                    Log.d(MainActivity.LOG_TAG, activity.getClass().getName() + " started");
                }

                @Override public void onActivityResumed(Activity activity) {
                    Log.d(MainActivity.LOG_TAG, activity.getClass().getName() + " resumed");
                }

                @Override public void onActivityPaused(Activity activity) {
                    Log.d(MainActivity.LOG_TAG, activity.getClass().getName() + " paused");
                }

                @Override public void onActivityStopped(Activity activity) {
                    Log.d(MainActivity.LOG_TAG, activity.getClass().getName() + " stopped");
                }

                @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    Log.d(MainActivity.LOG_TAG, activity.getClass().getName() + " saved");
                }

                @Override public void onActivityDestroyed(Activity activity) {
                    Log.d(MainActivity.LOG_TAG, activity.getClass().getName() + " destroyed");
                }
            };

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.this.registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }

    @Override
    public void onTerminate(){
        MyApplication.this.unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
        super.onTerminate();
    }
}
