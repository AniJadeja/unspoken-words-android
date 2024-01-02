package com.penofdreams.unspokenwords.utils;


import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.penofdreams.unspokenwords.R;


// This class is used to post notification to the user
public class PostNotification {
    String TAG = "Flow -> PostNotification";
    Context context;
    Activity activity;
    int notificationId = 1;

    public PostNotification(Context context, Activity activity) {
        Log.d(TAG, "Flow -> PostNotification: inside constructor");
        this.context = context;
        this.activity = activity;
        createNotificationChannel();
    }

    // Create notification channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = context.getString(R.string.channel_name);
            String channelDescription = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(context.getString(R.string.channel_id), channelName, importance);
            channel.setDescription(channelDescription);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


    // Post notification to the user
    public void postNotification(String senderName, String senderMessage) {
        Log.d(TAG, "sendNotification: inside notification sending");
        Intent wearActionIntent = new Intent(context, ActionReceiver.class);
        wearActionIntent.setAction("OPEN_MESSAGES");
        PendingIntent wearActionPendingIntent = PendingIntent.getBroadcast(context, 0, wearActionIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.channel_id));
        builder.setContentTitle(senderName);
        builder.setContentText(senderMessage);
        builder.setSmallIcon(R.drawable.logo);
        builder.extend(new NotificationCompat.WearableExtender().addAction(new NotificationCompat.Action(
                R.drawable.logo, context.getString(R.string.open_messages), wearActionPendingIntent
        )));
        builder.setAutoCancel(true);
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            int permissionState = ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS);
            // If the permission is not granted, request it.
            if (permissionState == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        Log.d(TAG, "notification sent ");
        compat.notify( notificationId , builder.build());
        notificationId++;
    }
}
