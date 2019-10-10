package com.okode.richlocalnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.getcapacitor.LogUtils;


/**
 * Receiver called when notification is dismissed by user
 */
public class RichLocalNotificationDismissReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.e(LogUtils.getPluginTag("RLN"), "Dimissing local notification");
  }

}
