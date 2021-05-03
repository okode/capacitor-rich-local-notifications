package com.okode.richlocalnotifications;

import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.LogUtils;

import java.text.ParseException;

public class RichLocalNotificationAttachment {

    private static final String URL = "url";

    private String url;

    public RichLocalNotificationAttachment() { }

    public static RichLocalNotificationAttachment buildFromJson(JSObject jsonAttachment) throws ParseException {
        if (jsonAttachment == null) { return null; }

        RichLocalNotificationAttachment attachment = new RichLocalNotificationAttachment();

        attachment.setUrl(jsonAttachment.getString(URL));

        return attachment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
      boolean isHttpUrl = url != null && (url.startsWith("http") || url.startsWith("https"));
      if (isHttpUrl) {
        this.url = url;
      } else {
        Log.e(LogUtils.getPluginTag("RLN"), "Ignored nonHttp attachment");
      }
    }

}
