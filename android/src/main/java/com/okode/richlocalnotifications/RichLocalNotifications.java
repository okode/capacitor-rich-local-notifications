package com.okode.richlocalnotifications;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.plugin.LocalNotifications;
import com.getcapacitor.plugin.notification.LocalNotification;
import com.getcapacitor.plugin.notification.LocalNotificationManager;
import com.getcapacitor.plugin.notification.NotificationStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@NativePlugin()
public class RichLocalNotifications extends Plugin {

    protected RichLocalNotificationManager manager;

    @Override
    public void load() {
        manager = new RichLocalNotificationManager(getActivity());
        manager.createNotificationChannel();
    }

    @PluginMethod()
    public void show(PluginCall call) {
        JSONObject notification = call.getObject("notification");
        RichLocalNotification richLocalNotification = RichLocalNotification.buildNotification(notification);
        if (richLocalNotification == null) {
            call.error("Provided notification format is invalid");
            return;
        }
        Integer id = manager.show(call, richLocalNotification);
        call.success(new JSObject().put("id", id));
    }

    protected void setNotificationsManager(RichLocalNotificationManager manager) {
        this.manager = manager;
    }

}
