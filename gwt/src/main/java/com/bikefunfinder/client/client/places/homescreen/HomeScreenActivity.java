package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.bikefunfinder.client.shared.model.*;
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

    public HomeScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final HomeScreenDisplay display = clientFactory.getHomeScreenDisplay();

        display.setPresenter(this);

        panel.setWidget(display);

        usePhoneLocationToMakeTimeOfDayRequestAndUpdateDisplay(display);
        //currentList = getModuleList(justForShits());
        //display.display(currentList);

        Root root = testObjectParse(justForShits());
        display.display(root.getClosestLocation().getCity());
    }


    private List<BikeRide> getModuleList(Root root) {
        ArrayList<BikeRide> list = new ArrayList<BikeRide>();
        JsArray<BikeRide> bikeRides = root.getBikeRides();
        final int numBikeRides = bikeRides.length();
        for(int index=0; index< numBikeRides; index++) {
            list.add(bikeRides.get(index));
        }

        return list;
    }

    private List<BikeRide> getModuleList(String rootJson) {
        Root root = testObjectParse(rootJson);
        return getModuleList(root);
    }

    private String justForShits() {
        String geoJson = "{\"longitude\":\"-122.65895080566406\",\"latitude\":\"45.52901840209961\"}";
        //GeoLoc geoLoc = testObjectParse(geoJson);
        //Window.alert(JSODescriber.describe(geoLoc));

        String locationJson = "{ " +
                " \"streetAddress\": \"650 NE Holladay St\"," +
                " \"city\": \"Portland\"," +
                " \"state\": \"OR\"," +
                " \"geoLoc\": " +geoJson+", "+
                " \"formattedAddress\": \"650 Northeast Holladay Street, Portland, OR 97232, USA\"" +
                " }";
//        Location location = testObjectParse(locationJson);
//        Window.alert(JSODescriber.describe(location));

        String bikeRideJson = "{" +
                " \"id\": \"51494f39e4b0776ff69f738d\"," +
                " \"bikeRideName\": \"2: Two Days in the future\"," +
                " \"rideStartTime\": \"1365486905310\"," +
                " \"location\": "+locationJson+"," +
                " \"imagePath\": \"Images\\/BikeRides\\/defaultBikeRide.jpg\"," +
                " \"trackingAllowed\": \"true\"," +
                " \"distanceFromClient\": \"3717.7103706379244\"," +
                " \"currentlyTracking\": \"false\"," +
                " \"totalPeopleTrackingCount\": \"0\"" +
              "}";
//        BikeRide bikeRide = testObjectParse(bikeRideJson);
//        Window.alert(JSODescriber.describe(bikeRide));

        String closestLocationJson = "{" +
        "    \"id\": \"514943d0e4b0776ff69f714d\"," +
                "    \"city\": \"Portland\"," +
                "    \"state\": \"OR\"," +
                "    \"geoLoc\": {" +
                "      \"longitude\": \"-122.67620849609375\"," +
                "      \"latitude\": \"45.52345275878906\"" +
                "    }," +
                "    \"formattedAddress\": \"Portland, OR, USA\"" +
                "  }";
//        ClosestLocation closestLocation = testObjectParse(closestLocationJson);
//        Window.alert(JSODescriber.describe(closestLocation));


        String rootJson = "{" +
                "  \"BikeRides\": [ "+ bikeRideJson + ","+ bikeRideJson + "  ]," +
                "  \"ClosestLocation\": "+closestLocationJson+"," +
                "  \"formattedAddress\": \"Portland, OR, USA\"" +
            "}";

//        Root root = testObjectParse(rootJson);
//        Window.alert(JSODescriber.describe(root));
        return rootJson;
    }

    public static <T extends JavaScriptObject> T testObjectParse(String json) {
        if(!JsonUtils.safeToEval(json)) {
            Window.alert("woah nelly we're not safe to eval");
        }

        //Window.alert("PepPep: "+json);
        //Window.alert("jsonLenght: "+json.length());


        T castingObject = JsonUtils.safeEval(json);
        return castingObject;
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
        final HomeScreenDisplay display = clientFactory.getHomeScreenDisplay();
        usePhoneLocationToMakeHereAndNowRequestAndUpdateDisplay(display);
    }

    private void fireRequestForTimeOfDay(final HomeScreenDisplay display, final double latitude, final double longitude) {
        SearchByTimeOfDayRequest.Callback callback = new SearchByTimeOfDayRequest.Callback() {
            @Override
            public void onError() {
                Window.alert("oh noes, server fail11");
            }

            @Override
            public void onResponseReceived(Root root) {
                currentList = getModuleList(root);
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
                Window.alert("Failed to get geo log.. using 80,80");
                fireRequestForTimeOfDay(display, 80, 80);
            }
        };

        Geolocation phoneGeoLocation = clientFactory.getPhoneGap().getGeolocation();
        phoneGeoLocation.getCurrentPosition(geolocationCallback, options);
    }


    private void fireRequestForHereAndNow(final HomeScreenDisplay display, final double latitude, final double longitude) {
        SearchByProximityRequest.Callback callback = new SearchByProximityRequest.Callback() {
            @Override
            public void onError() {
                Window.alert("oh noes, server fail11");
            }

            @Override
            public void onResponseReceived(Root root) {
                currentList = getModuleList(root);
                display.display(currentList);
                display.display(root.getClosestLocation().getCity());
            }
        };
        SearchByProximityRequest.Builder request = new SearchByProximityRequest.Builder(callback);
        request.latitude(latitude).longitude(longitude).send();
    }

    private void usePhoneLocationToMakeHereAndNowRequestAndUpdateDisplay(final HomeScreenDisplay display) {
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

                fireRequestForHereAndNow(display, latitude, longitude);
            }

            @Override
            public void onFailure(final PositionError error) {
                Window.alert("Failed to get geo log.. using 80,80");
                fireRequestForTimeOfDay(display, 80, 80);
            }
        };

        Geolocation phoneGeoLocation = clientFactory.getPhoneGap().getGeolocation();
        phoneGeoLocation.getCurrentPosition(geolocationCallback, options);
    }
}