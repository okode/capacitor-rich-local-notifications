import { registerPlugin } from '@capacitor/core';
const RichLocalNotifications = registerPlugin('RichLocalNotifications', {
    web: () => import('./web').then(m => new m.RichLocalNotificationsWeb()),
});
export * from './definitions';
export { RichLocalNotifications };
//# sourceMappingURL=index.js.map