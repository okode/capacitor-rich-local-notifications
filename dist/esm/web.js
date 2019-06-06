import { WebPlugin } from '@capacitor/core';
export class RichLocalNotificationsWeb extends WebPlugin {
    constructor() {
        super({
            name: 'RichLocalNotifications',
            platforms: ['web']
        });
    }
    show(options) {
        console.log('Show notification', options);
        throw new Error("Method not implemented.");
    }
}
const RichLocalNotifications = new RichLocalNotificationsWeb();
export { RichLocalNotifications };
import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(RichLocalNotifications);
//# sourceMappingURL=web.js.map