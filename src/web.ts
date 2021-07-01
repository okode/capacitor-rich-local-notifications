import { WebPlugin } from '@capacitor/core';

import type { RichLocalNotificationsPlugin, RichLocalNotification, RichLocalNotificationShowResult } from './definitions';

export class RichLocalNotificationsWeb extends WebPlugin implements RichLocalNotificationsPlugin {

  async show(options: { notification: RichLocalNotification; }): Promise<RichLocalNotificationShowResult> {
    console.log('Show notification', options);
    throw this.unimplemented('Not implemented on web.');
  }

}
