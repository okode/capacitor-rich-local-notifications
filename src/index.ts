import { registerPlugin } from '@capacitor/core';

import type { RichLocalNotificationsPlugin } from './definitions';

const RichLocalNotifications = registerPlugin<RichLocalNotificationsPlugin>('RichLocalNotifications', {
    web: () => import('./web').then(m => new m.RichLocalNotificationsWeb()),
});

export * from './definitions';
export { RichLocalNotifications };
