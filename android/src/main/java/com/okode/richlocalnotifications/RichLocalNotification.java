package com.okode.richlocalnotifications;

import android.content.Context;

import com.getcapacitor.JSObject;

import org.json.JSONException;
import org.json.JSONObject;

public class RichLocalNotification {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String SOUND = "sound";
    public static final String EXTRA = "extra";
    public static final String CHANNEL_ID = "channelId";
    public static final String PRIORITY = "priority";
    public static final String SMALL_ICON = "smallIcon";

    private Integer id;
    private String title;
    private String body;
    private String sound;
    private JSONObject extra;
    private String channelId;
    private Integer priority; // Android 7 or lower notifications priority
    private int smallIconId;

    private String source;

    private RichLocalNotification() { }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public JSONObject getExtra() {
        return extra;
    }

    public void setExtra(JSONObject extra) {
        this.extra = extra;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public int getSmallIconId() {
        if (this.smallIconId == 0) {
            return android.R.drawable.ic_dialog_info;
        }
        return this.smallIconId;
    }

    public void setSmallIconId(int smallIconId) {
        this.smallIconId = smallIconId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public static RichLocalNotification buildNotification(Context context, JSONObject jsonNotification) {
        if (jsonNotification == null) {
            return null;
        }

        JSObject notification = null;
        try {
            notification = JSObject.fromJSONObject(jsonNotification);
        } catch (JSONException e) {
            return null;
        }

        RichLocalNotification richNotification = new RichLocalNotification();
        richNotification.setSource(notification.toString());
        richNotification.setId(notification.getInteger(ID));
        richNotification.setTitle(notification.getString(TITLE));
        richNotification.setBody(notification.getString(BODY));
        richNotification.setSound(notification.getString(SOUND));
        try {
            richNotification.setExtra(notification.getJSONObject(EXTRA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        richNotification.setChannelId(notification.getString(CHANNEL_ID));
        richNotification.setPriority(notification.getInteger(PRIORITY));
        String smallIconName = notification.getString(SMALL_ICON);
        if (context != null && smallIconName != null) {
            int smallIconId = context.getResources().getIdentifier(
                    smallIconName,"drawable", context.getPackageName());
            richNotification.setSmallIconId(smallIconId);
        }
        return richNotification;
    }

}
