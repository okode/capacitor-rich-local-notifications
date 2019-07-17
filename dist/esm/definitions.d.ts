import { PluginListenerHandle } from "@capacitor/core";
declare module "@capacitor/core" {
    interface PluginRegistry {
        RichLocalNotifications: RichLocalNotificationsPlugin;
    }
}
export interface RichLocalNotificationsPlugin {
    show(options: {
        notification: RichLocalNotification;
    }): Promise<RichLocalNotificationShowResult>;
    addListener(eventName: 'richLocalNotificationActionPerformed', listenerFunc: (notification: RichLocalNotification) => void): PluginListenerHandle;
}
export interface RichLocalNotification {
    id: number;
    title: string;
    body: string;
    sound?: string;
    extra?: any;
    channelId?: string;
    priority?: number;
    smallIcon?: string;
}
export interface RichLocalNotificationShowResult {
    id: string;
}
