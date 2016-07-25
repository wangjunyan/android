package com.example.apidemowjy;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;



public class MyApplication extends Application {
	 private static MyApplication singleton;
	 public static String TAG = "APIDemoWJY";
	  public static MyApplication getInstance() {
		    return singleton;
	  }
	  
	  @Override
	  public final void onCreate() {
	    super.onCreate();
	    singleton = this;
	    Log.d(TAG, "enter MyApp onCreate");
	  }

	  @Override
	  public final void onLowMemory() {
	    super.onLowMemory();
	  }

	  @Override
	  public final void onTrimMemory(int level) {
	    super.onTrimMemory(level);
	  }

	  @Override
	  public final void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	  } 
}
