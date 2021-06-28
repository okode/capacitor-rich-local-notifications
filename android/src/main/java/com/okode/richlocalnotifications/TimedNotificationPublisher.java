package com.okode.richlocalnotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.getcapacitor.Logger;

/**
 * Class used to create notification from timer event
 * Note: Class is being registered in Android manifest as broadcast receiver
 */
public class TimedNotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_KEY = "RichNotificationPublisher.notification";

    /**
     * Restore and present notification
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION_KEY);
        int id = intent.getIntExtra(RichLocalNotificationManager.NOTIFICATION_INTENT_KEY, Integer.MIN_VALUE);
        if (id == Integer.MIN_VALUE) {
            Logger.error(Logger.tags("LN"), "No valid id supplied", null);
        }
        notificationManager.notify(id, notification);
    }

}
