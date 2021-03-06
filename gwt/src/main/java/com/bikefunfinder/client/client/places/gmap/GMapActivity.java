package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.*;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.request.EventRequest;
import com.bikefunfinder.client.shared.request.NewTrackRequest;
import com.bikefunfinder.client.shared.request.management.GeoLocCacheStrategy;
import com.bikefunfinder.client.shared.request.management.WebServiceResponseConsumer;
import com.bikefunfinder.client.shared.widgets.NavBaseActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
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
    private static ScreenRefreshTimer refreshTimer;
    private static GeoLoc accurateGeoLoc;

    private final AnonymousUser anonymousUser;

    private final User user;
    private String userId;
    private String userName;
    private String pageName;
    private static boolean isTracking;
    private boolean reCenterReZoom = true;
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
            gMapActivity.trackingPingLogic();
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
            isShowing = false;
            timeTrackingStartedInMillis = (new Date()).getTime();
        }

        @Override
        public void onCancel() {
            isTracking = false;
            isShowing = false;
            display.setTrackingButtonText(isTracking);
        }
    };

    public BikeRide bikeRide;

    public GMapActivity(String pageName, BikeRide bikeRide, User user, AnonymousUser anonymousUser) {

        cancelRefreshTimer();
        refreshTimer = new ScreenRefreshTimer(this);

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

        if(warningDialog!=null) {
            warningDialog.hide();
        }
        isShowing = false;

        setDisplayPageName(this.pageName);

        refreshTimer.scheduleRepeating(ScreenConstants.SCREEN_REFRESH_RATE_IN_MILLISECONDS);

        //Required GeoLoc even for viewing a bikeride, otherwise maps does not load
        screenRefreshLogic();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        display.setPresenter(this);
        panel.setWidget(display);
    }

    public static void cancelTimersAndAnyOutstandingActivities() {
        cancelRefreshTimer();
        cancelGeoLocationWatcherIfRegistered();
    }

    public static void cancelRefreshTimer() {
        if(refreshTimer !=null) {
            refreshTimer.cancel();
            refreshTimer = null;
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

    private static boolean isShowing = false;
    private void checkIfWeShouldShowWarningDialog() {
        final long timeSpentTracking = calculateTimeSpentTracking();

        if(timeSpentTracking >= ScreenConstants.MAX_TRACKING_WITHOUT_CONFORMATION_IN_MILLISECONDS) {
            if(warningDialog!=null) {
                warningDialog.hide();
            }

            trackingWarningCallBackAction.onCancel();
        } else if(timeSpentTracking >= ScreenConstants.TIME_TO_WARN_USER_OF_TRACKING_DISABLING_IN_MILLIS) {
            if(warningDialog==null && !isShowing) {
                warningDialog = Dialogs.confirm("Warning:", "Tracking is about to expire. Continue Tracking?", trackingWarningCallBackAction);
            } else {
                warningDialog.show();
            }
            isShowing = true;
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
        updatedBikeRide(geoLoc);
        reCenterReZoom = false;
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
        display.reset(phoneGeoLoc);
        display.display(bikeRide);
        display.setupMapToDisplayBikeRide(phoneGeoLoc, bikeRide, isTracking, isTracking);
    }

    private void setEventView(final GeoLoc phoneGeoLoc, final GeoLoc eventGeoLoc) {
        display.reset(eventGeoLoc);
        display.display(bikeRide);
        display.setupMapToDisplayBikeRide(phoneGeoLoc, bikeRide, reCenterReZoom, false);
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
        NavigationHelper.goToPriorScreen(clientFactory.getPlaceController());
    }

    public static void cancelGeoLocationWatcherIfRegistered() {
        if(geolocationWatcher!=null) {
            DeviceTools.cancelWatcher(geolocationWatcher);
            NativeUtilities.releasePartialWakeLock();
            geolocationWatcher = null;
        }
    }

//    @Override
//    public String provideTokenHrefFor(BikeRide bikeRide) {
//        if(bikeRide==null) {
//            return "";
//        }
//
//        String hashToken = clientFactory.getPlaceHistoryMapper().getToken(new EventScreenPlace(bikeRide));
//        UrlBuilder urlBuilder = Window.Location.createUrlBuilder().setHash(hashToken);
//        return urlBuilder.buildString();
//    }

}
