package com.okode.richlocalnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.getcapacitor.JSObject;


/**
 * Receiver called when notification is dismissed by user
 */
public class RichLocalNotificationDismissReceiver extends BroadcastReceiver {

  private RichLocalNotificationManager richLocalNotificationManager;

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.i("RLN", "Dimissing local notification");
    JSObject dataJson = getRichLocalNotificationManagerInstance(context).handleNotificationActionPerformed(intent);
    RichLocalNotifications.sendNotificationActionPerformed(dataJson);
  }

  private RichLocalNotificationManager getRichLocalNotificationManagerInstance(Context context) {
    if (richLocalNotificationManager == null) {
      this.richLocalNotificationManager = new RichLocalNotificationManager(context);
    }
    return this.richLocalNotificationManager;
  }

}
