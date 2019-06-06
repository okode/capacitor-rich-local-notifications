import { WebPlugin } from '@capacitor/core';
import { RichLocalNotificationsPlugin, RichLocalNotification, RichLocalNotificationShowResult } from './definitions';

export class RichLocalNotificationsWeb extends WebPlugin implements RichLocalNotificationsPlugin {

  constructor() {
    super({
      name: 'RichLocalNotifications',
      platforms: ['web']
    });
  }

  show(options: { notification: RichLocalNotification; }): Promise<RichLocalNotificationShowResult> {
    console.log('Show notification', options);
    throw new Error("Method not implemented.");
  }

}

const RichLocalNotifications = new RichLocalNotificationsWeb();

export { RichLocalNotifications };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(RichLocalNotifications);
