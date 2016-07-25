package com.example.wangjunyan.myfirstapp;

import android.app.Activity;
import android.os.Bundle;

public class TempTest2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_test2);
        RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.ripple);
        rippleBackground.startRippleAnimation();
    }
}
