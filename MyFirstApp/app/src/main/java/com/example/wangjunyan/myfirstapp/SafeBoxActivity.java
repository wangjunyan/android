package com.example.wangjunyan.myfirstapp;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class SafeBoxActivity extends Activity {

    View viewTop;
    View viewBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_box);
        viewTop = findViewById(R.id.view_top);
        viewBottom = findViewById(R.id.view_bottom);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == event.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            ObjectAnimator animatorTop = ObjectAnimator.ofFloat(viewTop, "translationY", -viewTop.getHeight());
            animatorTop.setDuration(1000);
            animatorTop.start();
            ObjectAnimator animatorBottom = ObjectAnimator.ofFloat(viewBottom, "translationY", viewBottom.getHeight());
            animatorBottom.setDuration(1000);
            animatorBottom.start();
        }
        return true;
    }
}
