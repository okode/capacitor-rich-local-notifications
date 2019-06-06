package com.okode.richlocalnotifications;

import android.content.Context;

import com.getcapacitor.JSObject;

import org.json.JSONException;
import org.json.JSONObject;

public class RichLocalNotification {

    private Integer id;
    private String title;
    private String body;
    private String sound;
    private JSObject extra;
    private String channelId;
    private Integer priority; // Android 7 or lower notifications priority
    private String smallIcon;

    private String source;

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

    public JSObject getExtra() {
        return extra;
    }

    public void setExtra(JSObject extra) {
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

    public int getSmallIcon(Context context) {
        if (this.smallIcon == null) {
            return android.R.drawable.ic_dialog_info;
        }
        return context.getResources().getIdentifier(
                this.smallIcon,
                "drawable",
                context.getPackageName());
    }

    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }

    public String getSmallIcon() {
        return smallIcon;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public static RichLocalNotification buildNotification(JSONObject jsonNotification) {
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
        richNotification.setId(notification.getInteger("id"));
        richNotification.setTitle(notification.getString("title"));
        richNotification.setBody(notification.getString("body"));
        richNotification.setSound(notification.getString("sound"));
        richNotification.setExtra(notification.getJSObject("extra"));
        richNotification.setChannelId(notification.getString("channelId"));
        richNotification.setPriority(notification.getInteger("priority"));
        richNotification.setSmallIcon(notification.getString("smallIcon"));
        return richNotification;
    }

}
