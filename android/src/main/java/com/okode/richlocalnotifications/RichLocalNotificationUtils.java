package com.okode.richlocalnotifications;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

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
}
