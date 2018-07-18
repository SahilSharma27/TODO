package com.example.android.todo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Sahil Sharma on 11-07-2018.
 */

public class MyReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("mychannelid", "Expenses Channel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "myChannelId");
        builder.setContentTitle("TODO Alarm");
        builder.setContentText("Alarm Recieved");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);

        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, intent1, 0);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        manager.notify(1, notification);


    }

}
