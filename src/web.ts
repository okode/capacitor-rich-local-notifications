import { WebPlugin } from '@capacitor/core';

import type { RichLocalNotificationsPlugin, RichLocalNotification, RichLocalNotificationShowResult } from './definitions';

export class RichLocalNotificationsWeb extends WebPlugin implements RichLocalNotificationsPlugin {

  show(options: { notification: RichLocalNotification; }): Promise<RichLocalNotificationShowResult> {
    console.log('Show notification', options);
    return Promise.reject('Method not implemented.');
  }

}
