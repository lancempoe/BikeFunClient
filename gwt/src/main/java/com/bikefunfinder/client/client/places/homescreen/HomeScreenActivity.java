package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
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
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */
public class HomeScreenActivity extends MGWTAbstractActivity implements HomeScreenDisplay.Presenter {
    private final ClientFactory<HomeScreenDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private final HomeScreenDisplay display = clientFactory.getDisplay(this);
    private final HomeScreenPlace.UsageEnum usageEnum;
    private final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();

    private int geoFailCount = 0;

    final NotifyTimeAndDayCallback noOpNotifyTimeAndDayCallback = new NotifyTimeAndDayCallback() {
        @Override public void onError() { } // noOp
        @Override public void onResponseReceived() { } // noOp
    };

    public HomeScreenActivity(Root root, HomeScreenPlace.UsageEnum usageEnum) {
        this.usageEnum = usageEnum;
        if (root != null) {
            Logger.getLogger("").log(Level.INFO, "Passed a root to the Home page. Happens during Search and Profile");
            setupDisplay(root);
        } else {
            List<BikeRide> bikeRides = Extractor.getBikeRidesFrom(ramObjectCache.getRoot());
            if (bikeRides != null || bikeRides.size() == 0) {
                Logger.getLogger("").log(Level.SEVERE, "ramObjectCache.getTimeOfDayBikeRideCache().size() == 0");
                refreshTimeAndDayReq(noOpNotifyTimeAndDayCallback);
            }
        }
    }

    private void setupDisplay(Root root) {
        //root will never be null

        display.display(Extractor.getBikeRidesFrom(root));

        //Get City
        if (root.getBikeRides() != null &&
            root.getBikeRides().length() > 0 &&
            root.getClosestLocation() != null)  {
            MatchResult matcher = buildMatcher(root.getClosestLocation().getFormattedAddress());
            boolean matchFound = (matcher != null); // equivalent to regExp.test(inputStr);
            if (matchFound) {
                display.setTitle(matcher.getGroup(0));
            } else {
                if (root.getClosestLocation() != null &&
                    root.getClosestLocation().getFormattedAddress() != null) {
                    display.setTitle("Unknown City");
                } else {
                    display.setTitle("Search Results");
                }
            }
        }  else {
            display.setTitle("Add an Event!");
        }
        ramObjectCache.setMainScreenSize(display.getMainSize());
    }

    private MatchResult buildMatcher(String formattedAddress) {
        RegExp regExp = RegExp.compile(ScreenConstants.RegularExpression_City);
        return regExp.exec(formattedAddress);
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
    public void onTimeAndDayButton() {
        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                fireRequestForTimeOfDay(display, geoLoc, noOpNotifyTimeAndDayCallback);
            }
        }));
    }

    @Override
    public void onHereAndNowButton() {
        clientFactory.getPlaceController().goTo(NavigationHelper.getGMapHomePlace());
    }

    @Override
    public void onExpiredRidesButton() {
        display.display(Extractor.getBikeRidesFrom(ramObjectCache.getRoot()));
    }

    @Override
    public void refreshTimeAndDayReq(final NotifyTimeAndDayCallback callback) {
        if(HomeScreenPlace.UsageEnum.ShowMyRides == usageEnum ||
           HomeScreenPlace.UsageEnum.FilterRides == usageEnum) {
            // we dont do stuff in this way
            callback.onResponseReceived(); // tell the caller everything is happy
            return;
        }

        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                fireRequestForTimeOfDay(display, geoLoc, callback);
                callback.onResponseReceived();
            }
        }));

    }

    private void fireRequestForTimeOfDay(
            final HomeScreenDisplay display,
            final GeoLoc geoLoc,
            final NotifyTimeAndDayCallback notifyTimeAndDayCallback) {

        WebServiceResponseConsumer<Root> callback = new WebServiceResponseConsumer<Root>() {
            @Override
            public void onResponseReceived(Root root) {
                Logger.getLogger("").log(Level.INFO, "Root retrieved from fireRequestForTimeOfDay");
                ramObjectCache.setRoot(root);
                setupDisplay(root);
                notifyTimeAndDayCallback.onResponseReceived();
            }

        };
        SearchByTimeOfDayRequest.Builder request = new SearchByTimeOfDayRequest.Builder(callback);
        request.latitude(geoLoc).longitude(geoLoc).send();
    }


}