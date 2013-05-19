package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.bikefunfinder.client.shared.request.SearchByProximityRequest;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Timer;
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

    private final Geolocation geolocation; //TODO WHAT IS THIS??? NEEDS TO BE CLEANED UP.. CAUSING TWO CALLS.
    private GeolocationWatcher watcher = null;  //TODO WHAT IS THIS??? NEEDS TO BE CLEANED UP.. CAUSING TWO CALLS.
    private final GMapActivity itsAMeMario = this;  //TODO WHAT IS THIS??? NEEDS TO BE CLEANED UP
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
        final GMapDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        panel.setWidget(display);
    }

    private void setupDisplay(BikeRide bikeRide) {

        startWatching(bikeRide);

        //This is close.  it does refresh but stop when you leave the page.  I'll fix that soon and then bring back.
//        Timer t = new Timer() {
//            public void run() {
//                refreshScreen();
//            }
//        };
//
//        // Schedule the timer to run once in 5 minutes.
//        t.scheduleRepeating(ScreenConstants.SCREEN_REFRESH_RATE_IN_SECONDS*1000);
    }

    private void refreshScreen() {
        if (bikeRide != null) {
            //TODO CALL SERVICE AND GET UPDATED BIKERIDE WITH TRACKING DETAILS.
            Window.alert("refreshing bike ride..");
        }
        Window.alert("sending to screen.");
        startWatching(bikeRide);
    }

    private void setupDisplayPageName(String pageName) {
        GMapDisplay display = clientFactory.getDisplay(this);
        display.displayPageName(pageName);
    }

    private void startWatching(final BikeRide bikeRide) {

        final NonPhoneGapGeoLocCallback callback = new NonPhoneGapGeoLocCallback() {
            @Override
            public void onSuccess(GeoLoc phoneGeoLoc) {
                setMapView(phoneGeoLoc);
            }

            @Override
            public void onFailure(GeoLoc phoneGeoLoc) {
                setMapView(phoneGeoLoc);
            }
        };
        DeviceTools.getPhoneGeoLoc(clientFactory, callback);

        if (bikeRide == null) {
            final GeolocationOptions options = new GeolocationOptions();
            options.setEnableHighAccuracy(true);
            options.setTimeout(10000);
            options.setMaximumAge(1000);
            watchPosition(options, callback);
        }
    }

    /**
     * Set the map to view the correct geo location as well as setting all date points as well.
     * @param phoneGeoLoc
     */
    private void setMapView(GeoLoc phoneGeoLoc) {
        if (bikeRide == null) { //Here & now Map
            if (watcher != null) {
                setHereAndNowView(phoneGeoLoc); //Here & now Map
            }
        } else { //Event Map
            clearWatch();
            if (false) { //Tracking    ??? NOT SURE WHAT TO DO YET.
                setTrackView(phoneGeoLoc);
            } else if (bikeRide.getRideLeaderTracking() != null && bikeRide.getRideLeaderTracking().getId() != null) { //Following Event
                setEventView(phoneGeoLoc, bikeRide.getRideLeaderTracking().getGeoLoc());
            } else if (bikeRide.getCurrentTrackings() != null && bikeRide.getCurrentTrackings().length() > 0) { //Following Event
                setEventView(phoneGeoLoc, bikeRide.getCurrentTrackings().get(0).getGeoLoc());
            }  else { //Following Event
                setEventView(phoneGeoLoc, bikeRide.getLocation().getGeoLoc());
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

    private void setTrackView(final GeoLoc phoneGeoLoc) {
        final GMapDisplay display = clientFactory.getDisplay(this);
        display.resetForEvent(phoneGeoLoc);
        display.display(bikeRide);
        display.setMapInfo(phoneGeoLoc, bikeRide);
    }

    private void setEventView(final GeoLoc phoneGeoLoc, final GeoLoc eventGeoLoc) {
        final GMapDisplay display = clientFactory.getDisplay(this);
        display.resetForEvent(eventGeoLoc);
        display.display(bikeRide);
        display.setMapInfo(phoneGeoLoc, bikeRide);
    }

    private void setHereAndNowView(final GeoLoc phoneGeoLoc) {
        final GMapDisplay display = clientFactory.getDisplay(this);
        display.resetForHereAndNow(phoneGeoLoc);
        fireRequestForHereAndNow(display, phoneGeoLoc);
    }


    private int calledTimes = 0;
    private void fireRequestForHereAndNow(final GMapDisplay display, final GeoLoc phoneGeoLoc) {

        SearchByProximityRequest.Callback callback = new SearchByProximityRequest.Callback() {
            @Override
            public void onError() {
                Window.alert("Oops, your BFF will be back shortly.");
                display.displayPageName("Sorry, No Rides");
                display.setMapInfo(phoneGeoLoc, new ArrayList<BikeRide>());
            }

            @Override
            public void onResponseReceived(Root root) {
                currentList =  Extractor.getBikeRidesFrom(root);
                if (currentList == null || currentList.size() == 0) {
                    display.displayPageName("Sorry, No Rides");
                }

                display.setMapInfo(phoneGeoLoc,currentList);
                ramObjectCache.setHereAndNowBikeRideCache(currentList);
            }
        };
        SearchByProximityRequest.Builder request = new SearchByProximityRequest.Builder(callback);

//        if(calledTimes == 0) {
//            request.latitude(phoneGeoLoc).longitude(phoneGeoLoc).sendAndDebug();
//            calledTimes++;
//        } else {
            request.latitude(phoneGeoLoc).longitude(phoneGeoLoc).send();
            calledTimes++;
//        }

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
