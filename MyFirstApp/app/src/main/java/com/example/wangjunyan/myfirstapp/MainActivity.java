package com.example.wangjunyan.myfirstapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.wangjunyan.myfirstapp.crazyandroid.CrazyAndroidActivity;
import com.example.wangjunyan.myfirstapp.firstlinecode.FirstLineCodeTestActivity;
import com.example.wangjunyan.myfirstapp.floatview.FloatViewActivity;
import com.example.wangjunyan.myfirstapp.photoselector.PhotoSelectActivity;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

  public static final String LOG_TAG = "MyFirstApp";

  private static final String[] ITEMS = { "Touch Events Handle", "Scroll Test", "Temp Test",
      "First Line Code", "Photo Selector", "Crazy Android", "Like Button", "Radar Scan",
      "Float View", "Temp Test2", "Drag and Drop", "ImageView Test", "WebView Test", "Plot Test", "Video Test", "Caddy Test" };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // setContentView(R.layout.activity_main);
    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ITEMS);
    getListView().setAdapter(adapter);
    getListView().setOnItemClickListener(this);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    switch (position) {
      case 0:
        startActivity(new Intent(this, TouchEventActivity.class));
        break;
      case 1:
        startActivity(new Intent(this, ScrollTestActivity.class));
        break;
      case 2:
        startActivity(new Intent(this, TempTestActivity.class));
        break;
      case 3:
        startActivity(new Intent(this, FirstLineCodeTestActivity.class));
        break;
      case 4:
        startActivity(new Intent(this, PhotoSelectActivity.class));
        break;
      case 5:
        startActivity(new Intent(this, CrazyAndroidActivity.class));
        break;
      case 6:
        startActivity(new Intent(this, LikeButtonActivity.class));
        break;
      case 7:
        startActivity(new Intent(this, RadarScanActivity.class));
        break;
      case 8:
        startActivity(new Intent(this, FloatViewActivity.class));
        break;
      case 9:
        startActivity(new Intent(this, TempTest2Activity.class));
        break;
      case 10:
        startActivity(new Intent(this, DragDropActivity.class));
        break;
      case 11:
        startActivity(new Intent(this, ImageViewActivity.class));
        break;
      case 12:
        startActivity(new Intent(this, WebViewTestActivity.class));
        break;
      case 13:
        startActivity(new Intent(this, PlotActivity.class));
        break;
      case 14:
        startActivity(new Intent(this, VideoActivity.class));
        break;
      case 15:
        startActivity(new Intent(this, CaddyTestActivity.class));
        break;
      default:
        break;
    }
  }
}
