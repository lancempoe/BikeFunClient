package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.MustGetGoodGeo;
import com.bikefunfinder.client.shared.Tools.NativeUtilities;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.constants.ScreenConstants.MapScreenType;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.bikefunfinder.client.shared.request.EventRequest;
import com.bikefunfinder.client.shared.request.NewTrackRequest;
import com.bikefunfinder.client.shared.request.SearchByProximityRequest;
import com.bikefunfinder.client.shared.request.management.GeoLocCacheStrategy;
import com.bikefunfinder.client.shared.request.management.WebServiceResponseConsumer;
import com.bikefunfinder.client.shared.widgets.NavBaseActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationWatcher;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog;
import com.googlecode.mgwt.ui.client.dialog.Dialog;
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
    private static ScreenRefreshTimer screenRefreshTimer;
    private static TrackingRefreshTimer trackingRefreshTimer;
    private static GeoLoc accurateGeoLoc;

    private final AnonymousUser anonymousUser;

    private final User user;
    private String userId;
    private String userName;
    private String pageName;
    private static boolean isTracking;
    private boolean isFirstPostSavePhoneGeoLoc = true;
    private long timeTrackingStartedInMillis;

    private static Dialog warningDialog;

    private class ScreenRefreshTimer extends Timer {
        private final GMapActivity gMapActivity;

        private ScreenRefreshTimer(GMapActivity gMapActivity) {
            this.gMapActivity = gMapActivity;
        }

        @Override
        public void run() {
            gMapActivity.screenRefreshLogic();
        }
    }

    private class TrackingRefreshTimer extends Timer {
        private final GMapActivity gMapActivity;

        private TrackingRefreshTimer(GMapActivity gMapActivity) {
            this.gMapActivity = gMapActivity;
        }

        @Override
        public void run() {
            gMapActivity.trackingPingLogic();
        }
    }

    private ConfirmDialog.ConfirmCallback trackingWarningCallBackAction = new ConfirmDialog.ConfirmCallback() {
        @Override
        public void onOk() {
            isTracking = true;
            timeTrackingStartedInMillis = (new Date()).getTime();
        }

        @Override
        public void onCancel() {
            isTracking = false;
            display.setTrackingButtonText(isTracking);
        }
    };

    public MapScreenType screenType = MapScreenType.EVENT; //Default Value.
    public BikeRide bikeRide;

    public GMapActivity(String pageName, BikeRide bikeRide, User user, AnonymousUser anonymousUser) {
        if (bikeRide == null) { screenType = MapScreenType.HERE_AND_NOW; }

        cancelScreenRefreshTimer();
        screenRefreshTimer = new ScreenRefreshTimer(this);

        cancelTrackingRefreshTimer();
        trackingRefreshTimer = new TrackingRefreshTimer(this);

        this.anonymousUser = anonymousUser;
        this.user = user;

        this.bikeRide = bikeRide;
        this.pageName = pageName;
        this.isTracking = false;

        setUserOrAnonymousUser();

        display.setUserId(userId);
        display.resetPolyLine();
        display.truffleShuffle();
        display.setTrackingButtonText(isTracking);

        setDisplayPageName(this.pageName);

        chooseWhichViewToGoWith();
    }

    private void chooseWhichViewToGoWith() {
        if (MapScreenType.HERE_AND_NOW.equals(screenType)) {
            DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
                @Override
                public void onSuccess(GeoLoc geoLoc) {
                    setHereAndNowView(geoLoc); //Here & now Map
                }
            }));
        } else {

            screenRefreshTimer.scheduleRepeating(ScreenConstants.SCREEN_REFRESH_RATE_IN_MILLISECONDS);
            trackingRefreshTimer.scheduleRepeating(ScreenConstants.SCREEN_REFRESH_RATE_IN_MILLISECONDS);

            //Required GeoLoc even for viewing a bikeride, otherwise maps does not load
            screenRefreshLogic();
        }
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        display.setPresenter(this);
        panel.setWidget(display);
    }

    public static void cancelTimersAndAnyOutstandingActivities() {
        cancelScreenRefreshTimer();
        cancelTrackingRefreshTimer();
        cancelGeoLocationWatcherIfRegistered();
    }

    public static void cancelScreenRefreshTimer() {
        if(screenRefreshTimer!=null) {
            screenRefreshTimer.cancel();
            screenRefreshTimer = null;
        }
    }

    public static void cancelTrackingRefreshTimer() {
        if(trackingRefreshTimer!=null) {
            trackingRefreshTimer.cancel();
            trackingRefreshTimer = null;
        }
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

    private void screenRefreshLogic() {


        if(accurateGeoLoc!=null) {
            //skip a geo and get better accuracy when rides are being tracked!
            updateBikeRideOnMap(accurateGeoLoc);
        } else {
            //TN this is bad form, I'm doing it for the sake of getting out, the geo location call
            //producers need the same XHRs treatment with repect to the abstract typed stragegies
            //so we can controll the fuckers with finesse
            GeoLoc lastLocationWeSaw = GeoLocCacheStrategy.INSTANCE.getCachedType();
            if(lastLocationWeSaw != null) {
                updateBikeRideOnMap(lastLocationWeSaw);
            }else {
                //cest la vie. We've got to commit to the double donkey dip
                DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
                    @Override
                    public void onSuccess(GeoLoc geoLoc) {
                        updateBikeRideOnMap(geoLoc);
                    }
                },2));
            }
        }
    }

    private void setDisplayPageName(String pageName) {
        display.displayPageName(pageName);
    }

    private void checkIfWeShouldShowWarningDialog() {
        final long timeSpentTracking = calculateTimeSpentTracking();

        if(timeSpentTracking >= ScreenConstants.MAX_TRACKING_WITHOUT_CONFORMATION_IN_MILLISECONDS) {
            if(warningDialog!=null) {
                warningDialog.hide();
            }

            trackingWarningCallBackAction.onCancel();
        } else if(timeSpentTracking >= ScreenConstants.TIME_TO_WARN_USER_OF_TRACKING_DISABLING_IN_MILLIS) {
            warningDialog = Dialogs.confirm("Warning:", "Tracking is about to expire. Continue Tracking?", trackingWarningCallBackAction);
        }
    }

    private long calculateTimeSpentTracking() {
        final long nowInMillies = (new Date()).getTime();
        return nowInMillies - timeTrackingStartedInMillis;
    }


    private void callServerForClientTrack(GeoLoc geoLoc) {

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

    private void updateBikeRideOnMap(GeoLoc geoLoc) {
        if (MapScreenType.EVENT.equals(screenType)) {
            updatedBikeRide(geoLoc);
        }


        isFirstPostSavePhoneGeoLoc = false;
    }

    /**
     * Set the map to view the correct geo location as well as setting all date points as well.
     * @param phoneGeoLoc
     */
    private void setMapView(GeoLoc phoneGeoLoc, BikeRide bikeRide) {
        this.bikeRide = bikeRide;

        if (isTracking) { //Tracking

        } else if (bikeRide.getRideLeaderTracking() != null && bikeRide.getRideLeaderTracking().getId() != null) { //Following Event
            setEventView(phoneGeoLoc, bikeRide.getRideLeaderTracking().getGeoLoc());
        } else if (bikeRide.getCurrentTrackings() != null && bikeRide.getCurrentTrackings().length() > 0) { //Following Event
            setEventView(phoneGeoLoc, bikeRide.getCurrentTrackings().get(0).getGeoLoc());
        }  else { //Following Event
            setEventView(phoneGeoLoc, bikeRide.getLocation().getGeoLoc());
        }
    }

    private void setTrackView(final GeoLoc phoneGeoLoc) {
        display.resetForEvent(phoneGeoLoc);
        display.display(bikeRide);
        display.setupMapToDisplayBikeRide(phoneGeoLoc, bikeRide, isTracking, isTracking);
    }

    private void setEventView(final GeoLoc phoneGeoLoc, final GeoLoc eventGeoLoc) {
        display.resetForEvent(eventGeoLoc);
        display.display(bikeRide);
        display.setupMapToDisplayBikeRide(phoneGeoLoc, bikeRide, isFirstPostSavePhoneGeoLoc, false);

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
                setMapView(phoneGeoLoc, bikeRide);
            }

        };
        EventRequest.Builder request = new EventRequest.Builder(callback);
        request.clientId(userId).id(bikeRide.getId()).latitude(phoneGeoLoc).longitude(phoneGeoLoc).send();
    }

    @Override
    public void trackingRideButtonSelected() {
        isTracking = !isTracking;

        if(isTracking) {
            timeTrackingStartedInMillis = (new Date()).getTime();
            screenRefreshLogic();
            trackingPingLogic();
        }

        display.setTrackingButtonText(isTracking);
    }

    private void trackingPingLogic() {
        if(isTracking) {
            if(geolocationWatcher==null) {
                geolocationWatcher = DeviceTools.requestGeoUpdates(
                    new MustGetGoodGeo(
                        new NonPhoneGapGeoLocCallback.GeolocationHandler() {
                            @Override
                            public void onSuccess(GeoLoc geoLoc) {
                                NativeUtilities.partialWakeLock();
                                callServerForClientTrack(geoLoc);

                                cancelGeoLocationWatcherIfRegistered();
                                setTrackView(geoLoc);
                                accurateGeoLoc = geoLoc;
                            }
                        }){
                            @Override
                            public void killingCall() {
                                cancelGeoLocationWatcherIfRegistered();
                            }
                        }
                );
            }
        } else {
            cancelGeoLocationWatcherIfRegistered();
        }

        checkIfWeShouldShowWarningDialog();
    }

    @Override
    public void backButtonSelected() {
        cancelTimersAndAnyOutstandingActivities();

        final Place whereWereGoing;
        if(bikeRide!=null) {
            whereWereGoing = new EventScreenPlace(bikeRide);
        } else {
            whereWereGoing = new HomeScreenPlace();
        }

        clientFactory.getPlaceController().goTo(whereWereGoing);
    }

    public static void cancelGeoLocationWatcherIfRegistered() {
        if(geolocationWatcher!=null) {
            DeviceTools.cancelWatcher(geolocationWatcher);
            NativeUtilities.releasePartialWakeLock();
            geolocationWatcher = null;
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
