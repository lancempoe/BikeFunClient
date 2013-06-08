package com.bikefunfinder.client.shared.Tools;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.gin.Injector;
import com.googlecode.gwtphonegap.client.geolocation.Geolocation;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationOptions;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationWatcher;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 5/8/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeviceTools {
    private static final Geolocation phoneGeoLocation = Injector.INSTANCE.getClientFactory().getPhoneGap().getGeolocation();

    private static  final GeolocationOptions options = new GeolocationOptions();

    static {
        options.setEnableHighAccuracy(true);
        options.setTimeout(5000);
        options.setMaximumAge(15000);
    }

    public static void requestPhoneGeoLoc(NonPhoneGapGeoLocCallback callback) {
        phoneGeoLocation.getCurrentPosition(callback, options);
    }

    public static GeolocationWatcher requestGeoUpdates(NonPhoneGapGeoLocCallback callback) {
         return phoneGeoLocation.watchPosition(options, callback);
    }

    public static void cancelWatcher(GeolocationWatcher watcher) {
        phoneGeoLocation.clearWatch(watcher);
    }
}
