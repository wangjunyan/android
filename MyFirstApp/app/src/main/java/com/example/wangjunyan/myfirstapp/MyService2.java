package com.example.wangjunyan.myfirstapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.example.wangjunyan.myfirstapp.firstlinecode.FirstLineCodeTestActivity;

import java.util.ArrayList;
import java.util.List;

public class MyService2 extends Service {
    private final static String TAG = "MyService2";
    private static final String PACKAGE_SAYHI = "com.example.wangjunyan.myfirstapp";

    private NotificationManager mNotificationManager;
    private boolean mCanRun = true;
    private List<Student> mStudents = new ArrayList<Student>();

    private final IMyService.Stub mBinder = new IMyService.Stub(){

        @Override
        public List<Student> getStudent() throws RemoteException {
            synchronized (mStudents) {
                return mStudents;
            }
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            synchronized (mStudents) {
                if (!mStudents.contains(student)) {
                    mStudents.add(student);
                }
            }
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException{
            String packageName = null;
            String[] packages = MyService2.this.getPackageManager().getPackagesForUid(getCallingUid());
            if(packages != null && packages.length > 0){
                packageName = packages[0];
            }
            Log.d(TAG, "onTransact: " + packageName);
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public void onCreate()
    {
        Thread thr = new Thread(null, new ServiceWorker(), "BackgroundService");
        thr.start();

        synchronized (mStudents) {
            for (int i = 1; i < 6; i++) {
                Student student = new Student();
                student.name = "student#" + i;
                student.age = i * 5;
                mStudents.add(student);
            }
        }

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        super.onCreate();
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, String.format("on bind,intent = %s", intent.toString()));
        displayNotificationMessage("Service has been started");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        mCanRun = false;
        super.onDestroy();
    }

    private void displayNotificationMessage(String message)
    {
        Notification notification = new Notification(R.drawable.icon_checked, message,
                System.currentTimeMillis());
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_ALL;
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, FirstLineCodeTestActivity.class), 0);
        //notification.setLatestEventInfo(this, "My Notification", message,
        //        contentIntent);
        mNotificationManager.notify(0, notification);
    }


    class ServiceWorker implements Runnable
    {
        long counter = 0;

        @Override
        public void run()
        {
            // do background processing here.....
            while (mCanRun)
            {
                Log.d(TAG,  "" + counter);
                counter++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
