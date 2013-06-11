package com.bikefunfinder.client.shared.Tools;

import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.request.management.CacheStrategy;
import com.bikefunfinder.client.shared.request.management.GeoLocCacheStrategy;
import com.bikefunfinder.client.shared.widgets.LoadingScreen;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.googlecode.gwtphonegap.client.geolocation.Coordinates;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationCallback;
import com.googlecode.gwtphonegap.client.geolocation.Position;
import com.googlecode.gwtphonegap.client.geolocation.PositionError;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

public class NonPhoneGapGeoLocCallback implements GeolocationCallback {
    public enum GeoLocationAccuracy {
        /*
           From AmericanCulturalAssumption:
           (Most?) American cities are laid out with 1/16 mile by 1/8 mile grids.
           (Metric equivalents: 100 meters by 200 meters.)
           Major streets are usually at 1/4, 1/2, or 1 mile intervals.
           (Metric equivalents: 400 meters, 800 meters, or 1.6 km
        */
        GOOD(100), FAIR(500), BAD(501);

        private final int accuracyInMeters;

        private GeoLocationAccuracy(int accuracyInMeters) {
            this.accuracyInMeters = accuracyInMeters;
        }

        public static
        GeoLocationAccuracy qualifyPosition(double accuracy) {

            if(accuracy <= GOOD.accuracyInMeters) {
                return GOOD;
            } else if (accuracy <= FAIR.accuracyInMeters) {
                return FAIR;
            }

            return BAD;
        }
    }

    public interface GeolocationHandler {
        public abstract void onSuccess(GeoLoc geoLoc);
    }

    private final GeolocationHandler callback;
    private final CacheStrategy<GeoLoc> cacheStrategy;

    public NonPhoneGapGeoLocCallback(GeolocationHandler callback) {
        this.callback = callback;
        this.cacheStrategy = GeoLocCacheStrategy.INSTANCE;

        LoadingScreen.openLoaderPanel();
    }

    @Override
    public void onSuccess(final Position position) {

        GeoLoc geoLocToReturn;

        GeoLoc lastGeoLoc = cacheStrategy.getCachedType();
        if(lastGeoLoc==null) {
            geoLocToReturn = buildGeoLocFrom(position);
        } else {
            // we have a geo so we can be picky
            double accuracyInMeters = position.getCoordinates().getAccuracy();
            GeoLocationAccuracy positionQuality = GeoLocationAccuracy.qualifyPosition(accuracyInMeters);

            if(positionQuality == GeoLocationAccuracy.BAD) {
                geoLocToReturn = lastGeoLoc;
            } else {
                geoLocToReturn = buildGeoLocFrom(position);
            }
        }


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
        GeoLoc cachedEntry = cacheStrategy.getCachedType();
        if(cachedEntry!=null) {
            informCallBackOfSuccess(cachedEntry);
        } else {

            if(error.getCode() == PositionError.PERMISSION_DENIED) {
                final NonPhoneGapGeoLocCallback thizz = this;
                Dialogs.alert("Error:", "Permission denied, change settings and continue.", new Dialogs.AlertCallback() {
                    @Override
                    public void onButtonPressed() {
                        refireRequestInAfterSomeTime(thizz);
                    }
                });
            } else {
                refireRequestInAfterSomeTime(this);
            }
        }
    }

    private void refireRequestInAfterSomeTime(final NonPhoneGapGeoLocCallback thizz) {
        Timer timer = new Timer() {
            public void run() {
                DeviceTools.requestPhoneGeoLoc(thizz);
            }
        };

        // Execute the timer to expire 2 seconds in the future
        timer.schedule(2000);
    }
}