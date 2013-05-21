package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.bootstrap.db.DBKeys;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.request.EventRequest;
import com.bikefunfinder.client.shared.request.NewTrackRequest;
import com.bikefunfinder.client.shared.request.SearchByProximityRequest;
import com.google.gwt.core.client.GWT;
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
import java.util.Date;

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
    private static Timer timer;
    private int calledTimes = 0;

    private final Geolocation geolocation; //TODO WHAT IS THIS??? NEEDS TO BE CLEANED UP.. CAUSING TWO CALLS.
    private GeolocationWatcher watcher = null;  //TODO WHAT IS THIS??? NEEDS TO BE CLEANED UP.. CAUSING TWO CALLS.
    private final RamObjectCache ramObjectCache;
    private String userId;
    private String userName;
    private String pageName;
    private boolean tracking;
    private int refreshCount = 0;

    public GMapActivity(String pageName, BikeRide bikeRide) {
        this.clientFactory = injector.getClientFactory();
        this.ramObjectCache = injector.getRamObjectCache();
        ramObjectCache.setCurrentBikeRide(bikeRide);
        this.geolocation = clientFactory.getPhoneGap().getGeolocation();  //TODO WHY HAVE THIS AND THE CALL DOWN BELOW???
        this.pageName = pageName;
        setUserOrAnonymousUser();
        setUserId(this.userId);
        setDisplayPageName(this.pageName);
        refreshScreen();
    }

    private void setUserOrAnonymousUser() {
        //Set the logged in user details
        if (clientFactory.getStoredValue(DBKeys.USER) != null) {
            User user = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.USER));
            this.userId = user.getId();
            this.userName = user.getUserName();
        }
        else if (clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER) != null) {
            AnonymousUser anonymousUser = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER));
            this.userId = anonymousUser.getId();
            this.userName = anonymousUser.getUserName();
        }
    }
    private void refreshScreen() {
        DeviceTools.getPhoneGeoLoc(clientFactory, new NonPhoneGapGeoLocCallback() {
            @Override
            public void onSuccess(GeoLoc phoneGeoLoc) {
                ramObjectCache.setCurrentPhoneGeoLoc(phoneGeoLoc);
                postSavePhoneGeoLoc();
            }

            @Override
            public void onFailure(GeoLoc phoneGeoLoc) {
                ramObjectCache.setCurrentPhoneGeoLoc(phoneGeoLoc);
                postSavePhoneGeoLoc();
            }
        });
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final GMapDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        panel.setWidget(display);
    }

    private void postSavePhoneGeoLoc() {
        if (refreshCount == 0) {
            startWatching();

            //This is close.  it does refresh but stop when you leave the page.  I'll fix that soon and then bring back.
            timer = new Timer() {
                public void run() {
                    refreshScreen();
                }
            };

            // Schedule the timer to run once in x seconds.
            timer.scheduleRepeating(ScreenConstants.SCREEN_REFRESH_RATE_IN_SECONDS * 1000);
            refreshCount++;
        } else {
            startWatching();
            refreshCount++;
        }
    }

    private void pingClientTrack() {
        if (tracking) {
            NewTrackRequest.Callback callback = new NewTrackRequest.Callback() {
                @Override
                public void onError() {
                    //Unable to refresh... leave screen as is.
                }

                @Override
                public void onResponseReceived(Tracking tracking) {
                    //Success!  Nothing more needed.
                }
            };
            NewTrackRequest.Builder request = new NewTrackRequest.Builder(callback);
            Date date = new Date();

            Tracking tracking = GWT.create(Tracking.class);
            tracking.setBikeRideId(ramObjectCache.getCurrentBikeRide().getId());
            tracking.setGeoLoc(ramObjectCache.getCurrentPhoneGeoLoc());
            tracking.setTrackingTime(date.getTime());
            tracking.setTrackingUserId(userId);
            tracking.setTrackingUserName(userName);

            request.tracking(tracking).send();
        }
    }

    private void setUserId(String userId) {
        GMapDisplay display = clientFactory.getDisplay(this);
        display.setUserId(userId);
    }

    private void setDisplayPageName(String pageName) {
        GMapDisplay display = clientFactory.getDisplay(this);
        display.displayPageName(pageName);
    }

    private void startWatching() { //final BikeRide bikeRide) {
        //Update the bikeRide
        if (ramObjectCache.getCurrentBikeRide() != null) {
            updatedBikeRide(ramObjectCache.getCurrentPhoneGeoLoc());
            if (tracking) {
                pingClientTrack();
            }
        }

        setMapView(ramObjectCache.getCurrentPhoneGeoLoc());

//        if (bikeRide == null) {
//            final GeolocationOptions options = new GeolocationOptions();
//            options.setEnableHighAccuracy(true);
//            options.setTimeout(10000);
//            options.setMaximumAge(1000);
//            watchPosition(options, callback);
//        }
    }

    /**
     * Set the map to view the correct geo location as well as setting all date points as well.
     * @param phoneGeoLoc
     */
    private void setMapView(GeoLoc phoneGeoLoc) {
        if (ramObjectCache.getCurrentBikeRide() == null) { //Here & now Map
//            if (watcher != null) {
                setHereAndNowView(phoneGeoLoc); //Here & now Map
//            }
        } else { //Event Map
            clearWatch();
            if (tracking) { //Tracking
                setTrackView(phoneGeoLoc);
            } else if (ramObjectCache.getCurrentBikeRide().getRideLeaderTracking() != null && ramObjectCache.getCurrentBikeRide().getRideLeaderTracking().getId() != null) { //Following Event
                setEventView(phoneGeoLoc, ramObjectCache.getCurrentBikeRide().getRideLeaderTracking().getGeoLoc());
            } else if (ramObjectCache.getCurrentBikeRide().getCurrentTrackings() != null && ramObjectCache.getCurrentBikeRide().getCurrentTrackings().length() > 0) { //Following Event
                setEventView(phoneGeoLoc, ramObjectCache.getCurrentBikeRide().getCurrentTrackings().get(0).getGeoLoc());
            }  else { //Following Event
                setEventView(phoneGeoLoc, ramObjectCache.getCurrentBikeRide().getLocation().getGeoLoc());
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
        display.display(ramObjectCache.getCurrentBikeRide());
        display.setMapInfo(phoneGeoLoc, ramObjectCache.getCurrentBikeRide());
    }

    private void setEventView(final GeoLoc phoneGeoLoc, final GeoLoc eventGeoLoc) {
        final GMapDisplay display = clientFactory.getDisplay(this);
        display.resetForEvent(eventGeoLoc);
        display.display(ramObjectCache.getCurrentBikeRide());
        display.setMapInfo(phoneGeoLoc, ramObjectCache.getCurrentBikeRide());
    }

    private void setHereAndNowView(final GeoLoc phoneGeoLoc) {
        final GMapDisplay display = clientFactory.getDisplay(this);
        display.resetForHereAndNow(phoneGeoLoc);
        fireRequestForHereAndNow(display, phoneGeoLoc);
    }


    private void fireRequestForHereAndNow(final GMapDisplay display, final GeoLoc phoneGeoLoc) {

        SearchByProximityRequest.Callback callback = new SearchByProximityRequest.Callback() {
            @Override
            public void onError() {
                display.displayPageName("Sorry, No Rides");
                display.setMapInfo(phoneGeoLoc, new ArrayList<BikeRide>());
            }

            @Override
            public void onResponseReceived(Root root) {
                ramObjectCache.setHereAndNowBikeRideCache(Extractor.getBikeRidesFrom(root));
                if (ramObjectCache.getHereAndNowBikeRideCache().size() == 0) {
                    display.displayPageName("Sorry, No Rides");
                }

                display.setMapInfo(phoneGeoLoc,ramObjectCache.getHereAndNowBikeRideCache());
            }
        };
        SearchByProximityRequest.Builder request = new SearchByProximityRequest.Builder(callback);

        request.latitude(phoneGeoLoc).longitude(phoneGeoLoc).send();
    }

    private void updatedBikeRide(final GeoLoc phoneGeoLoc) {

        EventRequest.Callback callback = new EventRequest.Callback() {
            @Override
            public void onError() {
                //Unable to refresh... leave screen as is.
            }

            @Override
            public void onResponseReceived(BikeRide bikeRide) {
                ramObjectCache.setCurrentBikeRide(bikeRide);
            }

        };
        EventRequest.Builder request = new EventRequest.Builder(callback);
        request.id(ramObjectCache.getCurrentBikeRide().getId()).latitude(phoneGeoLoc).longitude(phoneGeoLoc).send();
    }

    @Override
    public void moreRideDetilsScreenRequested(BikeRide bikeRide) {
        timer.cancel();
        clientFactory.getPlaceController().goTo(new EventScreenPlace(bikeRide));
    }

    @Override
    public void trackingRideButtonSelected(boolean tracking) {
        this.tracking = tracking;
        refreshScreen();
    }

    @Override
    public void backButtonSelected() {
        timer.cancel();
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
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
