package com.example.p11taskmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class TaskReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        int reqCode = intent.getIntExtra("requestCode", 0);
        String task = intent.getStringExtra("task");

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity
                ( context, reqCode, i,
                        PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.BigPictureStyle bigPicture = new NotificationCompat.BigPictureStyle();
        bigPicture.bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.task)).build();


        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Task Manager Reminder");
        builder.setContentText(task);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
        builder.setStyle(bigPicture);
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        builder.setVibrate(new long[] { 1000, 1000});
        builder.setLights(Color.YELLOW, 3000, 3000);
        builder.setAutoCancel(true);

        Notification n = builder.build();


        notificationManager.notify(123, n);
    }//end of onReceive


}//end of class