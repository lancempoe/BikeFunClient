package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.gmap.GMapPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NativeUtilities;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.bikefunfinder.client.shared.request.AnonymousRequest;
import com.bikefunfinder.client.shared.request.SearchByTimeOfDayRequest;
import com.bikefunfinder.client.shared.request.ratsnest.AnnonymousUserCacheStrategy;
import com.bikefunfinder.client.shared.request.ratsnest.GeoLocCacheStrategy;
import com.bikefunfinder.client.shared.request.ratsnest.WebServiceResponseConsumer;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

/**
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */
public class HomeScreenActivity extends MGWTAbstractActivity implements HomeScreenDisplay.Presenter {
    private final ClientFactory<HomeScreenDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private final HomeScreenPlace.UsageEnum usageEnum;
    private int geoFailCount = 0;

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
        //root will never be null

        final HomeScreenDisplay display = clientFactory.getDisplay(this);

        display.display(Extractor.getBikeRidesFrom(root));

        //Get City
        if (root.getBikeRides() != null &&
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
            display.display("Sorry, No Rides");   //TODO NEED TO MAKE BUTTONS SMALLER OTHERWISE THE SORRY NO RIDES IS TOO LONG.
        }

        NativeUtilities.trackPage("Home Screen");
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
        clientFactory.getPlaceController().goTo(new CreateScreenPlace(null, null, AnnonymousUserCacheStrategy.INSTANCE.getCachedType()));
    }

    @Override
    public void onSearchButton() {
        clientFactory.getPlaceController().goTo(new SearchScreenPlace());
    }

    @Override
    public void onLoginButton() {

        WebServiceResponseConsumer<AnonymousUser> callback = new WebServiceResponseConsumer<AnonymousUser>() {
            @Override
            public void onResponseReceived(AnonymousUser anonymousUser) {
            clientFactory.getPlaceController().goTo(new ProfileScreenPlace(null, anonymousUser));
            }
        };
        new AnonymousRequest.Builder(callback).send();
    }

    @Override
    public void onRideClick(BikeRide bikeRide) {
        clientFactory.getPlaceController().goTo(new EventScreenPlace(bikeRide));
    }

    @Override
    public void onTimeAndDayButton() {
        final HomeScreenDisplay display = clientFactory.getDisplay(this);

        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                fireRequestForTimeOfDay(display, geoLoc, noOpNotifyTimeAndDayCallback);
            }
        }, GeoLocCacheStrategy.INSTANCE));
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

        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                fireRequestForTimeOfDay(display, geoLoc, callback);
                callback.onResponseReceived();
            }
        }, GeoLocCacheStrategy.INSTANCE));

    }

    final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();

    private void fireRequestForTimeOfDay(
            final HomeScreenDisplay display,
            final GeoLoc geoLoc,
            final NotifyTimeAndDayCallback notifyTimeAndDayCallback) {

        WebServiceResponseConsumer<Root> callback = new WebServiceResponseConsumer<Root>() {
            @Override
            public void onResponseReceived(Root root) {
                ramObjectCache.setHereAndNowBikeRideCache(Extractor.getBikeRidesFrom(root));
                setupDisplay(root);
                notifyTimeAndDayCallback.onResponseReceived();
            }

        };
        SearchByTimeOfDayRequest.Builder request = new SearchByTimeOfDayRequest.Builder(callback);
        request.latitude(geoLoc).longitude(geoLoc).send();
    }


}