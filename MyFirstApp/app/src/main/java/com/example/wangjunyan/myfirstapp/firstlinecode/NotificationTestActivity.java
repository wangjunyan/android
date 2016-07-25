package com.example.wangjunyan.myfirstapp.firstlinecode;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wangjunyan.myfirstapp.R;

public class NotificationTestActivity extends Activity {

    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);
        btnSend = (Button)findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new Notification(R.drawable.notify, "This is ticker text", System.currentTimeMillis());
                long[] vibrates = {0, 1000, 1000, 1000};
                notification.vibrate = vibrates;
                notification.ledARGB = Color.GREEN;
                notification.ledOnMS = 1000;
                notification.ledOffMS = 1000;
                notification.flags = Notification.FLAG_SHOW_LIGHTS;
                Intent intent = new Intent(NotificationTestActivity.this, ContactTestActivity.class);
                PendingIntent pi = PendingIntent.getActivity(NotificationTestActivity.this, 0 , intent, PendingIntent.FLAG_CANCEL_CURRENT);
                notification.setLatestEventInfo(NotificationTestActivity.this, "This is content title", "This is content text", pi);
                notificationManager.notify(1, notification);
            }
        });
    }

}
