package com.bikefunfinder.client.shared.Tools;

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.googlecode.gwtphonegap.client.geolocation.Coordinates;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationCallback;
import com.googlecode.gwtphonegap.client.geolocation.Position;
import com.googlecode.gwtphonegap.client.geolocation.PositionError;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 5/8/13
 * Time: 6:58 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class NonPhoneGapGeolocationCallback implements GeolocationCallback {
    @Override
    public void onSuccess(final Position position) {
        GeoLoc geoLoc = GWT.create(GeoLoc.class);
        final Coordinates coordinates = position.getCoordinates();
        geoLoc.setLatitude(Double.toString(coordinates.getLatitude()));
        geoLoc.setLongitude(Double.toString(coordinates.getLongitude()));
        onSuccess(geoLoc);
    }

    @Override
    public void onFailure(final PositionError error) {
        GeoLoc geoLoc = GWT.create(GeoLoc.class);
        Window.alert("Failed to get GeoLocation.  Using Portland as default.");
        geoLoc.setLatitude(Double.toString(ScreenConstants.PORTLAND_LATITUDE));
        geoLoc.setLongitude(Double.toString(ScreenConstants.PORTLAND_LOGITUDE));
        onFailure(geoLoc);
    }

    public abstract void onSuccess(GeoLoc geoLoc);
    public abstract void onFailure(GeoLoc geoLoc);
}
