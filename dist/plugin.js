var capacitorExitApp = (function (exports, core) {
    'use strict';

    const RichLocalNotifications = core.registerPlugin('RichLocalNotifications', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.RichLocalNotificationsWeb()),
    });

    class RichLocalNotificationsWeb extends core.WebPlugin {
        async show(options) {
            console.log('Show notification', options);
            throw this.unimplemented('Not implemented on web.');
        }
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        RichLocalNotificationsWeb: RichLocalNotificationsWeb
    });

    exports.RichLocalNotifications = RichLocalNotifications;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

}({}, capacitorExports));
//# sourceMappingURL=plugin.js.map
