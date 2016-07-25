package com.example.wangjunyan.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class RadarScanActivity extends Activity {

    private RadarScanView radar;

    int i = 0;

    int j = 0;

    private Handler handler;

    int num = 0;

    Runnable textRun = new Runnable() {
        @Override
        public void run() {
            radar.setCollectionNum(num);
            radar.setUnit("M");
            num++;
            handler.postDelayed(textRun, 10);
        }
    };

    private UserGuideView guideView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_scan);

        handler = new Handler();

        radar = (RadarScanView) findViewById(R.id.radar_scan);
        radar.setClearTime(3600);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i % 2 == 0) {
                    radar.stopScan();
                    radar.setWhiteLayer(true);
                    handler.removeCallbacksAndMessages(null);
                } else {
                    radar.startScan();
                    handler.post(textRun);
                }
            }
        });

        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                j++;
                if (j % 2 == 0) {
                    radar.stopClear();
                } else {
                    radar.startClear();
                }

            }
        });

        guideView = (UserGuideView) findViewById(R.id.guideView);

        guideView.setHighLightView(findViewById(R.id.btn));
    }
}
