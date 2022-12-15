import { WebPlugin } from '@capacitor/core';
export class RichLocalNotificationsWeb extends WebPlugin {
    async show(options) {
        console.log('Show notification', options);
        throw this.unimplemented('Not implemented on web.');
    }
}
//# sourceMappingURL=web.js.map