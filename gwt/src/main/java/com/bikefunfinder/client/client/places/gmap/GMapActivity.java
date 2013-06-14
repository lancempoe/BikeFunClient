package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NativeUtilities;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.constants.ScreenConstants.MapScreenType;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.bikefunfinder.client.shared.request.EventRequest;
import com.bikefunfinder.client.shared.request.NewTrackRequest;
import com.bikefunfinder.client.shared.request.SearchByProximityRequest;
import com.bikefunfinder.client.shared.request.management.WebServiceResponseConsumer;
import com.bikefunfinder.client.shared.widgets.NavBaseActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationWatcher;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMapActivity extends NavBaseActivity implements GMapDisplay.Presenter {
    private final ClientFactory<GMapDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private final GMapDisplay display = clientFactory.getDisplay(this);
    private final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();

    private static GeolocationWatcher geolocationWatcher;
    private final AnonymousUser anonymousUser;

    private final User user;
    private String userId;
    private String userName;
    private String pageName;
    private boolean isTracking;
    private boolean isFirstPostSavePhoneGeoLoc = true;
    private Date trackingTimer;
    private ConfirmDialog.ConfirmCallback trackingWarning;

    public MapScreenType screenType = MapScreenType.EVENT; //Default Value.
    public BikeRide bikeRide;

    public GMapActivity(String pageName, BikeRide bikeRide, User user, AnonymousUser anonymousUser) {
        if (bikeRide == null) { screenType = MapScreenType.HERE_AND_NOW; }

        this.anonymousUser = anonymousUser;
        this.user = user;

        this.bikeRide = bikeRide;
        this.pageName = pageName;

        setUserOrAnonymousUser();

        display.setUserId(userId);
        display.resetPolyLine();
        display.truffleShuffle();

        setDisplayPageName(this.pageName);
        //Required GeoLoc even for viewing a bikeride, otherwise maps does not load
        callGeoThenRefreshScreen();
    }

    private void setUserOrAnonymousUser() {
        //Set the logged in user details
        if (user != null) {
            this.userId = user.getId();
            this.userName = user.getUserName();
        }
        else if (anonymousUser != null) {
            this.userId = anonymousUser.getId();
            this.userName = anonymousUser.getUserName();
        }
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        display.setPresenter(this);
        panel.setWidget(display);
    }

    private void callGeoThenRefreshScreen() {
        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                refreshScreen(geoLoc);
            }
        }));
    }


    /**
     * Items that need to happen while on the map screen.  It can and should be cleaned up in version 2.
     */
    private void refreshScreen(final GeoLoc geoLoc) {

        if (isTracking) {
            Date now = new Date();
            if ((trackingTimer.getTime()+(ScreenConstants.TRACKING_WITHOUT_CONFORMATION_IN_MILLISECONDS)) < now.getTime()) {
                if(trackingWarning == null) { //Display message if it is not already displayed.
                    showTrackingWarning();
                }

                if ((trackingTimer.getTime()+(ScreenConstants.MAX_TRACKING_WITHOUT_CONFORMATION_IN_MILLISECONDS)) < now.getTime()) {
                    //Stop Tracking AND close the popup.
                    trackingWarning.onCancel();
                    backButtonSelected(); //This is bad code... right?
                    return;
                }
            }
        }

        updateBikeRideOnMapAndPingIfTracking(geoLoc);
        isFirstPostSavePhoneGeoLoc = false;
    }

    private void showTrackingWarning() {
        trackingWarning = new ConfirmDialog.ConfirmCallback() {
            @Override
            public void onOk() {
                trackingTimer = new Date();
                trackingWarning = null;
            }

            @Override
            public void onCancel() {
                trackingRideButtonSelected(false); //This is also pretty bad code.
                trackingWarning = null;
            }
        };

        Dialogs.confirm("Warning:", "Tracking is about to expire. Continue Tracking?", trackingWarning);
    }

    private void pingClientTrack(GeoLoc geoLoc) {
        if (isTracking) {
            WebServiceResponseConsumer<Tracking> callback = new WebServiceResponseConsumer<Tracking>() {
                @Override
                public void onResponseReceived(Tracking tracking) {
                    //Success!  Nothing more needed.
                }
            };
            NewTrackRequest.Builder request = new NewTrackRequest.Builder(callback);
            Date date = new Date();

            Tracking tracking = GWT.create(Tracking.class);
            tracking.setBikeRideId(bikeRide.getId());
            tracking.setGeoLoc(geoLoc);
            tracking.setTrackingTime(date.getTime());
            tracking.setTrackingUserId(userId);
            tracking.setTrackingUserName(userName);

            request.tracking(tracking).send();
        }
    }

    private void setIsTrackingDisplay() {
        display.setIsTracking(isTracking);
    }

    private void setDisplayPageName(String pageName) {
        display.displayPageName(pageName);
    }

    private void updateBikeRideOnMapAndPingIfTracking(GeoLoc geoLoc) {
        if (MapScreenType.EVENT.equals(screenType)) {
            updatedBikeRide(geoLoc);

            if (isTracking) {
                pingClientTrack(geoLoc);
            }
        }

        setMapView(geoLoc);
    }

    /**
     * Set the map to view the correct geo location as well as setting all date points as well.
     * @param phoneGeoLoc
     */
    private void setMapView(GeoLoc phoneGeoLoc) {
        if (MapScreenType.HERE_AND_NOW.equals(screenType)) {
            setHereAndNowView(phoneGeoLoc); //Here & now Map
        } else { //Event Map
//            clearWatch();

            if(bikeRide == null) {
                setHereAndNowView(phoneGeoLoc); //Here & now Map
                return;
            }

            if (isTracking) { //Tracking
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

    private void setTrackView(final GeoLoc phoneGeoLoc) {
        display.resetForEvent(phoneGeoLoc);
        display.display(bikeRide);
        display.setupMapToDisplayBikeRide(phoneGeoLoc, bikeRide, isTracking);
    }

    private void setEventView(final GeoLoc phoneGeoLoc, final GeoLoc eventGeoLoc) {
        display.resetForEvent(eventGeoLoc);
        display.display(bikeRide);
        display.setupMapToDisplayBikeRide(phoneGeoLoc, bikeRide, isFirstPostSavePhoneGeoLoc);

        NativeUtilities.trackPage("Event Map Screen");
    }

    private void setHereAndNowView(final GeoLoc phoneGeoLoc) {
        display.resetForHereAndNow(phoneGeoLoc);
        fireRequestForHereAndNow(display, phoneGeoLoc);

        NativeUtilities.trackPage("Here & Now Screen");
    }


    private void fireRequestForHereAndNow(final GMapDisplay display, final GeoLoc phoneGeoLoc) {

        WebServiceResponseConsumer<Root> callback = new WebServiceResponseConsumer<Root>() {
            @Override
            public void onResponseReceived(Root root) {
                ramObjectCache.setHereAndNowBikeRideCache(Extractor.getBikeRidesFrom(root));
                if (ramObjectCache.getHereAndNowBikeRideCache().size() == 0) {
                    display.displayPageName("No Rides");
                }

                display.setupMapDisplayForHereAndNow(phoneGeoLoc, ramObjectCache.getHereAndNowBikeRideCache());
            }
        };
        SearchByProximityRequest.Builder request = new SearchByProximityRequest.Builder(callback);

        request.latitude(phoneGeoLoc).longitude(phoneGeoLoc).send();
    }

    private void updatedBikeRide(final GeoLoc phoneGeoLoc) {

        if(bikeRide == null) {
            return;
        }

        WebServiceResponseConsumer<BikeRide> callback = new WebServiceResponseConsumer<BikeRide>() {
            @Override
            public void onResponseReceived(BikeRide bikeRide) {
                ramObjectCache.setCurrentBikeRide(bikeRide);
            }

        };
        EventRequest.Builder request = new EventRequest.Builder(callback);
        request.clientId(userId).id(bikeRide.getId()).latitude(phoneGeoLoc).longitude(phoneGeoLoc).send();
    }

    @Override
    public void trackingRideButtonSelected(boolean isTracking) {
        this.isTracking = isTracking;

        if(isTracking) {
            cancelGeoLocationWatcherIfRegistered();
            NativeUtilities.partialWakeLock();
            trackingTimer = new Date();
            geolocationWatcher = DeviceTools.requestGeoUpdates(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
                @Override
                public void onSuccess(GeoLoc geoLoc) {
                    refreshScreen(geoLoc);
                }
            }));
        } else {
            cancelGeoLocationWatcherIfRegistered();
        }

        setIsTrackingDisplay();
        callGeoThenRefreshScreen();
    }

    @Override
    public void backButtonSelected() {
        cancelGeoLocationWatcherIfRegistered();
        trackingRideButtonSelected(false);
        trackingWarning = null; //just in case the message is up.
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }

    public static void cancelGeoLocationWatcherIfRegistered() {
        if(geolocationWatcher!=null) {
            DeviceTools.cancelWatcher(geolocationWatcher);
            NativeUtilities.releasePartialWakeLock();
        }
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
