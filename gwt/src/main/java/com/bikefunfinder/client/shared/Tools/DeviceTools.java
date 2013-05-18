package com.bikefunfinder.client.shared.Tools;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.googlecode.gwtphonegap.client.geolocation.*;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 5/8/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeviceTools {

    public static void getPhoneGeoLoc(ClientFactory clientFactory,
                                        NonPhoneGapGeoLocCallback callback) {

        final GeolocationOptions options = new GeolocationOptions();
        options.setEnableHighAccuracy(true);
        options.setTimeout(3000);
        options.setMaximumAge(1000);

        Geolocation phoneGeoLocation = clientFactory.getPhoneGap().getGeolocation();
        phoneGeoLocation.getCurrentPosition(callback, options);
    }
}
