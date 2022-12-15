import type { PluginListenerHandle } from "@capacitor/core";
export interface RichLocalNotificationsPlugin {
    show(options: {
        notification: RichLocalNotification;
    }): Promise<RichLocalNotificationShowResult>;
    addListener(eventName: 'richLocalNotificationActionPerformed', listenerFunc: (action: RichLocalNotificationActionPerformed) => void): PluginListenerHandle;
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
    schedule?: RichLocalNotificationSchedule;
    attachment?: RichLocalNotificationAttachment;
}
export interface RichLocalNotificationSchedule {
    at?: Date;
    repeats?: boolean;
    every?: 'year' | 'month' | 'two-weeks' | 'week' | 'day' | 'hour' | 'minute' | 'second';
}
export interface RichLocalNotificationAttachment {
    url: string;
    altText?: string;
}
export interface RichLocalNotificationShowResult {
    id: string;
}
export interface RichLocalNotificationActionPerformed {
    actionId: string;
    notification: RichLocalNotification;
}
