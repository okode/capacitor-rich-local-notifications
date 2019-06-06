package com.okode.richlocalnotifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.getcapacitor.PluginCall;
import com.getcapacitor.plugin.notification.LocalNotificationManager;

public class RichLocalNotificationManager {

    public static final String DEFAULT_NOTIFICATION_CHANNEL_ID = "RichNotifications";
    public static final String DEFAULT_NOTIFICATION_CHANNEL_NAME = "Local notifications";

    private static final String DEFAULT_PRESS_ACTION = "tap";

    private Activity activity;
    private Context context;

    public RichLocalNotificationManager(Activity activity) {
        this.activity = activity;
        this.context = activity;
        createNotificationChannel();
    }

    public void createNotificationChannel() {
        createHighPriorityNotificationChannel();
    }

    public Integer show(PluginCall call, RichLocalNotification richLocalNotification) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        boolean notificationsEnabled = notificationManager.areNotificationsEnabled();
        if (!notificationsEnabled) {
            call.error("Notifications not enabled on this device");
            return null;
        }
        Integer id = richLocalNotification.getId();
        if (richLocalNotification.getId() == null) {
            call.error("RichLocalNotification missing identifier");
            return null;
        }
        dismissVisibleNotification(id);
        buildNotification(notificationManager, richLocalNotification, call);
        return richLocalNotification.getId();
    }

    protected void buildNotification(NotificationManagerCompat notificationManager, RichLocalNotification richLocalNotification, PluginCall call) {
        String channelId = richLocalNotification.getChannelId() != null ?
                richLocalNotification.getChannelId() : DEFAULT_NOTIFICATION_CHANNEL_ID;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.context, channelId)
                .setContentTitle(richLocalNotification.getTitle())
                .setContentText(richLocalNotification.getBody())
                .setAutoCancel(true)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);

        if (richLocalNotification.getPriority() != null) {
            mBuilder.setPriority(richLocalNotification.getPriority());
        } else {
            mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }

        String sound = richLocalNotification.getSound();
        if (sound != null) {
            Uri soundUri = Uri.parse(sound);
            // Grant permission to use sound
            context.grantUriPermission(
                    "com.android.systemui", soundUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
            mBuilder.setSound(soundUri);
        }

        mBuilder.setVisibility(Notification.VISIBILITY_PRIVATE);
        mBuilder.setOnlyAlertOnce(true);

        mBuilder.setSmallIcon(richLocalNotification.getSmallIcon(context));
        createActionIntents(richLocalNotification, mBuilder);
        // notificationId is a unique int for each localNotification that you must define
        Notification buildNotification = mBuilder.build();
        notificationManager.notify(richLocalNotification.getId(), buildNotification);
    }

    protected String getOpenAction() {
        return DEFAULT_PRESS_ACTION;
    }

    // Create intent for open action
    protected void createActionIntents(RichLocalNotification richLocalNotification, NotificationCompat.Builder mBuilder) {
        // Open intent
        Intent intent = buildIntent(richLocalNotification, getOpenAction());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, richLocalNotification.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
    }

    protected Intent buildIntent(RichLocalNotification richLocalNotification, String action) {
        Intent intent = new Intent(context, activity.getClass());
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(LocalNotificationManager.NOTIFICATION_INTENT_KEY, richLocalNotification.getId());
        intent.putExtra(LocalNotificationManager.ACTION_INTENT_KEY, action);
        intent.putExtra(LocalNotificationManager.NOTIFICATION_OBJ_INTENT_KEY, richLocalNotification.getSource());
        return intent;
    }

    private void createHighPriorityNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    DEFAULT_NOTIFICATION_CHANNEL_ID,
                    DEFAULT_NOTIFICATION_CHANNEL_NAME,
                    android.app.NotificationManager.IMPORTANCE_HIGH);
            android.app.NotificationManager notificationManager =
                    context.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void dismissVisibleNotification(int notificationId) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.context);
        notificationManager.cancel(notificationId);
    }

}
