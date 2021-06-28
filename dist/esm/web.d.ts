import { WebPlugin } from '@capacitor/core';
import type { RichLocalNotificationsPlugin, RichLocalNotification, RichLocalNotificationShowResult } from './definitions';
export declare class RichLocalNotificationsWeb extends WebPlugin implements RichLocalNotificationsPlugin {
    show(options: {
        notification: RichLocalNotification;
    }): Promise<RichLocalNotificationShowResult>;
}
