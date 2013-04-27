package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.bikefunfinder.client.shared.request.SearchByProximityRequest;
import com.bikefunfinder.client.shared.request.SearchByTimeOfDayRequest;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.geolocation.*;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.bikefunfinder.client.client.places.gmap.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */
public class HomeScreenActivity extends MGWTAbstractActivity implements HomeScreenDisplay.Presenter {
    private final ClientFactory clientFactory;
    private GeolocationWatcher watcher = null;
    private List<BikeRide> currentList;
    private Root root;

    public HomeScreenActivity(ClientFactory clientFactory, Root root) {
        this.clientFactory = clientFactory;
        this.root = root;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final HomeScreenDisplay display = clientFactory.getHomeScreenDisplay();

        display.setPresenter(this);

        panel.setWidget(display);

        usePhoneLocationToMakeTimeOfDayRequestAndUpdateDisplay(display);
    }

    private List<BikeRide> getModuleList(String rootJson) {
        Root root = Utils.castJsonTxtToJSOObject(rootJson);
        return Extractor.getBikeRidesFrom(root);
    }

    @Override
    public void onNewButton() {

        clientFactory.testLocalStorage();
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
        final EventScreenDisplay display = clientFactory.getEventScreenDisplay();
        display.display(bikeRide);
        clientFactory.getPlaceController().goTo(new EventScreenPlace());
    }

    @Override
    public void onTimeAndDayButton() {
        final HomeScreenDisplay display = clientFactory.getHomeScreenDisplay();
        usePhoneLocationToMakeTimeOfDayRequestAndUpdateDisplay(display);
    }

    @Override
    public void onHereAndNowButton() {
        clientFactory.getPlaceController().goTo(new GMapPlace("bookMark"));
    }

    private void fireRequestForTimeOfDay(final HomeScreenDisplay display, final double latitude, final double longitude) {
        SearchByTimeOfDayRequest.Callback callback = new SearchByTimeOfDayRequest.Callback() {
            @Override
            public void onError() {
                Window.alert("Oops, your BFF will be back shortly.");
                display.display(new ArrayList<BikeRide>());
                display.display("City Unknown");
            }

            @Override
            public void onResponseReceived(Root root) {
                currentList = Extractor.getBikeRidesFrom(root);
                display.display(currentList);
                display.display(root.getClosestLocation().getCity());
            }
        };
        SearchByTimeOfDayRequest.Builder request = new SearchByTimeOfDayRequest.Builder(callback);
        request.latitude(latitude).longitude(longitude).send();
    }

    private void usePhoneLocationToMakeTimeOfDayRequestAndUpdateDisplay(final HomeScreenDisplay display) {
        final GeolocationOptions options = new GeolocationOptions();
        options.setEnableHighAccuracy(true);
        options.setTimeout(3000);
        options.setMaximumAge(1000);

        final GeolocationCallback geolocationCallback = new GeolocationCallback() {
            @Override
            public void onSuccess(final Position position) {

                final Coordinates coordinates = position.getCoordinates();
                final double latitude = coordinates.getLatitude();
                final double longitude = coordinates.getLongitude();

                fireRequestForTimeOfDay(display, latitude, longitude);
            }

            @Override
            public void onFailure(final PositionError error) {
                Window.alert("Failed to get GeoLocation.  Using Portland as default.");
                fireRequestForTimeOfDay(display, 45.52345275878906, -122.6762084960938);
            }
        };

        Geolocation phoneGeoLocation = clientFactory.getPhoneGap().getGeolocation();
        phoneGeoLocation.getCurrentPosition(geolocationCallback, options);
    }



}