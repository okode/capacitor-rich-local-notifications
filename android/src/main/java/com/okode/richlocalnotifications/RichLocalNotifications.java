package com.okode.richlocalnotifications;

import android.content.Intent;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import org.json.JSONObject;


@NativePlugin()
public class RichLocalNotifications extends Plugin {

    private RichLocalNotificationManager manager;

    @Override
    public void load() {
        manager = new RichLocalNotificationManager(getActivity());
        manager.createDefaultNotificationChannel();
    }

    @Override
    protected void handleOnNewIntent(Intent data) {
        super.handleOnNewIntent(data);
        if (!Intent.ACTION_MAIN.equals(data.getAction())) {
            return;
        }
        JSObject dataJson = manager.handleNotificationActionPerformed(data);
        if (dataJson != null) {
            notifyListeners("richLocalNotificationActionPerformed", dataJson, true);
        }
    }

    @PluginMethod()
    public void show(PluginCall call) {
        JSONObject notification = call.getObject("notification");
        RichLocalNotification richLocalNotification = RichLocalNotification.Builder
                .from(getContext(), notification)
                .build();
        Integer id = manager.show(call, richLocalNotification);
        call.success(new JSObject().put("id", id));
    }

    protected void setNotificationsManager(RichLocalNotificationManager manager) {
        this.manager = manager;
    }

}
