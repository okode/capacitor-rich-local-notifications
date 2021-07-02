import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitor.ionicframework.com/docs/plugins/ios
 */
@objc(RichLocalNotifications)
public class RichLocalNotifications: CAPPlugin {

    @objc func show(_ call: CAPPluginCall) {
        call.reject("Method not implemented")
    }
}
