package com.example.wangjunyan.myfirstapp;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TempTestActivity extends Activity {
    private static final String XWALK_APK_PACKAGE = "org.xwalk.core";
    private static final String SHORTCUT_ENTRY_PKG_NAME = "com.example.wangjunyan.myfirstapp";
    private static final String SHORTCUT_ENTRY_CLASS_NAME = "com.example.wangjunyan.myfirstapp.photoselector.PhotoSelectActivity";
    private static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final String ACTION_UNINSTALL_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";


    private JobScheduler mJobScheduler;


    private EditText edtProgress;
    private EditText edtSecondProgress;
    private Button btnOK;
    private ProgressBar progressBar;
    private ProgressView progressView;
    private int progress = 0;
    private LightWaveOvalView lightWaveOvalView;


    private IMyService iMyService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            iMyService = IMyService.Stub.asInterface(service);
            try{
                Student student = iMyService.getStudent().get(0);
                Toast.makeText(TempTestActivity.this, student.toString(), Toast.LENGTH_LONG);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyService = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_test);
        edtProgress = (EditText) findViewById(R.id.edt_prgress);

        edtProgress.setLinksClickable(true);
        edtProgress.setAutoLinkMask(Linkify.WEB_URLS);
        //If the edit text contains previous text with potential links
        Linkify.addLinks(edtProgress, Linkify.WEB_URLS);
        edtProgress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                Linkify.addLinks(s, Linkify.WEB_URLS);

            }
        });

        edtSecondProgress = (EditText) findViewById(R.id.edt_secondProgress);
        btnOK = (Button) findViewById(R.id.btn_ok);
        progressBar = (ProgressBar) findViewById(R.id.circularProgressbar);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = Integer.parseInt(edtProgress.getText().toString());
                int secondProgress = Integer.parseInt(edtSecondProgress.getText().toString());
                progressBar.setSecondaryProgress(secondProgress);
                progressBar.setProgress(progress);
            }
        });

        BigDecimal bFen = new BigDecimal(5L);
        BigDecimal bYuan = bFen.divide(new BigDecimal(100));
        Log.d(MainActivity.LOG_TAG, String.format("%.2f", bYuan));
        Log.d(MainActivity.LOG_TAG, String.format("%.2f", 5000000000000L/100f));
        DecimalFormat df = new DecimalFormat("#,##0.00");
        Log.d(MainActivity.LOG_TAG, df.format(bYuan));

        Log.d(MainActivity.LOG_TAG, "String ->" + java.lang.String.class.getClassLoader());
        Log.d(MainActivity.LOG_TAG, "Activity ->" + android.app.Activity.class.getClassLoader());
        Log.d(MainActivity.LOG_TAG, "This ->" + TempTestActivity.class.getClassLoader());
        Log.d(MainActivity.LOG_TAG, "System ->" + ClassLoader.getSystemClassLoader());

