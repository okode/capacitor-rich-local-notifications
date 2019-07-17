package com.okode.richlocalnotifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.LogUtils;
import com.getcapacitor.PluginCall;
import com.okode.richlocalnotifications.capacitorrichlocalnotifications.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RichLocalNotificationManager {

    private static final String NOTIFICATION_INTENT_KEY = "RichLocalNotificationId";
    private static final String NOTIFICATION_OBJ_INTENT_KEY = "RichLocalNotficationObject";
    private static final String ACTION_INTENT_KEY = "RichLocalNotificationUserAction";
    private static final String DEFAULT_PRESS_ACTION = "tap";

    private Context context;
    private Class<?> mainActivityClazz;

    public RichLocalNotificationManager(Context context) {
        this.context = context;
        this.mainActivityClazz = this.context.getClass();
        createDefaultNotificationChannel();
    }

    public RichLocalNotificationManager(Context context, Class<?> mainActivityClazz) {
        this.context = context;
        this.mainActivityClazz = mainActivityClazz;
        createDefaultNotificationChannel();
    }

    /**
     * Method extecuted when notification is launched by user from the notification bar.
     */
    public JSObject handleNotificationActionPerformed(Intent data) {
        Log.d(LogUtils.getPluginTag("RLN"), "RichLocalNotification received: " + data.getDataString());
        int notificationId = data.getIntExtra(NOTIFICATION_INTENT_KEY, Integer.MIN_VALUE);
        if (notificationId == Integer.MIN_VALUE) {
            Log.d(LogUtils.getPluginTag("RLN"), "Activity started without notification attached");
            return null;
        }
        JSObject dataJson = new JSObject();

        String menuAction = data.getStringExtra(ACTION_INTENT_KEY);
        if (menuAction != DEFAULT_PRESS_ACTION) {
            dismissVisibleNotification(notificationId);
        }
        dataJson.put("actionId", menuAction);
        JSONObject request = null;
        try {
            String notificationJsonString = data.getStringExtra(NOTIFICATION_OBJ_INTENT_KEY);
            if (notificationJsonString != null) {
                request = new JSObject(notificationJsonString);
            }
        } catch (JSONException e) {
            Log.e(LogUtils.getPluginTag("RLN"), "Error getting notification data", e);
        }
        dataJson.put("notification", request);
        return dataJson;
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
        return postNotification(richLocalNotification);
    }

    public Integer postNotification(RichLocalNotification richLocalNotification) {
        if (richLocalNotification == null) { return null; }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder mBuilder = getNotificationBuilder(richLocalNotification);
        createActionIntents(richLocalNotification, mBuilder);
        notificationManager.notify(richLocalNotification.getId(), mBuilder.build());
        return richLocalNotification.getId();
    }

    protected void createDefaultNotificationChannel() {
        createHighPriorityNotificationChannel();
    }

    protected String getDefaultChannelId() {
        return context.getString(R.string.rich_local_notifications_default_channel_id);
    }

    protected String getDefaultChannelName() {
        return context.getString(R.string.rich_local_notifications_default_channel_name);
    }

    protected NotificationCompat.Builder getNotificationBuilder(RichLocalNotification richLocalNotification) {
        String channelId = richLocalNotification.getChannelId() != null ?
                richLocalNotification.getChannelId() : getDefaultChannelId();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(richLocalNotification.getTitle())
                .setContentText(richLocalNotification.getBody())
                .setAutoCancel(true)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);

        if (richLocalNotification.getPriority() != null) {
            mBuilder.setPriority(richLocalNotification.getPriority());
        } else {
            mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
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

        mBuilder.setSmallIcon(richLocalNotification.getSmallIconId());
        return mBuilder;
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
        Intent intent = new Intent(context, mainActivityClazz);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(NOTIFICATION_INTENT_KEY, richLocalNotification.getId());
        intent.putExtra(ACTION_INTENT_KEY, action);
        intent.putExtra(NOTIFICATION_OBJ_INTENT_KEY, richLocalNotification.getSource());
        return intent;
    }

    private void createHighPriorityNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getDefaultChannelId(),
                    getDefaultChannelName(),
                    android.app.NotificationManager.IMPORTANCE_HIGH);
            android.app.NotificationManager notificationManager =
                    context.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void dismissVisibleNotification(int notificationId) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notificationId);
    }

}
