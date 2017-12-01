package com.example.wangjunyan.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

public class CaddyTestActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

  private PanelLabelView panelLabelView;
  private SeekBar seekBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_caddy_test);
    panelLabelView = (PanelLabelView) findViewById(R.id.panel);
    seekBar = (SeekBar) findViewById(R.id.power);
    seekBar.setMax(100);
    seekBar.setProgress(0);
    seekBar.setOnSeekBarChangeListener(this);
  }

  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    panelLabelView.setPowerPercent(progress);
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {

  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {

  }
}