//        Context mBridgeContext = null;
//        Context mWrapperContext = this;
//        try {
//            mBridgeContext = mWrapperContext.createPackageContext(XWALK_APK_PACKAGE,
//                    Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
//            Log.d(MainActivity.LOG_TAG, "Created bridge context");
//        } catch (NameNotFoundException e) {
//            Log.d(MainActivity.LOG_TAG, "Crosswalk package not found");
//        }
//        Log.d(MainActivity.LOG_TAG, "mWrapperContext ->" +  mWrapperContext.getClassLoader());
//        Log.d(MainActivity.LOG_TAG, "mBridgeContext ->" + mBridgeContext.getClassLoader());

        findViewById(R.id.btn_install).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences  sharedPreferences = getSharedPreferences("TEST", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("test", "hello");
                editor.commit();
                createShortcut(true);

                lightWaveOvalView.startWave();
            }
        });

        findViewById(R.id.btn_uninstall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShortcut(false);
            }
        });

        findViewById(R.id.btn_window).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button btn_floatView = new Button(getApplicationContext());
                btn_floatView.setText("floating window");
                final WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                params.format = PixelFormat.RGBA_8888;
                params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                params.width = 100;
                params.height = 100;
                btn_floatView.setOnTouchListener(new View.OnTouchListener()
                {
                    int lastX, lastY;
                    int paramX, paramY;

                    public boolean onTouch(View v, MotionEvent event)
                    {
                        switch (event.getAction())
                        {
                            case MotionEvent.ACTION_DOWN:
                                lastX = (int) event.getRawX();
                                lastY = (int) event.getRawY();
                                paramX = params.x;
                                paramY = params.y;
                                break;
                            case MotionEvent.ACTION_MOVE:
                                int dx = (int) event.getRawX() - lastX;
                                int dy = (int) event.getRawY() - lastY;
                                params.x = paramX + dx;
                                params.y = paramY + dy;
                                wm.updateViewLayout(btn_floatView, params);
                                break;
                        }
                        return true;
                    }
                });

                wm.addView(btn_floatView, params);
            }
        });

        findViewById(R.id.btn_safebox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_safe_box, null);
                final WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                params.format = PixelFormat.RGBA_8888;
                params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
                //params.width = WindowManager.LayoutParams.MATCH_PARENT;
                //params.height = WindowManager.LayoutParams.MATCH_PARENT;

                wm.addView(view, params);
                View viewTop = view.findViewById(R.id.view_top);
                View viewBottom = view.findViewById(R.id.view_bottom);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);

                ObjectAnimator animatorTop = ObjectAnimator.ofFloat(viewTop, "translationY", -displayMetrics.heightPixels/2);
                animatorTop.setDuration(1000);
                animatorTop.start();
                ObjectAnimator animatorBottom = ObjectAnimator.ofFloat(viewBottom, "translationY", displayMetrics.heightPixels/2);
                animatorBottom.setDuration(1000);
                animatorBottom.start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wm.removeView(view);
                    }
                }, 1000);

                //wm.removeView(view);
                //startActivity(new Intent(TempTestActivity.this, SafeBoxActivity.class));
                //GuideLayout gl = new GuideLayout(TempTestActivity.this);
                //gl.sendMessage();

            }
        });

        progressView = (ProgressView) findViewById(R.id.progress_view);

        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressView.setProgress(progress);
                    }
                });

                if(progress < 100){
                    progress += 1;
                }else{
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 50);


        findViewById(R.id.btn_aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(TempTestActivity.this, MyService2.class);
                TempTestActivity.this.bindService(intentService, serviceConnection, BIND_AUTO_CREATE);
            }
        });

        mJobScheduler = (JobScheduler) getSystemService( Context.JOB_SCHEDULER_SERVICE );
        findViewById(R.id.btn_startjob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), JobSchedulerService.class.getName()));
                builder.setPeriodic(3000);
                if(mJobScheduler.schedule(builder.build()) < 0){
                    ////If something goes wrong
                }
            }
        });

        findViewById(R.id.btn_stopjob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJobScheduler.cancelAll();
            }
        });

        lightWaveOvalView = (LightWaveOvalView) findViewById(R.id.wave_oval);

        StatsValueLayout statsValueLayout = (StatsValueLayout) findViewById(R.id.stats_layout);
        StatsValueLayout.StatsValue sv1 = new StatsValueLayout.StatsValue(1, "1", R.drawable.grey_circle, R.drawable.blue_circle);
        StatsValueLayout.StatsValue sv2 = new StatsValueLayout.StatsValue(2, "2", R.drawable.grey_circle, R.drawable.blue_circle);
        StatsValueLayout.StatsValue sv3 = new StatsValueLayout.StatsValue(3, "3", R.drawable.grey_circle, R.drawable.blue_circle);
        StatsValueLayout.StatsValue sv4 = new StatsValueLayout.StatsValue(4, "4+", R.drawable.grey_circle, R.drawable.blue_circle);
        List<StatsValueLayout.StatsValue> statsValues = new ArrayList<>();
        statsValues.add(sv1);
        statsValues.add(sv2);
        statsValues.add(sv3);
        statsValues.add(sv4);
        statsValueLayout.setStatsValues(statsValues);
        statsValueLayout.setValue(-1);
    }

    public void createShortcut(boolean install){
        //Intent shortIntent = new Intent(getApplicationContext(), PhotoSelectActivity.class);
        Intent shortIntent = new Intent();
        shortIntent.setClassName(SHORTCUT_ENTRY_PKG_NAME, SHORTCUT_ENTRY_CLASS_NAME);
        shortIntent.setAction(Intent.ACTION_MAIN);
        //shortIntent.addCategory()

        Intent shortcutIntent = new Intent();
        shortcutIntent.putExtra("duplicate", false);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this, R.drawable.personal_space_1));
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Photo Select");
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortIntent);
        if(install){
            shortcutIntent.setAction(ACTION_INSTALL_SHORTCUT);
        }else{
            shortcutIntent.setAction(ACTION_UNINSTALL_SHORTCUT);
        }
        Log.d(MainActivity.LOG_TAG, (install ? "install" : "uninstall") + "shortcut");
        getApplicationContext().sendBroadcast(shortcutIntent);
    }

    @Override
    public void onDestroy(){
        if(iMyService != null){
            unbindService(serviceConnection);
        }
        super.onDestroy();
    }
}
