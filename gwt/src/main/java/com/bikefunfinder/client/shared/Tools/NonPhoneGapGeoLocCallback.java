package com.bikefunfinder.client.shared.Tools;

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.request.ratsnest.CacheStrategy;
import com.bikefunfinder.client.shared.request.ratsnest.RequestCallbackSorter;
import com.bikefunfinder.client.shared.widgets.LoadingScreen;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.googlecode.gwtphonegap.client.geolocation.Coordinates;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationCallback;
import com.googlecode.gwtphonegap.client.geolocation.Position;
import com.googlecode.gwtphonegap.client.geolocation.PositionError;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NonPhoneGapGeoLocCallback implements GeolocationCallback {

    public interface GeolocationHandler {
        public abstract void onSuccess(GeoLoc geoLoc);
    }

    private final GeolocationHandler callback;
    private final CacheStrategy<GeoLoc> cacheStrategy;

    public NonPhoneGapGeoLocCallback(GeolocationHandler callback,
                                     CacheStrategy<GeoLoc> cacheStrategy) {
        this.callback = callback;
        this.cacheStrategy = cacheStrategy;

        LoadingScreen.openLoaderPanel();
    }

    @Override
    public void onSuccess(final Position position) {
        GeoLoc geoLoc = buildGeoLocFrom(position);
        cacheStrategy.cacheType(geoLoc);
        informCallBackOfSuccess(geoLoc);
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
                Window.alert("Permission denied, change settings and continue.");
            }

            refireRequestInAfterSomeTime(this);
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
