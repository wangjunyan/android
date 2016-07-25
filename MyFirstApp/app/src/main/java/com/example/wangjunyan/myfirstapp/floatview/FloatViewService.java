package com.example.wangjunyan.myfirstapp.floatview;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class FloatViewService extends Service {
    private FloatView mFloatView;

    public FloatViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new FloatViewServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFloatView = new FloatView(this);
    }

    public void showFloat() {
        if ( mFloatView != null ) {
            mFloatView.show();
        }
    }

    public void hideFloat() {
        if ( mFloatView != null ) {
            mFloatView.hide();
        }
    }

    public void destroyFloat() {
        if ( mFloatView != null ) {
            mFloatView.destroy();
        }
        mFloatView = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyFloat();
    }

    public class FloatViewServiceBinder extends Binder {
        public FloatViewService getService() {
            return FloatViewService.this;
        }
    }
}
