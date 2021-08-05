package com.example.p12taskmanagerwear;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import static android.content.Context.NOTIFICATION_SERVICE;

public class TaskReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        int reqCode = intent.getIntExtra("requestCode", 0);
        String task = intent.getStringExtra("name");

        String description = intent.getStringExtra("description");
        String notification = task + "/n" + description;
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }
// Wear OS Notification ---------------------------------------------------
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity
                ( context, reqCode, i,
                        PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Action action = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Launch Task Manager",
                pIntent).build();

        Intent intentReply = new Intent(context,
                ReplyActivity.class);

        PendingIntent pendingIntentReply = PendingIntent.getActivity
                (context, 0, intentReply,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String [] {"Completed"})
                .build();

        NotificationCompat.Action action2 = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Reply",
                pendingIntentReply)
                .addRemoteInput(ri)
                .build();

        NotificationCompat.WearableExtender extender = new
                NotificationCompat.WearableExtender();
        extender.addAction(action);
        extender.addAction(action2);
// --------------------------------------------------------------------------
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Task");
        builder.setContentText(notification);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        builder.setVibrate(new long[] { 1000, 1000});
        builder.setLights(Color.YELLOW, 3000, 3000);
        builder.setAutoCancel(true);
        builder.extend(extender);
        Notification n = builder.build();


        notificationManager.notify(123, n);
    }//end of onReceive


}//end of class