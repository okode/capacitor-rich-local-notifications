package com.okode.richlocalnotifications;

import android.util.Log;

import com.getcapacitor.JSObject;

import java.text.ParseException;

public class RichLocalNotificationAttachment {

    private static final String URL = "url";
    private static final String ALT_TEXT = "altText";

    private String url;
    private String altText;

    public RichLocalNotificationAttachment() { }

    public static RichLocalNotificationAttachment buildFromJson(JSObject jsonAttachment) throws ParseException {
        if (jsonAttachment == null) { return null; }

        RichLocalNotificationAttachment attachment = new RichLocalNotificationAttachment();

        attachment.setUrl(jsonAttachment.getString(URL));
        attachment.setAltText(jsonAttachment.getString(ALT_TEXT));

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
          Log.e("RLN", "Ignored nonHttp attachment");
        }
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

}
