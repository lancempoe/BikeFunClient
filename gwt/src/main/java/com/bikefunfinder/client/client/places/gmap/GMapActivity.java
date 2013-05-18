package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
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
import com.googlecode.gwtphonegap.client.geolocation.Geolocation;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationCallback;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationOptions;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationWatcher;
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
    private final Injector injector = Injector.INSTANCE;
    private final ClientFactory<GMapDisplay> clientFactory;

    private final Geolocation geolocation;
    private GeolocationWatcher watcher = null;
    private final GMapActivity itsAMeMario = this;
    private final RamObjectCache ramObjectCache;
    private List<BikeRide> currentList;
    private BikeRide bikeRide;
    private String pageName;

    public GMapActivity(String pageName, BikeRide bikeRide) {
        this.ramObjectCache = injector.getRamObjectCache();
        this.clientFactory = injector.getClientFactory();
        this.geolocation = clientFactory.getPhoneGap().getGeolocation();  //TODO WHY HAVE THIS AND THE CALL DOWN BELOW???
        this.bikeRide = bikeRide;
        this.pageName = pageName;
        setupDisplay(this.bikeRide);
        setupDisplayPageName(this.pageName);
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final GMapDisplay geoMapView = clientFactory.getDisplay(this);
        geoMapView.setPresenter(this);
        panel.setWidget(geoMapView);
    }

    private void setupDisplay(BikeRide bikeRide) {
        startWatching(bikeRide);
    }

    private void setupDisplayPageName(String pageName) {
        GMapDisplay display = clientFactory.getDisplay(this);
        display.displayPageName(pageName);
    }

    private void startWatching(final BikeRide bikeRide) {

        final GeolocationOptions options = new GeolocationOptions();
        options.setEnableHighAccuracy(true);
        options.setTimeout(10000);
        options.setMaximumAge(1000);

        final NonPhoneGapGeoLocCallback callback = new NonPhoneGapGeoLocCallback() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                setMapView(geoLoc);
            }

            @Override
            public void onFailure(GeoLoc geoLoc) {
                setMapView(geoLoc);
            }
        };
        DeviceTools.getPhoneGeoLoc(clientFactory, callback);

        if (bikeRide == null) {
            watchPosition(options, callback);
        }
    }

    /**
     * Set the map to view the correct geo location as well as setting all date points as well.
     * @param geoLoc
     */
    private void setMapView(GeoLoc geoLoc) {
        if (bikeRide == null) { //Here & now Map
            if (watcher != null) {
                setView(geoLoc); //Here & now Map
            }
        } else { //Event Map
            if (watcher != null) { //Tracking
                setView(geoLoc);
            } else if (bikeRide.getRideLeaderTracking() != null && bikeRide.getRideLeaderTracking().getId() != null) { //Following Event
                setView(bikeRide.getRideLeaderTracking().getGeoLoc(), geoLoc);
            } else if (bikeRide.getCurrentTrackings() != null && bikeRide.getCurrentTrackings().length() > 0) { //Following Event
                setView(bikeRide.getCurrentTrackings().get(0).getGeoLoc(), geoLoc);
            }  else { //Following Event
                setView(bikeRide.getLocation().getGeoLoc(), geoLoc);
            }
        }
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

    private void setView(final GeoLoc viewGeoLoc, final GeoLoc clientGeoLoc) {
        final GMapDisplay display = clientFactory.getDisplay(this);
        display.display(bikeRide);

        String city = bikeRide.getLocation().getCity();

        display.setMapInfo(clientGeoLoc, bikeRide, city);
        display.refresh();
    }

    private void setView(final GeoLoc geoLoc) {
        final GMapDisplay geoMapView = clientFactory.getDisplay(this);
        fireRequestForHereAndNow(geoMapView, geoLoc);
    }


    private int calledTimes = 0;
    private void fireRequestForHereAndNow(final GMapDisplay display, final GeoLoc geoLoc) {

        SearchByProximityRequest.Callback callback = new SearchByProximityRequest.Callback() {
            @Override
            public void onError() {
                Window.alert("Oops, your BFF will be back shortly.");
                GeoLoc phonesGeoLoc = GWT.create(GeoLoc.class);
                phonesGeoLoc.setLatitude(geoLoc.getLatitude());
                phonesGeoLoc.setLongitude(geoLoc.getLongitude());
                display.setMapInfo(phonesGeoLoc,
                        new ArrayList<BikeRide>(),
                        "City Unknown");
                display.refresh();
            }

            @Override
            public void onResponseReceived(Root root) {
                currentList =  Extractor.getBikeRidesFrom(root);
                final String city = getCityNameFromRoot(root);
                GeoLoc phonesGeoLoc = GWT.create(GeoLoc.class);
                phonesGeoLoc.setLatitude(geoLoc.getLatitude());
                phonesGeoLoc.setLongitude(geoLoc.getLongitude());
                display.setMapInfo(phonesGeoLoc,
                        currentList,
                        city);
                display.refresh();

                ramObjectCache.setHereAndNowBikeRideCache(currentList);
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
            request.latitude(geoLoc).longitude(geoLoc).sendAndDebug();
            calledTimes++;
        } else {
            request.latitude(geoLoc).longitude(geoLoc).send();
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
