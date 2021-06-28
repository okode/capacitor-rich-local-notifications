import { WebPlugin } from '@capacitor/core';
export class RichLocalNotificationsWeb extends WebPlugin {
    show(options) {
        console.log('Show notification', options);
        return Promise.reject('Method not implemented.');
    }
}
//# sourceMappingURL=web.js.map