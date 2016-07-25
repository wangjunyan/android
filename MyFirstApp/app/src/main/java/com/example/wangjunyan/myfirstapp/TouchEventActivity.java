package com.example.wangjunyan.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/*
LayoutView1
    LayoutView2
        MyTextView
1. 没有View或ViewGroup消费ACTION_DOWN事件
ACTION_DOWN:
TouchEventActivity.dispatchTouchEvent
LayoutView1.dispatchTouchEvent
LayoutView1.onInterceptTouchEvent
LayoutView2.dispatchTouchEvent
LayoutView2.onInterceptTouchEvent
MyTextView.dispatchTouchEvent
MyTextView.OnTouchListener.onTouch
MyTextView.onTouchEvent
LayoutView2.OnTouchListener.onTouch
LayoutView2.onTouchEvent
LayoutView1.OnTouchListener.onTouch
LayoutView1.onTouchEvent
TouchEventActivity.onTouchEvent
ACTION_MOVE:
TouchEventActivity.dispatchTouchEvent
TouchEventActivity.onTouchEvent
------------
因为没有消费ACTION_DOWN事件，后续的TouchEvent不再传递

2. LayoutView2拦截且消费了ACTION_DOWN事件
ACTION_DOWN:
TouchEventActivity.dispatchTouchEvent
LayoutView1.dispatchTouchEvent
LayoutView1.onInterceptTouchEvent
LayoutView2.dispatchTouchEvent
LayoutView2.onInterceptTouchEvent
LayoutView2.OnTouchListener.onTouch
LayoutView2.onTouchEvent
ACTION_MOVE:
TouchEventActivity.dispatchTouchEvent
LayoutView1.dispatchTouchEvent
LayoutView1.onInterceptTouchEvent
LayoutView2.dispatchTouchEvent
LayoutView2.OnTouchListener.onTouch
LayoutView2.onTouchEvent
------------
后续的TouchEvent都传递给了LayoutView2

3. LayoutView2拦截了ACTION_DOWN事件但不消费，而LayoutView1消费了该TouchEvent
ACTION_DOWN:
TouchEventActivity.dispatchTouchEvent
LayoutView1.dispatchTouchEvent
LayoutView1.onInterceptTouchEvent
LayoutView2.dispatchTouchEvent
LayoutView2.onInterceptTouchEvent
LayoutView2.OnTouchListener.onTouch
LayoutView2.onTouchEvent
LayoutView1.OnTouchListener.onTouch
LayoutView1.onTouchEvent
ACTION_MOVE:
TouchEventActivity.dispatchTouchEvent
LayoutView1.dispatchTouchEvent
LayoutView1.OnTouchListener.onTouch
LayoutView1.onTouchEvent
------------
后续的TouchEvent都传递给了LayoutView1
*/

public class TouchEventActivity extends Activity {

    private static final String TAG = "TouchEventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
        findViewById(R.id.layout_view1).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "LayoutView1 OnTouchListener action:ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG,"LayoutView1 OnTouchListener action:ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG,"LayoutView1 OnTouchListener action:ACTION_UP");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d(TAG,"LayoutView1 OnTouchListener action:ACTION_CANCEL");
                        break;
                }
                return false;
            }
        });

        findViewById(R.id.layout_view2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "LayoutView2 OnTouchListener action:ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG,"LayoutView2 OnTouchListener action:ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG,"LayoutView2 OnTouchListener action:ACTION_UP");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d(TAG,"LayoutView2 OnTouchListener action:ACTION_CANCEL");
                        break;
                }
                return false;
            }
        });


        findViewById(R.id.text_view).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "MyTextView OnTouchListener action:ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG,"MyTextView OnTouchListener action:ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG,"MyTextView OnTouchListener action:ACTION_UP");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d(TAG,"MyTextView OnTouchListener action:ACTION_CANCEL");
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        //super.dispatchTouchEvent(ev);
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "dispatchTouchEvent action:ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"dispatchTouchEvent action:ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"dispatchTouchEvent action:ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG,"dispatchTouchEvent action:ACTION_CANCEL");
                break;
        }
        return  super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev){
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent action:ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"onTouchEvent action:ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"onTouchEvent action:ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG,"onTouchEvent action:ACTION_CANCEL");
                break;
        }
        return  true;
    }

}
