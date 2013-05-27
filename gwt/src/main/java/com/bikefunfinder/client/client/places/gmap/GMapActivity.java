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
import com.bikefunfinder.client.shared.request.ServiceCallback;
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
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

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
    private boolean isTracking;
    private boolean isFirstPostSavePhoneGeoLoc = true;
    private int refreshTrackingCount = 0;
    private ConfirmDialog.ConfirmCallback trackingWarning;
    private int geoFailCount = 0;

    public GMapActivity(String pageName, BikeRide bikeRide) {
        this.clientFactory = injector.getClientFactory();
        this.ramObjectCache = injector.getRamObjectCache();
        ramObjectCache.setCurrentBikeRide(bikeRide);
        this.geolocation = clientFactory.getPhoneGap().getGeolocation();  //Init GeoLoc Obj, not a request from device.
        this.pageName = pageName;
        setUserOrAnonymousUser();
        setUserId(this.userId);
        setDisplayPageName(this.pageName);
        //Required GeoLoc even for viewing a bikeride, otherwise maps does not load
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
                geoFailCount = 0;
                postSavePhoneGeoLoc();
            }

            @Override
            public void onFailure(GeoLoc phoneGeoLoc) {
                if(!isTracking) {
                    Dialogs.alert("Warning", "Your GPS location is currently unavailable, we will show you results for Portland Oregon.", new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                        }
                    });
                    ramObjectCache.setCurrentPhoneGeoLoc(phoneGeoLoc);
                    postSavePhoneGeoLoc();
                } else if (geoFailCount++ > 5) { //Allow fails up to 5 times.
                    Dialogs.alert("Error", "Unable to obtain your GeoLocation.", new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            //Stop Tracking AND close the popup.
                            backButtonSelected();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final GMapDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        panel.setWidget(display);
    }

    /**
     * Items that need to happen while on the map screen.  It can and should be cleaned up in version 2.
     */
    private void postSavePhoneGeoLoc() {

        if (isFirstPostSavePhoneGeoLoc) {
            isFirstPostSavePhoneGeoLoc = false;
            startWatching();

            if (ramObjectCache.getCurrentBikeRide() != null) { //Event page.
                timer = new Timer() {
                    public void run() {
                        refreshScreen();
                    }
                };

                // Schedule the timer to run once in x seconds.
                timer.scheduleRepeating(ScreenConstants.SCREEN_REFRESH_RATE_IN_SECONDS * 1000);
            }
        } else if (isTracking) {

            if (refreshTrackingCount *ScreenConstants.SCREEN_REFRESH_RATE_IN_SECONDS >= ScreenConstants.TRACKING_WITHOUT_CONFORMATION_IN_SECONDS) {
                if(trackingWarning == null) { //Display message if it is not already displayed.
                    showTrackingWarning();
                }

                if (refreshTrackingCount *ScreenConstants.SCREEN_REFRESH_RATE_IN_SECONDS >= ScreenConstants.MAX_TRACKING_WITHOUT_CONFORMATION_IN_SECONDS) {
                    //Stop Tracking AND close the popup.
                    trackingWarning.onCancel();
                    backButtonSelected(); //This is bad code... right?

                } else { //Keep tracking
                    startWatching();
                }
            } else {
                startWatching();
            }
            refreshTrackingCount++;

        } else {
            startWatching();
        }
    }

    private void showTrackingWarning() {
        trackingWarning = new ConfirmDialog.ConfirmCallback() {
            @Override
            public void onOk() {
                refreshTrackingCount = 0;
                trackingWarning = null;
            }

            @Override
            public void onCancel() {
                refreshTrackingCount = 0;
                trackingRideButtonSelected(false); //This is also pretty bad code.
                trackingWarning = null;
            }
        };

        Dialogs.confirm("Warning:", "Tracking is about to expire. Continue Tracking?", trackingWarning);
    }

    private void pingClientTrack() {
        if (isTracking) {
            ServiceCallback<Tracking> callback = new ServiceCallback<Tracking>() {
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

    private void setIsTrackingDisplay() {
        GMapDisplay display = clientFactory.getDisplay(this);
        display.setIsTracking(isTracking);
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
            if (isTracking) {
                pingClientTrack();
            }
        }

        setMapView(ramObjectCache.getCurrentPhoneGeoLoc());
    }

    /**
     * Set the map to view the correct geo location as well as setting all date points as well.
     * @param phoneGeoLoc
     */
    private void setMapView(GeoLoc phoneGeoLoc) {
        if (ramObjectCache.getCurrentBikeRide() == null) { //Here & now Map
            setHereAndNowView(phoneGeoLoc); //Here & now Map
        } else { //Event Map
            clearWatch();

            if (isTracking) { //Tracking
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

        ServiceCallback<Root> callback = new ServiceCallback<Root>() {
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

        ServiceCallback<BikeRide> callback = new ServiceCallback<BikeRide>() {
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
        request.clientId(userId).id(ramObjectCache.getCurrentBikeRide().getId()).latitude(phoneGeoLoc).longitude(phoneGeoLoc).send();
    }

    @Override
    public void moreRideDetilsScreenRequested(BikeRide bikeRide) {
        if (timer != null) { timer.cancel(); }
        clientFactory.getPlaceController().goTo(new EventScreenPlace(bikeRide));
    }

    @Override
    public void trackingRideButtonSelected(boolean isTracking) {
        this.isTracking = isTracking;
        setIsTrackingDisplay();  //And of course, bad code here as well.
        refreshScreen();
    }

    @Override
    public void backButtonSelected() {
        if (timer != null) { timer.cancel(); }
        trackingWarning = null; //just in case the message is up.
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
