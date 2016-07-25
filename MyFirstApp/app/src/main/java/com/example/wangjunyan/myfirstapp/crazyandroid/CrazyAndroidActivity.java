package com.example.wangjunyan.myfirstapp.crazyandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wangjunyan.myfirstapp.R;

public class CrazyAndroidActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crazy_android);
        findViewById(R.id.btn_drawview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrazyAndroidActivity.this, DrawViewActivity.class));
            }
        });
        findViewById(R.id.btn_ballgame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrazyAndroidActivity.this, BallGameActivity.class));
            }
        });
        findViewById(R.id.btn_blast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrazyAndroidActivity.this, BlastActivity.class));
            }
        });

        findViewById(R.id.btn_ballfall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrazyAndroidActivity.this, BallFallActivity.class));
            }
        });
    }
}
