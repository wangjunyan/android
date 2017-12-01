package com.example.wangjunyan.myfirstapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;

public class VideoActivity extends Activity {

  private static final String TAG = "VideoActivity";
  private static final int VIDEO_WIDTH = 268;
  private static final int VIDEO_HEIGHT = 480;
  VideoView videoView;

  public static final String DEFAULT_BIRTHDAY_CLIENT = "Jan 01 1980";
  public static final DateFormat BIRTHDAY_FORMAT_CLIENT = new SimpleDateFormat("MMM dd yyyy");
  public static final String DEFAULT_BIRTHDAY_SERVER = "01/01/1980";
  public static final DateFormat BIRTHDAY_FORMAT_SERVER = new SimpleDateFormat("MM/dd/yyyy");

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
    // WindowManager.LayoutParams.FLAG_FULLSCREEN);
    // requestWindowFeature(Window.FEATURE_NO_TITLE);

    setContentView(R.layout.activity_video);

    findViewById(R.id.iv_logo).setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(VideoActivity.this, TempTestActivity.class);
        startActivity(intent);
      }
    });

    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    int w = displayMetrics.widthPixels;// 1080
    int h = displayMetrics.heightPixels;// 1794
    float scaleW = w * 1f / VIDEO_WIDTH;
    float scaleH = h * 1f / VIDEO_HEIGHT;
    int viewW = (scaleW > scaleH ? w : (int) (scaleH * VIDEO_WIDTH));
    int viewH = (scaleH > scaleW ? h : (int) (scaleW * VIDEO_HEIGHT));

    videoView = (VideoView) findViewById(R.id.view_video);

    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(viewW, viewH);
    params.setMargins((w - viewW) / 2, (h - viewH) / 2, 0, 0);
    videoView.setLayoutParams(params);
    String path = "android.resource://" + getPackageName() + "/" + R.raw.golf;
    videoView.setVideoPath(path);
    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

      @Override
      public void onPrepared(MediaPlayer mp) {
        mp.setLooping(true);
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    videoView.start();

    String birthday = "";
    try {
      Date date = BIRTHDAY_FORMAT_SERVER.parse(DEFAULT_BIRTHDAY_SERVER);
      birthday = BIRTHDAY_FORMAT_CLIENT.format(date);
    } catch (ParseException ex) {

    }
    Log.i(TAG, "birthday = " + birthday);

  }

  @Override
  protected void onPause() {
    super.onPause();
    videoView.pause();
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
  }
}
