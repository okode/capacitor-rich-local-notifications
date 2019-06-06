import { WebPlugin } from '@capacitor/core';
import { RichLocalNotificationsPlugin, RichLocalNotification, RichLocalNotificationShowResult } from './definitions';
export declare class RichLocalNotificationsWeb extends WebPlugin implements RichLocalNotificationsPlugin {
    constructor();
    show(options: {
        notification: RichLocalNotification;
    }): Promise<RichLocalNotificationShowResult>;
}
declare const RichLocalNotifications: RichLocalNotificationsWeb;
export { RichLocalNotifications };
