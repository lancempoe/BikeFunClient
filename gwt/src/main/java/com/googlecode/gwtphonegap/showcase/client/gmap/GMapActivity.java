package com.googlecode.gwtphonegap.showcase.client.gmap;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.geolocation.*;
import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMapActivity extends NavBaseActivity implements GMapView.Presenter {
    private final ClientFactory clientFactory;

    private final Geolocation geolocation;
    private GeolocationWatcher watcher = null;

    public GMapActivity(final ClientFactory clientFactory) {
        super(clientFactory);
        this.clientFactory = clientFactory;
        geolocation = clientFactory.getPhoneGap().getGeolocation();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final Place place = clientFactory.getPlaceController().getWhere();
        if (!(place instanceof GMapPlace)) {
            throw new IllegalStateException();
        }

        final GMapView geoMapView = null;//clientFactory.getGMapView();
        geoMapView.setPresenter(this);
        panel.setWidget(geoMapView);

        startWatching();
    }

    private void startWatching() {
        final GeolocationOptions options = new GeolocationOptions();
        options.setEnableHighAccuracy(true);
        options.setTimeout(10000);
        options.setMaximumAge(1000);
        final GeolocationCallback geolocationCallback = new GeolocationCallback() {
            Integer count = 0;

            @Override
            public void onSuccess(final Position position) {
                if (watcher != null) {
                    final Coordinates coordinates = position.getCoordinates();
                    final double latitude = coordinates.getLatitude();
                    final double longitude = coordinates.getLongitude();
                    final double altitude = coordinates.getAltitude();
                    final double accuracy = coordinates.getAccuracy();
                    final double altitudeAccuracy = coordinates.getAltitudeAccuracy();
                    final double heading = coordinates.getHeading();
                    final double speed = coordinates.getSpeed();

                    setView(++count, latitude, longitude, altitude, accuracy, altitudeAccuracy, heading, speed);
                }
            }

            @Override
            public void onFailure(final PositionError error) {
                Window.alert("error code[" + error.getCode()+ "], msg[" + error.getMessage() + "]");
            }
        };
        watchPosition(options, geolocationCallback);
    }

    private synchronized void clearWatch() {
        if (watcher != null) {
            geolocation.clearWatch(watcher);
            watcher = null;
        }
    }

    private synchronized void watchPosition(final GeolocationOptions options, final GeolocationCallback callback) {
        clearWatch();
        watcher = geolocation.watchPosition(options, callback);
    }

    private void clearView() {
        final GMapView gMapView = null;//clientFactory.getGMapView();

        gMapView.clearMapInfo();
        gMapView.refresh();
    }

    private void setView(final int count, final double latitude, final double longitude, final double altitude,
                         final double accuracy, final double altitudeAccuracy, final double heading, final double speed) {
        final GMapView geoCheckinMapView = null;//

        geoCheckinMapView.setMapInfo(latitude, longitude, accuracy);
        geoCheckinMapView.refresh();
    }
}
