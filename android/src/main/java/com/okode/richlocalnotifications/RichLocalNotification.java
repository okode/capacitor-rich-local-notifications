package com.okode.richlocalnotifications;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getcapacitor.JSObject;
import com.getcapacitor.LogUtils;
import com.okode.richlocalnotifications.capacitorrichlocalnotifications.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RichLocalNotification {

    private Integer id;
    private String title;
    private String body;
    private String sound;
    private Map<String, Object> extra;
    private String channelId;
    private Integer priority; // Android 7 or lower notifications priority
    private int smallIconId;
    private String source; // Raw notification

    private RichLocalNotification() { }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Integer getId() {
        return id;
    }

    public String getSound() {
        return sound;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public Integer getPriority() {
        return priority;
    }

    public int getSmallIconId() {
        if (this.smallIconId == 0) {
            return android.R.drawable.ic_dialog_info;
        }
        return this.smallIconId;
    }

    public String getSource() {
        return this.source;
    }

    public static class Builder {

        private static final ObjectMapper MAPPER = new ObjectMapper();

        private static final String ID = "id";
        private static final String TITLE = "title";
        private static final String BODY = "body";
        private static final String SOUND = "sound";
        private static final String EXTRA = "extra";
        private static final String CHANNEL_ID = "channelId";
        private static final String PRIORITY = "priority";
        private static final String SMALL_ICON = "smallIcon";

        @JsonProperty
        private String id;
        @JsonProperty
        private String title;
        @JsonProperty
        private String body;
        @JsonProperty
        private String sound;
        @JsonProperty
        private Map<String, Object> extra;
        @JsonProperty
        private String channelId;
        @JsonProperty
        private Integer priority;
        @JsonProperty
        private String smallIcon;
        @JsonProperty
        private int smallIconId;

        public static Builder from(Context context, JSONObject jsonNotification) {
            Builder builder = new Builder();
            if (jsonNotification == null) {
                return builder;
            }

            JSObject notification;
            try {
                notification = JSObject.fromJSONObject(jsonNotification);
            } catch (JSONException e) {
                return builder;
            }

            builder.setId(notification.getString(ID))
                .setTitle(notification.getString(TITLE))
                .setBody(notification.getString(BODY))
                .setSound(notification.getString(SOUND))
                .setSmallIcon(context, notification.getString(SMALL_ICON))
                .setChannelId(notification.getString(CHANNEL_ID))
                .setPriority(notification.getInteger(PRIORITY));

            try {
                JSONObject extra = notification.getJSONObject(EXTRA);
                if (extra != null) {
                    builder.setExtra(MAPPER.readValue(extra.toString(), HashMap.class));
                }
            } catch (JSONException e) {
                Log.e(LogUtils.getPluginTag("RLN"), "Error getting notification extras");
            } catch (IOException e) {
                Log.e(LogUtils.getPluginTag("RLN"), "Error setting notification extras");
            }

            return builder;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }

        public Builder setExtra(Map<String, Object> extra) {
            this.extra = extra;
            return this;
        }

        public Builder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder setPriority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public Builder setSmallIcon(Context context, String smallIconName) {
            // Setting notification icon
            this.smallIcon = smallIconName;
            if (context != null && this.smallIcon != null) {
                this.smallIconId = context.getResources().getIdentifier(
                    this.smallIcon,"drawable", context.getPackageName());
            }
            return this;
        }

        public Builder setSmallIconId(Context context, int smallIconId) {
            this.smallIconId = smallIconId;
            if (this.smallIconId > 0) {
                this.smallIcon = context.getResources().getResourceEntryName(smallIconId);
            }
            return this;
        }

        public RichLocalNotification build() {
            RichLocalNotification notification = new RichLocalNotification();

            notification.id = getIdAsInteger();
            notification.title = this.title;
            notification.body = this.body;
            notification.sound = this.sound;
            notification.extra = this.extra;
            notification.channelId = this.channelId;
            notification.priority = this.priority;
            notification.smallIconId = smallIconId;

            try {
                notification.source = MAPPER.writeValueAsString(this);
            } catch (IOException e) {
                Log.e(LogUtils.getPluginTag("RLN"), "Error setting notification source", e);
            }

            return notification;
        }

        private Integer getIdAsInteger() {
            Integer notificationId = 0;
            try {
                notificationId = Integer.valueOf(this.id);
            } catch (NumberFormatException e) {
                Log.e(LogUtils.getPluginTag("RLN"), "Invalid notification ID. Using 0 as default one", e);
            }
            return notificationId;
        }

    }

}
