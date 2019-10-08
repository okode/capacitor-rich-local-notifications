package com.okode.richlocalnotifications;

import android.app.NotificationChannel;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RichLocalNotificationChannelBuilder {

    private String id;
    private String name;
    private String description;
    private int importance;
    private boolean showBadge;
    private boolean enableVibration;
    private int lockScreenVisibility;
    private String sound;

    public RichLocalNotificationChannelBuilder(String id, String name, int importance) {
        this.setId(id);
        this.setName(name);
        this.setImportance(importance);
    }

    public RichLocalNotificationChannelBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public RichLocalNotificationChannelBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RichLocalNotificationChannelBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public RichLocalNotificationChannelBuilder setImportance(int importance) {
        this.importance = importance;
        return this;
    }

    public RichLocalNotificationChannelBuilder setShowBadge(boolean showBadge) {
        this.showBadge = showBadge;
        return this;
    }

    public RichLocalNotificationChannelBuilder setEnableVibration(boolean enableVibration) {
        this.enableVibration = enableVibration;
        return this;
    }

    public RichLocalNotificationChannelBuilder setLockScreenVisibility(int lockScreenVisibility) {
        this.lockScreenVisibility = lockScreenVisibility;
        return this;
    }

    public RichLocalNotificationChannelBuilder setSound(String sound) {
        this.sound = sound;
        return this;
    }

    public NotificationChannel build(Context context) {
        NotificationChannel channel = new NotificationChannel(this.id, this.name, this.importance);

        channel.setDescription(this.description);
        channel.setShowBadge(this.showBadge);
        channel.enableVibration(this.enableVibration);
        channel.setLockscreenVisibility(this.lockScreenVisibility);

        // Audio configuration
        Uri soundUri = RichLocalNotificationUtils.getSound(context, this.sound);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE).build();

        channel.setSound(soundUri, audioAttributes);

        return channel;
    }

}
