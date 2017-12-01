package com.example.wangjunyan.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class PlotActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

  private PlotView plotView;
  private TextView tvBearing;
  private TextView tvTilt;
  private TextView tvTransX;
  private TextView tvTransY;
  private TextView tvRotateZ;
  private SeekBar skBearing;
  private SeekBar skTilt;
  private SeekBar skTransX;
  private SeekBar skTransY;
  private SeekBar skRotateZ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_plot);

    plotView = (PlotView) findViewById(R.id.plot);
    tvBearing = (TextView) findViewById(R.id.tv_bearing);
    tvTilt = (TextView) findViewById(R.id.tv_tilt);
    tvTransX = (TextView) findViewById(R.id.tv_transx);
    tvTransY = (TextView) findViewById(R.id.tv_transy);
    tvRotateZ = (TextView) findViewById(R.id.tv_rotatez);

    skBearing = (SeekBar) findViewById(R.id.bearing);
    skTilt = (SeekBar) findViewById(R.id.tilt);
    skTransX = (SeekBar) findViewById(R.id.transx);
    skTransY = (SeekBar) findViewById(R.id.transy);
    skRotateZ = (SeekBar) findViewById(R.id.rotatez);

    skBearing.setMax(360);
    skBearing.setProgress(0);
    skBearing.setOnSeekBarChangeListener(this);
    skTilt.setMax(45);
    skTilt.setProgress(0);
    skTilt.setOnSeekBarChangeListener(this);
    skTransX.setMax(100);
    skTransX.setProgress(50);
    skTransX.setOnSeekBarChangeListener(this);
    skTransY.setMax(100);
    skTransY.setProgress(50);
    skTransY.setOnSeekBarChangeListener(this);
    skRotateZ.setMax(360);
    skRotateZ.setProgress(0);
    skRotateZ.setOnSeekBarChangeListener(this);

  }

  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    DecimalFormat df=new DecimalFormat(".00");
    if (seekBar == skBearing) {
      tvBearing.setText("Bear: " + progress);
      plotView.setBear(progress);
    } else if (seekBar == skTilt) {
      tvTilt.setText("Tilt: " + progress);
      plotView.setTilt(progress);
    } else if(seekBar == skTransX){
      float dx = -1f + progress/100f*2;
      tvTransX.setText("TransX: " + df.format(dx));
      plotView.setTransX(dx);
    } else if(seekBar == skTransY){
      float dy = -1f + progress/100f*2;
      tvTransY.setText("TransY: " + df.format(dy));
      plotView.setTransY(dy);
    } else if (seekBar == skRotateZ){
      tvRotateZ.setText("RotateZ: " + progress);
      plotView.setRotateZ(progress);
    }
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {

  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {

  }
}
