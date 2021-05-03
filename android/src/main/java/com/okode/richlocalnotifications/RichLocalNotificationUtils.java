package com.okode.richlocalnotifications;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.getcapacitor.LogUtils;

import java.net.HttpURLConnection;
import java.net.URL;

public class RichLocalNotificationUtils {

    public static final String SOUND_SYSTEM_RINGTONE = "system_ringtone";

    private RichLocalNotificationUtils() { }

    public static Uri getSound(Context context, String sound) {
        Uri soundUri = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;

        if (SOUND_SYSTEM_RINGTONE.equalsIgnoreCase(sound)) {
            soundUri = android.provider.Settings.System.DEFAULT_RINGTONE_URI;
        } else if (sound != null && !sound.isEmpty()) {
            soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/" + sound);
        }

        return soundUri;
    }

    public static Bitmap createImageBitmap(String url) {
      try {
        URL var1 = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)var1.openConnection();
        connection.setDoInput(true);
        connection.setReadTimeout(30000);
        connection.setConnectTimeout(30000);
        connection.connect();
        return BitmapFactory.decodeStream(connection.getInputStream());
      } catch (Exception e) {
        Log.e(LogUtils.getPluginTag("RLN"), "Unable to download image", e);
        return null;
      }
    }

}
