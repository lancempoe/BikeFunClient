package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.bikefunfinder.client.shared.request.SearchByProximityRequest;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.geolocation.*;
import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMapActivity extends NavBaseActivity implements GMapDisplay.Presenter {
    private final ClientFactory<GMapDisplay> clientFactory;

    private final Geolocation geolocation;
    private GeolocationWatcher watcher = null;
    private List<BikeRide> currentList;
    private final GMapActivity itsAMeMario = this;

    public GMapActivity(final ClientFactory clientFactory) {
        super(clientFactory);
        this.clientFactory = clientFactory;
        geolocation = clientFactory.getPhoneGap().getGeolocation();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final GMapDisplay geoMapView = clientFactory.getDisplay(this);
        geoMapView.setPresenter(this);
        panel.setWidget(geoMapView);

        startWatching();  //naughty! ?? or maybenot =D
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
                Window.alert("Failed to get GeoLocation. error code[" + error.getCode()+ "], msg[" + error.getMessage() + "]");
                final GMapDisplay geoMapView = clientFactory.getDisplay(itsAMeMario);
                fireRequestForHereAndNow(
                    geoMapView,
                    ScreenConstants.PORTLAND_LATITUDE,
                    ScreenConstants.PORTLAND_LONGITUDE,
                    0
                );
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
        final GMapDisplay gMapView = clientFactory.getDisplay(this);

        gMapView.clearMapInfo();
        gMapView.refresh();
    }

    private void setView(final int count, final double latitude, final double longitude, final double altitude,
                         final double accuracy, final double altitudeAccuracy, final double heading, final double speed) {
        final GMapDisplay geoMapView = clientFactory.getDisplay(this);
        fireRequestForHereAndNow(geoMapView, latitude, longitude, accuracy);
    }


    private int calledTimes = 0;
    private void fireRequestForHereAndNow(final GMapDisplay display, final double latitude,
                                          final double longitude,
                                          final double altitudeAccuracy) {

        SearchByProximityRequest.Callback callback = new SearchByProximityRequest.Callback() {
            @Override
            public void onError() {
                Window.alert("Oops, your BFF will be back shortly.");
                GeoLoc phonesGeoLoc = GWT.create(GeoLoc.class);
                phonesGeoLoc.setLatitude(Double.toString(latitude));
                phonesGeoLoc.setLongitude(Double.toString(longitude));
                display.setMapInfo(phonesGeoLoc, altitudeAccuracy,
                        new ArrayList<BikeRide>(),
                        "City Unknown");
                display.refresh();
            }

            @Override
            public void onResponseReceived(Root root) {
                currentList =  Extractor.getBikeRidesFrom(root);
                final String city = getCityNameFromRoot(root);
                GeoLoc phonesGeoLoc = GWT.create(GeoLoc.class);
                phonesGeoLoc.setLatitude(Double.toString(latitude));
                phonesGeoLoc.setLongitude(Double.toString(longitude));
                display.setMapInfo(phonesGeoLoc, altitudeAccuracy,
                        currentList,
                        city);
                display.refresh();
            }

            private String getCityNameFromRoot(Root root) {
                if(root == null) {
                    return "Sorry, No Rides";
                }

                if(root.getClosestLocation()==null) {
                    return "Unknown City";
                }

                String cityName = root.getClosestLocation().getCity();
                if(cityName==null || cityName.isEmpty()) {
                    return "Unknown City";
                }

                return cityName;
            }
        };
        SearchByProximityRequest.Builder request = new SearchByProximityRequest.Builder(callback);

        if(calledTimes == 0) {
            request.latitude(latitude).longitude(longitude).sendAndDebug();
            calledTimes++;
        } else {
            request.latitude(latitude).longitude(longitude).send();
            calledTimes++;
        }

        if(calledTimes>100) {
            calledTimes=0;
        }

    }

    @Override
    public void moreRideDetilsScreenRequested(BikeRide bikeRide) {
        clientFactory.getPlaceController().goTo(new EventScreenPlace(bikeRide));
    }

    @Override
    public String provideTokenHrefFor(BikeRide bikeRide) {
        if(bikeRide==null) {
            return "";
        }

        String hashToken = clientFactory.getPlaceHistoryMapper().getToken(new EventScreenPlace(bikeRide));
        UrlBuilder urlBuilder = Window.Location.createUrlBuilder().setHash(hashToken);
        return urlBuilder.buildString();
    }
}
