package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.HomeHelper;
import com.bikefunfinder.client.shared.Tools.NavigationHelper;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.bikefunfinder.client.shared.request.SearchByTimeOfDayRequest;
import com.bikefunfinder.client.shared.request.management.WebServiceResponseConsumer;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author: tneuwerth / Major update by Lance Poehler
 * @created 4/5/13 3:59 PM / updated on 8/15/13
 */
public class HomeScreenActivity extends MGWTAbstractActivity implements HomeScreenDisplay.Presenter {
    private final ClientFactory<HomeScreenDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private final HomeScreenDisplay display = clientFactory.getDisplay(this);
    private final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();

    private int geoFailCount = 0;

    final NotifyTimeAndDayCallback noOpNotifyTimeAndDayCallback = new NotifyTimeAndDayCallback() {
        @Override public void onError() { } // noOp
        @Override public void onResponseReceived() { } // noOp
    };

    public HomeScreenActivity() {
        Root root = ramObjectCache.getRoot();
        GeoLoc geoLoc = ramObjectCache.getCurrentPhoneGeoLoc();
        if (geoLoc == null) {
            onRefreshButton();
        } else if (root == null) {
            fireRequest(geoLoc, noOpNotifyTimeAndDayCallback);
        } else {
            setupDisplay(Extractor.getBikeRidesFrom(root), HomeHelper.getHomeTitle(root));
        }
    }

    /**
     * Root will never be null
     * @param bikeRides
     * @param title
     */
    private void setupDisplay(List<BikeRide> bikeRides, String title) {
        display.display(bikeRides);
        display.setTitle(title);
        ramObjectCache.setMainScreenSize(display.getMainSize());
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        display.setPresenter(this);
        panel.setWidget(display);
    }

    @Override
    public void onRideClick(BikeRide bikeRide) {
        clientFactory.getPlaceController().goTo(new EventScreenPlace(bikeRide));
    }

    @Override
    public void onRefreshButton() {
        ramObjectCache.setMainScreenPullDownLocked(false); //unlock the pulldown
        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                ramObjectCache.setCurrentPhoneGeoLoc(geoLoc);
                fireRequest(geoLoc, noOpNotifyTimeAndDayCallback);
            }
        }));
    }

    @Override
    public void onMainScreenToggleButton() {
        clientFactory.getPlaceController().goTo(NavigationHelper.getGMapHomePlace());
    }

    @Override
    public void onShowHideToggleButton() {
        display.display(Extractor.getBikeRidesFrom(ramObjectCache.getRoot()));
    }

    @Override
    public void refreshTimeAndDayReq(final NotifyTimeAndDayCallback callback) {
        if (ramObjectCache.getMainScreenPullDownLocked()) {
            //We are disabling the pulldown when viewing the profile or the search data.
            callback.onResponseReceived(); // tell the caller everything is happy
            return;
        }

        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                ramObjectCache.setCurrentPhoneGeoLoc(geoLoc);
                fireRequest(geoLoc, callback);
            }
        }));
    }

    private void fireRequest(final GeoLoc geoLoc,
                             final NotifyTimeAndDayCallback notifyTimeAndDayCallback) {

        WebServiceResponseConsumer<Root> callback = new WebServiceResponseConsumer<Root>() {
            @Override
            public void onResponseReceived(Root root) {
                Logger.getLogger("").log(Level.INFO, "Root retrieved from fireRequest");
                ramObjectCache.setRoot(root);
                setupDisplay(Extractor.getBikeRidesFrom(root), HomeHelper.getHomeTitle(root));
                notifyTimeAndDayCallback.onResponseReceived();
            }
        };
        SearchByTimeOfDayRequest.Builder request = new SearchByTimeOfDayRequest.Builder(callback);
        request.latitude(geoLoc).longitude(geoLoc).send();
    }
}