package com.bikefunfinder.client.shared.Tools;
/*
 * @author: tneuwerth
 * @created 6/13/13 11:40 PM
 */

import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.request.management.GeoLocCacheStrategy;
import com.bikefunfinder.client.shared.widgets.LoadingScreen;
import com.google.gwt.core.client.GWT;
import com.googlecode.gwtphonegap.client.geolocation.Coordinates;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationCallback;
import com.googlecode.gwtphonegap.client.geolocation.Position;
import com.googlecode.gwtphonegap.client.geolocation.PositionError;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

public abstract class MustGetGoodGeo implements GeolocationCallback {

    private final NonPhoneGapGeoLocCallback.GeolocationHandler callback;
    private final GeoLocCacheStrategy cacheStrategy = GeoLocCacheStrategy.INSTANCE;
    private int numberOfTimesTried;

    public MustGetGoodGeo(NonPhoneGapGeoLocCallback.GeolocationHandler callback) {
        this.callback = callback;
    }

    public abstract void killingCall();

    @Override
    public void onSuccess(final Position position) {


        if(position == null || position.getCoordinates() == null) {
            refireRequestInAfterSomeTime();
            return;
        }
        // we have a geo so we can be picky
        double accuracyInMeters = position.getCoordinates().getAccuracy();
        NonPhoneGapGeoLocCallback.GeoLocationAccuracy positionQuality = NonPhoneGapGeoLocCallback.GeoLocationAccuracy.qualifyPosition(accuracyInMeters);


        if(positionQuality == NonPhoneGapGeoLocCallback.GeoLocationAccuracy.BAD) {
            refireRequestInAfterSomeTime();
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
        LoadingScreen.closeLoader();
        callback.onSuccess(objectPayload);
    }

    @Override
    public void onFailure(final PositionError error) {
        if(error.getCode() == PositionError.PERMISSION_DENIED) {
            Dialogs.alert("Error:", "Permission denied, change settings and continue.", new Dialogs.AlertCallback() {
                @Override
                public void onButtonPressed() {
                    refireRequestInAfterSomeTime();
                }
            });
        } else {
            refireRequestInAfterSomeTime();
        }

    }

    private void refireRequestInAfterSomeTime() {
        if(numberOfTimesTried>2) {
            killingCall();
        } else {
            numberOfTimesTried ++;
        }
    }
}