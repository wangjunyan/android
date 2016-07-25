package com.example.wangjunyan.myfirstapp.crazyandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

import com.example.wangjunyan.myfirstapp.R;

public class DrawViewActivity extends Activity {

    DrawView drawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_view);

        //drawView = (DrawView) findViewById(R.id.draw_view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        drawView = new DrawView(this, displayMetrics.widthPixels, displayMetrics.heightPixels);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layout_content);
        relativeLayout.addView(drawView);

    }
}
