package com.Meenan.Term_App.UI;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.Meenan.Term_App.R;

public class CourseReceiver extends BroadcastReceiver {
    String channel_id = "Course Notification";
    static int notifcationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(intent.getStringExtra("courseNotification"))
                .setContentTitle("Course Notification")
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifcationID++, n);
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = "Course Channel";
        String descr = "Notication for Course";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(descr);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
