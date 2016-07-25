package com.example.wangjunyan.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;


public class ScrollTestActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test);

        ImageView imageView = (ImageView) findViewById(R.id.scrollImageView);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("getScrollX() = " + imageView.getScrollX());

        ImageView imageViewToRight = (ImageView) findViewById(R.id.scrollImageViewToRight);
        imageViewToRight.scrollTo(-100, 0);
        TextView textViewToRight = (TextView) findViewById(R.id.textViewToRight);
        textViewToRight.setText("getScrollX() = " + imageViewToRight.getScrollX());

        ImageView imageViewToLeft = (ImageView) findViewById(R.id.scrollImageViewToLeft);
        imageViewToLeft.scrollTo(100, 0);
        TextView textViewToLeft = (TextView) findViewById(R.id.textViewToLeft);
        textViewToLeft.setText("getScrollX() = " + imageViewToLeft.getScrollX());
    }

    @Override
    public void onStart(){
        Log.d(MainActivity.LOG_TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onRestart(){
        Log.d(MainActivity.LOG_TAG, "onRestart");
        super.onResume();
    }

    @Override
    public void onResume(){
        Log.d(MainActivity.LOG_TAG, "onResume");
        super.onResume();

        String value = "2079.74";
        long amount = (long)(Double.parseDouble(value)*100);
        Log.d(MainActivity.LOG_TAG, "value=" + value + " amount=" + amount);
        Log.d(MainActivity.LOG_TAG, "[BigDecimal] value=" + value + " amount=" + new BigDecimal(value).multiply(new BigDecimal(100)).longValue());
    }

    @Override
    public void onStop(){

        super.onStop();
        Log.d(MainActivity.LOG_TAG, "onStop");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(MainActivity.LOG_TAG, "onPause");


    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(MainActivity.LOG_TAG, "onDestroy");
    }
}
