package com.bikefunfinder.client.shared.Tools;
/*
 * @author: tneuwerth
 * @created 6/13/13 11:40 PM
 */

import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.request.management.GeoLocCacheStrategy;
import com.bikefunfinder.client.shared.widgets.LoadingScreen;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.googlecode.gwtphonegap.client.geolocation.Coordinates;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationCallback;
import com.googlecode.gwtphonegap.client.geolocation.Position;
import com.googlecode.gwtphonegap.client.geolocation.PositionError;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

import java.util.Date;

public abstract class MustGetGoodGeo implements GeolocationCallback {

    private final NonPhoneGapGeoLocCallback.GeolocationHandler callback;
    private final GeoLocCacheStrategy cacheStrategy = GeoLocCacheStrategy.INSTANCE;
    private final int timeToWaitBeforeKillingCall = 5000;

    public MustGetGoodGeo(NonPhoneGapGeoLocCallback.GeolocationHandler callback) {
        this.callback = callback;
        stopIfGeoDoesntChange.schedule(timeToWaitBeforeKillingCall);
    }

    private long lastGeoGet;
    private final Timer stopIfGeoDoesntChange = new Timer() {
        @Override
        public void run() {
            long now = (new Date()).getTime();
            long elapsed = now - lastGeoGet;
            if(elapsed>=timeToWaitBeforeKillingCall) {
                killingCall();
            } else {
                stopIfGeoDoesntChange.schedule(timeToWaitBeforeKillingCall);
            }
        }
    };

    public abstract void killingCall();

    @Override
    public void onSuccess(final Position position) {
        lastGeoGet = (new Date()).getTime();

        if(position == null || position.getCoordinates() == null) {
            return;
        }
        // we have a geo so we can be picky
        double accuracyInMeters = position.getCoordinates().getAccuracy();
        NonPhoneGapGeoLocCallback.GeoLocationAccuracy positionQuality = NonPhoneGapGeoLocCallback.GeoLocationAccuracy.qualifyPosition(accuracyInMeters);


        if(positionQuality == NonPhoneGapGeoLocCallback.GeoLocationAccuracy.BAD) {
            return;
        }
        GeoLoc geoLocToReturn = buildGeoLocFrom(position);

        cacheStrategy.cacheType(geoLocToReturn);
        informCallBackOfSuccess(geoLocToReturn);
    }

    private GeoLoc buildGeoLocFrom(Position position) {
        GeoLoc geoLoc = GWT.create(GeoLoc.class);
        final Coordinates coordinates = position.getCoordinates();
        geoLoc.setLatitude(coordinates.getLatitude());
        geoLoc.setLongitude(coordinates.getLongitude());
        return geoLoc;
    }

    private void informCallBackOfSuccess(GeoLoc objectPayload) {
        stopIfGeoDoesntChange.cancel();
        LoadingScreen.closeLoader();
        callback.onSuccess(objectPayload);
    }

    @Override
    public void onFailure(final PositionError error) {
        if(error.getCode() == PositionError.PERMISSION_DENIED) {
            Dialogs.alert("Error:", "Permission denied, change settings and continue.", new Dialogs.AlertCallback() {
                @Override
                public void onButtonPressed() {

                }
            });
        }

    }
}