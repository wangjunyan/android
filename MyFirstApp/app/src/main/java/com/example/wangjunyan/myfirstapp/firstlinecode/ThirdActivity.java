package com.example.wangjunyan.myfirstapp.firstlinecode;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.wangjunyan.myfirstapp.R;

public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_third);
    }

}
