package com.okode.richlocalnotifications;

import android.content.Intent;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginHandle;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONObject;


@CapacitorPlugin()
public class RichLocalNotifications extends Plugin {

    private static Bridge staticBridge = null;

    private RichLocalNotificationManager manager;

    @Override
    public void load() {
        staticBridge = this.bridge;
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
        fireNotificationActionPerformed(dataJson);
    }

    @PluginMethod()
    public void show(PluginCall call) {
        JSONObject notification = call.getObject("notification");
        RichLocalNotification richLocalNotification = RichLocalNotification.Builder
                .from(getContext(), notification)
                .build();
        Integer id = manager.show(call, richLocalNotification);
        call.resolve(new JSObject().put("id", id));
    }

    private void fireNotificationActionPerformed(JSObject dataJson) {
        if (dataJson != null) {
            notifyListeners("richLocalNotificationActionPerformed", dataJson, true);
        }
    }

    protected void setNotificationsManager(RichLocalNotificationManager manager) {
        this.manager = manager;
    }

    public static void sendNotificationActionPerformed(JSObject dataJson) {
        RichLocalNotifications plugin = RichLocalNotifications.getPluginInstance();
        if (plugin != null) {
            plugin.fireNotificationActionPerformed(dataJson);
        }
    }

    public static RichLocalNotifications getPluginInstance() {
        if (staticBridge != null && staticBridge.getWebView() != null) {
            PluginHandle handle = staticBridge.getPlugin("RichLocalNotifications");
            if (handle == null) {
                return null;
            }
            return (RichLocalNotifications) handle.getInstance();
        }
        return null;
    }

}
