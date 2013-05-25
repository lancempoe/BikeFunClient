package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.bootstrap.db.DBKeys;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.gmap.GMapPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.request.SearchByTimeOfDayRequest;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

import java.util.ArrayList;

import com.google.gwt.regexp.shared.*;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

/**
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */
public class HomeScreenActivity extends MGWTAbstractActivity implements HomeScreenDisplay.Presenter {
    private final ClientFactory<HomeScreenDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private final HomeScreenPlace.UsageEnum usageEnum;

    final NotifyTimeAndDayCallback noOpNotifyTimeAndDayCallback = new NotifyTimeAndDayCallback() {
        @Override public void onError() { } // noOp
        @Override public void onResponseReceived() { } // noOp
    };

    public HomeScreenActivity(Root root, HomeScreenPlace.UsageEnum usageEnum) {
        this.usageEnum = usageEnum;

        if(root == null ) {
            //save one and use a few times?
            //store in db maybe for later?
              refreshTimeAndDayReq(noOpNotifyTimeAndDayCallback);
              
//            String rootJson = com.bikefunfinder.client.shared.model.json.Utils.getTestingRootNodeJson(40);
//            Root largeFakeRoot = com.bikefunfinder.client.shared.model.json.Utils.castJsonTxtToJSOObject(rootJson);
//            setupDisplay(largeFakeRoot);

        } else {
            setupDisplay(root);
        }
    }

    private void setupDisplay(Root root) {
        final HomeScreenDisplay display = clientFactory.getDisplay(this);
        if(root != null) {
            display.display(Extractor.getBikeRidesFrom(root));
        }

        //Get City
        if (root != null &&
            root.getBikeRides() != null &&
            root.getBikeRides().length() > 0 &&
            root.getClosestLocation() != null)  {
            MatchResult matcher = buildMatcher(root.getClosestLocation().getFormattedAddress());
            boolean matchFound = (matcher != null); // equivalent to regExp.test(inputStr);
            if (matchFound) {
                display.display(matcher.getGroup(0));
            } else {
                if (root.getClosestLocation() != null &&
                    root.getClosestLocation().getFormattedAddress() != null) {
                    display.display("Unknown City");
                } else {
                    display.display("Search Results");
                }
            }
        }  else {
            display.display("Sorry, No Rides");
        }
    }

    private MatchResult buildMatcher(String formattedAddress) {
        RegExp regExp = RegExp.compile(ScreenConstants.RegularExpression_City);
        return regExp.exec(formattedAddress);
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final HomeScreenDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        panel.setWidget(display);
    }

    @Override
    public void onNewButton() {
        clientFactory.getPlaceController().goTo(new CreateScreenPlace());
    }

    @Override
    public void onSearchButton() {
        clientFactory.getPlaceController().goTo(new SearchScreenPlace());
    }

    @Override
    public void onLoginButton() {
        clientFactory.getPlaceController().goTo(new ProfileScreenPlace());
    }

    @Override
    public void onRideClick(BikeRide bikeRide) {
        clientFactory.getPlaceController().goTo(new EventScreenPlace(bikeRide));
    }

    @Override
    public void onTimeAndDayButton() {
        final HomeScreenDisplay display = clientFactory.getDisplay(this);

        DeviceTools.getPhoneGeoLoc(clientFactory, new NonPhoneGapGeoLocCallback() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                fireRequestForTimeOfDay(display, geoLoc, noOpNotifyTimeAndDayCallback);

            }

            @Override
            public void onFailure(GeoLoc geoLoc) {
                Dialogs.alert("Warning", "Your GPS location is currently unavailable, we will show you results for Portland Oregon.", new Dialogs.AlertCallback() {
                    @Override
                    public void onButtonPressed() {
                    }
                });
                fireRequestForTimeOfDay(display, geoLoc, noOpNotifyTimeAndDayCallback);
            }
        });
    }

    @Override
    public void onHereAndNowButton() {
        clientFactory.getPlaceController().goTo(new GMapPlace("Here & Now"));
    }

    @Override
    public void refreshTimeAndDayReq(final NotifyTimeAndDayCallback callback) {
        if(HomeScreenPlace.UsageEnum.ShowMyRides == usageEnum ||
           HomeScreenPlace.UsageEnum.FilterRides == usageEnum) {
            // we dont do stuff in this way
            callback.onResponseReceived(); // tell the caller everything is happy
            return;
        }

        final HomeScreenDisplay display = clientFactory.getDisplay(this);

        DeviceTools.getPhoneGeoLoc(clientFactory, new NonPhoneGapGeoLocCallback() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                fireRequestForTimeOfDay(display, geoLoc, callback);
                callback.onResponseReceived();
            }

            @Override
            public void onFailure(GeoLoc geoLoc) {
                Dialogs.alert("Warning", "Your GPS location is currently unavailable, we will show you results for Portland Oregon.", new Dialogs.AlertCallback() {
                    @Override
                    public void onButtonPressed() {
                    }
                });
                fireRequestForTimeOfDay(display, geoLoc, callback);
                callback.onResponseReceived();
            }
        });

    }

    final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();

    private void fireRequestForTimeOfDay(
            final HomeScreenDisplay display,
            final GeoLoc geoLoc,
            final NotifyTimeAndDayCallback notifyTimeAndDayCallback) {

        SearchByTimeOfDayRequest.Callback callback = new SearchByTimeOfDayRequest.Callback() {
            @Override
            public void onResponseReceived(Root root) {
                ramObjectCache.setHereAndNowBikeRideCache(Extractor.getBikeRidesFrom(root));
                setupDisplay(root);
                notifyTimeAndDayCallback.onResponseReceived();
            }

            @Override
            public void onError() {
                display.display(new ArrayList<BikeRide>());
                display.display("City Unknown");
                notifyTimeAndDayCallback.onError();
            }
        };
        SearchByTimeOfDayRequest.Builder request = new SearchByTimeOfDayRequest.Builder(callback);
        request.latitude(geoLoc).longitude(geoLoc).send();
    }


}