package com.example.wangjunyan.myfirstapp.firstlinecode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wangjunyan.myfirstapp.MainActivity;
import com.example.wangjunyan.myfirstapp.R;

import java.util.List;


public class FirstLineCodeTestActivity extends Activity {

    Button btnOK;
    Button btnView;
    Button btnFrag;
    Button btnContact;
    Button btnNotify;
    Button btnService;

    private SensorManager sensorManager;
    private LocationManager locationManager;
    private String provider;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float value = event.values[0];
            //Log.d(MainActivity.LOG_TAG, "Current light level is " + value);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_line_code_test);

        setLocation();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        btnOK = (Button) findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstLineCodeTestActivity.this, FirstLineCodeTestActivity.class);
                startActivity(intent);
            }
        });

        btnView = (Button) findViewById(R.id.btn_view);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });

        btnFrag = (Button) findViewById(R.id.btn_frag);
        btnFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstLineCodeTestActivity.this, FragmentTestActivity.class);
                startActivity(intent);
            }
        });

        btnContact = (Button) findViewById(R.id.btn_contact);
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstLineCodeTestActivity.this, ContactTestActivity.class);
                startActivity(intent);
            }
        });

        btnNotify = (Button) findViewById(R.id.btn_notify);
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstLineCodeTestActivity.this, NotificationTestActivity.class);
                startActivity(intent);
            }
        });

        btnService = (Button) findViewById(R.id.btn_service);
        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstLineCodeTestActivity.this, ServiceTestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        Log.d(MainActivity.LOG_TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume(){
        Log.d(MainActivity.LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause(){
        Log.d(MainActivity.LOG_TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop(){
        Log.d(MainActivity.LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        Log.d(MainActivity.LOG_TAG, "onDestroy");
        if(sensorManager != null){
            sensorManager.unregisterListener(sensorEventListener);
        }
        super.onDestroy();
    }

    @Override
    protected void onRestart(){
        Log.d(MainActivity.LOG_TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        Log.d(MainActivity.LOG_TAG, "onNewIntent");
    }

    private void setLocation(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if(providerList == null) return;
        if(providerList.contains(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
        }else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
            provider = LocationManager.NETWORK_PROVIDER;
        }else{
            Toast.makeText(this, "No location provider", Toast.LENGTH_LONG).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null){
            showLocation(location);
        }else{
            Log.d(MainActivity.LOG_TAG, "location is null");
        }
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
    }

    private void showLocation(Location location){
        Log.d(MainActivity.LOG_TAG, "latitude = " + location.getLatitude());
        Log.d(MainActivity.LOG_TAG, "longtitude = " + location.getLongitude());
    }
}
