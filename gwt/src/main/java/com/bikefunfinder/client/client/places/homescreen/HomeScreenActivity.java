package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.gmap.GMapPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.bikefunfinder.client.shared.request.SearchByTimeOfDayRequest;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.geolocation.*;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay.*;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.CellGroup;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.StandardCellGroup;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.regexp.shared.*;

/**
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */
public class HomeScreenActivity extends MGWTAbstractActivity implements HomeScreenDisplay.Presenter {
    private final ClientFactory<HomeScreenDisplay> clientFactory;

    final NotifyTimeAndDayCallback noOpNotifyTimeAndDayCallback = new NotifyTimeAndDayCallback() {
        @Override public void onError() { } // noOp
        @Override public void onResponseReceived() { } // noOp
    };

    public HomeScreenActivity(ClientFactory<HomeScreenDisplay> clientFactory, Root root) {
        this.clientFactory = clientFactory;


        if(root == null ) {
            //save one and use a few times?
            //store in db maybe for later?
            usePhoneLocationToMakeTimeOfDayRequestAndUpdateDisplay(
                    clientFactory.getDisplay(this),
                    noOpNotifyTimeAndDayCallback);

//            String rootJson = com.bikefunfinder.client.shared.model.json.Utils.getTestingRootNodeJson(40);
//            Root largeFakeRoot = com.bikefunfinder.client.shared.model.json.Utils.castJsonTxtToJSOObject(rootJson);
//            setupDisplay(largeFakeRoot);

        } else {
            setupDisplay(root);
        }


    }

    private List<CellGroup<Header, Content>> buildList(List<BikeRide> bikeRides) {
        ArrayList<CellGroup<Header, Content>> list = new ArrayList<CellGroup<Header, Content>>();

        Header header  = null;
        ArrayList<Content> contentList = new ArrayList<Content>();
        JsDateWrapper priorDate = null;
        for (BikeRide bikeRide : bikeRides) {

            JsDateWrapper bikeRideDate = bikeRide.createJsDateWrapperRideStartTime();
            if (priorDate == null || !priorDate.isSameDay(bikeRideDate)) {
                if (header != null && contentList.size() > 0) {
                    CellGroup<Header, Content> cellGroup = new StandardCellGroup<Header, Content>(header.getName(), header, contentList);
                    list.add(cellGroup);
                }
                priorDate = bikeRideDate;
                header = new Header(bikeRideDate.toString("dddd, MMMM dd, yyyy"));
                contentList = new ArrayList<Content>();
            }

            //Build content of 1 ride
            String timeString = bikeRideDate.toString("h:mm tt");
            StringBuilder html = new StringBuilder()
                    .append("<h2>").append(bikeRide.getBikeRideName()).append("</h2>")
                    .append("<p>").append(timeString).append("</p>")
                    .append("<p>").append(bikeRide.getDetails()).append("</p>");
            contentList.add(new Content(html.toString()));
        }

        //add the final item
        if (header != null && contentList.size() > 0) {
            CellGroup<Header, Content> cellGroup = new StandardCellGroup<Header, Content>(header.getName(), header, contentList);
            list.add(cellGroup);
        }

        return list;
    }

    private void setupDisplay(Root root) {
        final HomeScreenDisplay display = clientFactory.getDisplay(this);
        if(root != null) {
            display.display(Extractor.getBikeRidesFrom(root));
            //TODO PUT HERE?
        }

        //Get City
        if (root != null && root.getClosestLocation() != null)  {
            MatchResult matcher = buildMatcher(root.getClosestLocation().getFormattedAddress());
            boolean matchFound = (matcher != null); // equivalent to regExp.test(inputStr);
            if (matchFound) {
                display.display(matcher.getGroup(0));
            } else {
                display.display("Unknown City");
            }
        }  else {
            display.display("No Upcoming Rides");
        }
    }

    private MatchResult buildMatcher(String formattedAddress) {
        RegExp regExp = RegExp.compile("^(.*),");
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

        clientFactory.validateValidUser();
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

        usePhoneLocationToMakeTimeOfDayRequestAndUpdateDisplay(display, noOpNotifyTimeAndDayCallback);
    }

    @Override
    public void onHereAndNowButton() {
        clientFactory.getPlaceController().goTo(new GMapPlace("bookMark"));
    }

    @Override
    public void refreshTimeAndDayReq(NotifyTimeAndDayCallback callback) {
        final HomeScreenDisplay display = clientFactory.getDisplay(this);
        usePhoneLocationToMakeTimeOfDayRequestAndUpdateDisplay(display, callback);
    }

    private void fireRequestForTimeOfDay(
            final HomeScreenDisplay display,
            final double latitude,
            final double longitude,
            final NotifyTimeAndDayCallback notifyTimeAndDayCallback) {
        SearchByTimeOfDayRequest.Callback callback = new SearchByTimeOfDayRequest.Callback() {
            @Override
            public void onError() {
                Window.alert("Oops, your BFF will be back shortly.");
                display.display(new ArrayList<BikeRide>());
                display.display("City Unknown");
                notifyTimeAndDayCallback.onError();
            }

            @Override
            public void onResponseReceived(Root root) {
                setupDisplay(root);
                notifyTimeAndDayCallback.onResponseReceived();
            }
        };
        SearchByTimeOfDayRequest.Builder request = new SearchByTimeOfDayRequest.Builder(callback);
        request.latitude(latitude).longitude(longitude).send();
    }

    private void usePhoneLocationToMakeTimeOfDayRequestAndUpdateDisplay(
            final HomeScreenDisplay display,
            final NotifyTimeAndDayCallback callback) {
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

                fireRequestForTimeOfDay(display, latitude, longitude, callback);
            }

            @Override
            public void onFailure(final PositionError error) {
                Window.alert("Failed to get GeoLocation.  Using Portland as default.");

                fireRequestForTimeOfDay(display, ScreenConstants.PORTLAND_LATITUDE,
	                ScreenConstants.PORTLAND_LOGITUDE,
    	            callback);
            }
        };

        Geolocation phoneGeoLocation = clientFactory.getPhoneGap().getGeolocation();
        phoneGeoLocation.getCurrentPosition(geolocationCallback, options);
    }
}