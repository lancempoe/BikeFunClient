package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Location;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.showcase.client.about.AboutPlace;
import com.googlecode.gwtphonegap.showcase.client.accelerometer.AccelerometerPlace;
import com.googlecode.gwtphonegap.showcase.client.camera.CameraPlace;
import com.googlecode.gwtphonegap.showcase.client.compass.CompassPlace;
import com.googlecode.gwtphonegap.showcase.client.connection.ConnectionPlace;
import com.googlecode.gwtphonegap.showcase.client.contact.ContactPlace;
import com.googlecode.gwtphonegap.showcase.client.device.DevicePlace;
import com.googlecode.gwtphonegap.showcase.client.event.EventPlace;
import com.googlecode.gwtphonegap.showcase.client.file.FilePlace;
import com.googlecode.gwtphonegap.showcase.client.geolocation.GeolocationPlace;
import com.googlecode.gwtphonegap.showcase.client.gmap.GMapPlace;
import com.googlecode.gwtphonegap.showcase.client.inappbrowser.InAppBrowserPlace;
import com.googlecode.gwtphonegap.showcase.client.media.MediaPlace;
import com.googlecode.gwtphonegap.showcase.client.notification.NotificationPlace;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenActivity extends MGWTAbstractActivity implements HomeScreenDisplay.Presenter {
    private final ClientFactory clientFactory;
    private List<BikeRide> currentList;

    public HomeScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final HomeScreenDisplay display = clientFactory.getHomeScreenDisplay();


//        SearchByTimeOfDayRequest.Callback callback = new SearchByTimeOfDayRequest.Callback() {
//            @Override
//            public void onError() {
//                Window.alert("oh noes, server fail");
//            }
//
//            @Override
//            public void onResponseReceived(Root root) {
//                currentList = getModuleList();
//                display.display(currentList);
//            }
//        };
//        SearchByTimeOfDayRequest.Builder request = new SearchByTimeOfDayRequest.Builder(callback);
//        SearchByTimeOfDayRequest request2 = request.latitude(80.00).longitude(80.0).send();

        /*
        String json = "{      "+
       "     \"BikeRides\": [                                                                                         "+
       "     {                                                                                                        "+
       "         \"id\": \"514943d1e4b0776ff69f7153\",                                                                "+
       "             \"bikeRideName\": \"5: minus 1 day minus 1 minute in the past\",                                 "+
       "             \"rideStartTime\": \"1363669523454\",                                                             "+
       "             \"location\": {                                                                                  "+
       "         \"streetAddress\": \"2000 SE 82nd Ave.\",                                                            "+
       "                 \"city\": \"Portland\",                                                                      "+
       "                 \"state\": \"OR\",                                                                           "+
       "                 \"geoLoc\": {                                                                                "+
       "             \"longitude\": \"-122.57864379882812\",                                                          "+
       "                     \"latitude\": \"45.5081787109375\"                                                       "+
       "         },                                                                                                   "+
       "         \"formattedAddress\": \"2000 Southeast 82nd Avenue, Portland, OR 97216, USA\"                        "+
       "     },                                                                                                       "+
       "         \"imagePath\": \"Images/BikeRides/defaultBikeRide.jpg\",                                           "+
       "             \"trackingAllowed\": \"true\",                                                                   "+
       "             \"distanceFromClient\": \"3719.466993546847\",                                                   "+
       "             \"currentlyTracking\": \"false\",                                                                "+
       "             \"totalPeopleTrackingCount\": \"0\"                                                              "+
       "     },                                                                                                       "+
       "     {                                                                                                        "+
       "         \"id\": \"514943d1e4b0776ff69f7152\",                                                                "+
       "             \"bikeRideName\": \"4: minus 1 day plus 1 minute in the past\",                                  "+
       "             \"rideStartTime\": \"1363669643454\",                                                            "+
       "             \"location\": {                                                                                  "+
       "         \"streetAddress\": \"2000 SE 36th St.\",                                                             "+
       "                 \"city\": \"Portland\",                                                                      "+
       "                 \"state\": \"OR\",                                                                           "+
       "                 \"geoLoc\": {                                                                                "+
       "             \"longitude\": \"-122.62600708007812\",                                                          "+
       "                     \"latitude\": \"45.537601470947266\"                                                     "+
       "         },                                                                                                   "+
       "         \"formattedAddress\": \"2000 Northeast 36th Avenue, Portland, OR 97212, USA\"                        "+
       "     },                                                                                                       "+
       "         \"imagePath\": \"Images/BikeRides/defaultBikeRide.jpg\",                                           "+
       "             \"trackingAllowed\": \"true\",                                                                   "+
       "             \"distanceFromClient\": \"3717.251460188752\",                                                   "+
       "             \"currentlyTracking\": \"false\",                                                                "+
       "             \"totalPeopleTrackingCount\": \"0\"                                                              "+
       "     },                                                                                                       "+
       "     {                                                                                                        "+
       "         \"id\": \"514943d2e4b0776ff69f715a\",                                                                "+
       "             \"bikeRideName\": \"12: Started 1 min ago\",                                                     "+
       "             \"targetAudience\": \"0+\",                                                                      "+
       "             \"rideStartTime\": \"1363755925252\",                                                            "+
       "             \"location\": {                                                                                  "+
       "         \"streetAddress\": \"2000 SE 7th Ave.\",                                                             "+
       "                 \"city\": \"Portland\",                                                                      "+
       "                 \"state\": \"OR\",                                                                           "+
       "                 \"geoLoc\": {                                                                                "+
       "             \"longitude\": \"-122.6585693359375\",                                                           "+
       "                     \"latitude\": \"45.50858688354492\"                                                      "+
       "         },                                                                                                   "+
       "         \"formattedAddress\": \"2000 Southeast 7th Avenue, Portland, OR 97214, USA\"                         "+
       "     },                                                                                                       "+
       "         \"imagePath\": \"Images/BikeRides/defaultBikeRide.jpg\",                                           "+
       "             \"trackingAllowed\": \"true\",                                                                   "+
       "             \"distanceFromClient\": \"3719.1187237409354\",                                                  "+
       "             \"currentlyTracking\": \"false\",                                                                "+
       "             \"totalPeopleTrackingCount\": \"0\"                                                              "+
       "     },                                                                                                       "+
       "     {                                                                                                        "+
       "         \"id\": \"514943d2e4b0776ff69f715b\",                                                                "+
       "             \"bikeRideName\": \"13: Started 1 min ago\",                                                     "+
       "             \"targetAudience\": \"21+\",                                                                     "+
       "             \"rideStartTime\": \"1363755925252\",                                                            "+
       "             \"location\": {                                                                                  "+
       "         \"streetAddress\": \"2000 SE 7th Ave.\",                                                             "+
       "                 \"city\": \"Portland\",                                                                      "+
       "                 \"state\": \"OR\",                                                                           "+
       "                 \"geoLoc\": {                                                                                "+
       "             \"longitude\": \"-122.6585693359375\",                                                           "+
       "                     \"latitude\": \"45.50858688354492\"                                                      "+
       "         },                                                                                                   "+
       "         \"formattedAddress\": \"2000 Southeast 7th Avenue, Portland, OR 97214, USA\"                         "+
       "     },                                                                                                       "+
       "         \"imagePath\": \"Images/BikeRides/defaultBikeRide.jpg\",                                           "+
       "             \"trackingAllowed\": \"true\",                                                                   "+
       "             \"distanceFromClient\": \"3719.1187237409354\",                                                  "+
       "             \"currentlyTracking\": \"false\",                                                                "+
       "             \"totalPeopleTrackingCount\": \"0\"                                                              "+
       "     },                                                                                                       "+
       "     {                                                                                                        "+
       "         \"id\": \"514943d2e4b0776ff69f7159\",                                                                "+
       "             \"bikeRideName\": \"11: 10 minutes in the future, not close\",                                   "+
       "             \"rideStartTime\": \"1363756585252\",                                                            "+
       "             \"location\": {                                                                                  "+
       "         \"streetAddress\": \"2000 SE 82nd Ave\",                                                             "+
       "                 \"city\": \"Portland\",                                                                      "+
       "                 \"state\": \"OR\",                                                                           "+
       "                 \"geoLoc\": {                                                                                "+
       "             \"longitude\": \"-122.57864379882812\",                                                          "+
       "                     \"latitude\": \"45.5081787109375\"                                                       "+
       "         },                                                                                                   "+
       "         \"formattedAddress\": \"2000 Southeast 82nd Avenue, Portland, OR 97216, USA\"                        "+
       "     },                                                                                                       "+
       "         \"imagePath\": \"Images/BikeRides/defaultBikeRide.jpg\",                                           "+
       "             \"trackingAllowed\": \"true\",                                                                   "+
       "             \"distanceFromClient\": \"3719.466993546847\",                                                   "+
       "             \"currentlyTracking\": \"false\",                                                                "+
       "             \"totalPeopleTrackingCount\": \"0\"                                                              "+
       "     },                                                                                                       "+
       "     {                                                                                                        "+
       "         \"id\": \"514943d1e4b0776ff69f7154\",                                                                "+
       "             \"bikeRideName\": \"6: 59 minutes in the Future Ride\",                                          "+
       "             \"rideStartTime\": \"1363759525252\",                                                            "+
       "             \"location\": {                                                                                  "+
       "         \"streetAddress\": \"1500 SE Ash St.\",                                                              "+
       "                 \"city\": \"Portland\",                                                                      "+
       "                 \"state\": \"OR\",                                                                           "+
       "                 \"geoLoc\": {                                                                                "+
       "             \"longitude\": \"-122.65056610107422\",                                                          "+
       "                     \"latitude\": \"45.521461486816406\"                                                     "+
       "         },                                                                                                   "+
       "         \"formattedAddress\": \"1500 Southeast Ash Street, Portland, OR 97214, USA\"                         "+
       "     },                                                                                                       "+
       "         \"imagePath\": \"Images/BikeRides/defaultBikeRide.jpg\",                                           "+
       "             \"trackingAllowed\"\": \"true\",                                                                 "+
       "             \"distanceFromClient\": \"3718.264342378277\",                                                   "+
       "             \"currentlyTracking\": \"false\",                                                                "+
       "             \"totalPeopleTrackingCount\": \"0\"                                                              "+
       "     },                                                                                                       "+
       "     {                                                                                                        "+
       "         \"id\": \"514943d1e4b0776ff69f7155\",                                                                "+
       "             \"bikeRideName\": \"7: 61 minutes in the future\",                                               "+
       "             \"rideStartTime\": \"1363759645252\",                                                            "+
       "             \"location\": {                                                                                  "+
       "         \"streetAddress\": \"650 NE Holladay St\",                                                           "+
       "                 \"city\": \"Portland\",                                                                      "+
       "                 \"state\": \"OR\",                                                                           "+
       "                 \"geoLoc\": {                                                                                "+
       "             \"longitude\": \"-122.65895080566406\",                                                          "+
       "                     \"latitude\": \"45.52901840209961\"                                                      "+
       "         },                                                                                                   "+
       "         \"formattedAddress\": \"650 Northeast Holladay Street, Portland, OR 97232, USA\"                     "+
       "     },                                                                                                       "+
       "         \"imagePath\": \"Images/BikeRides/defaultBikeRide.jpg\",                                           "+
       "             \"trackingAllowed\": \"true\",                                                                   "+
       "             \"distanceFromClient\": \"3717.7103706379244\",                                                  "+
       "             \"currentlyTracking\": \"false\",                                                                "+
       "             \"totalPeopleTrackingCount\": \"0\"                                                              "+
       "     },                                                                                                       "+
       "     {                                                                                                        "+
       "         \"id\": \"514943d0e4b0776ff69f714e\",                                                                "+
       "             \"bikeRideName\": \"1: One Day in the Future Ride: Apple ride\",                                 "+
       "             \"rideStartTime\": \"1363842383454\",                                                            "+
       "             \"location\": {                                                                                  "+
       "         \"streetAddress\": \"1500 SE Ash St.\",                                                              "+
       "                 \"city\": \"Portland\",                                                                      "+
       "                 \"state\": \"OR\",                                                                           "+
       "                 \"geoLoc\": {                                                                                "+
       "             \"longitude\": \"-122.65056610107422\",                                                          "+
       "                     \"latitude\": \"45.521461486816406\"                                                     "+
       "         },                                                                                                   "+
       "         \"formattedAddress\": \"1500 Southeast Ash Street, Portland, OR 97214, USA\"                         "+
       "     },                                                                                                       "+
       "         \"imagePath\": \"Images/BikeRides/defaultBikeRide.jpg\",                                           "+
       "             \"trackingAllowed\": \"true\",                                                                   "+
       "             \"distanceFromClient\": \"3718.264342378277\",                                                   "+
       "             \"currentlyTracking\": \"false\",                                                                "+
       "             \"totalPeopleTrackingCount\": \"0\"                                                              "+
       "     },                                                                                                       "+
       "     {                                                                                                        "+
       "         \"id\": \"514943d0e4b0776ff69f714f\",                                                                "+
       "             \"bikeRideName\": \"2: Two Days in the future\",                                                 "+
       "             \"rideStartTime\": \"1363928783454\",                                                            "+
       "             \"location\": {                                                                                  "+
       "         \"streetAddress\": \"650 NE Holladay St\",                                                           "+
       "                 \"city\": \"Portland\",                                                                      "+
       "                 \"state\": \"OR\",                                                                           "+
       "                 \"geoLoc\": {                                                                                "+
       "             \"longitude\": \"-122.65895080566406\",                                                          "+
       "                     \"latitude\": \"45.52901840209961\"                                                      "+
       "         },                                                                                                   "+
       "         \"formattedAddress\": \"650 Northeast Holladay Street, Portland, OR 97232, USA\"                     "+
       "     },                                                                                                       "+
       "         \"imagePath\": \"Images/BikeRides/defaultBikeRide.jpg\",                                           "+
       "             \"trackingAllowed\": \"true\",                                                                   "+
       "             \"distanceFromClient\": \"3717.7103706379244\",                                                  "+
       "             \"currentlyTracking\": \"false\",                                                                "+
       "             \"totalPeopleTrackingCount\": \"0\"                                                              "+
       "     }                                                                                                        "+
       "     ],                                                                                                       "+
       "     \"ClosestLocation\": {                                                                                   "+
       "     \"id\": \"514943d0e4b0776ff69f714d\",                                                                    "+
       "             \"city\": \"Portland\",                                                                          "+
       "             \"state\": \"OR\",                                                                               "+
       "             \"geoLoc\": {                                                                                    "+
       "         \"longitude\": \"-122.67620849609375\",                                                              "+
       "                 \"latitude\": \"45.52345275878906\"                                                          "+
       "     },                                                                                                       "+
       "     \"formattedAddress\": \"Portland, OR, USA\"                                                              "+
       " }                                                                                                            "+
       " }";
       */

        /*
        String json = "{\n" +
                "  \"BikeRides\": [    {\n" +
                "      \"id\": \"51494f39e4b0776ff69f738d\",\n" +
                "      \"bikeRideName\": \"2: Two Days in the future\",\n" +
                "      \"rideStartTime\": \"1365486905310\",\n" +
                "      \"location\": {\n" +
                "        \"streetAddress\": \"650 NE Holladay St\",\n" +
                "        \"city\": \"Portland\",\n" +
                "        \"state\": \"OR\",\n" +
                "        \"geoLoc\": {\n" +
                "          \"longitude\": \"-122.65895080566406\",\n" +
                "          \"latitude\": \"45.52901840209961\"\n" +
                "        },\n" +
                "        \"formattedAddress\": \"650 Northeast Holladay Street, Portland, OR 97232, USA\"\n" +
                "      },\n" +
                "      \"imagePath\": \"Images\\/BikeRides\\/defaultBikeRide.jpg\",\n" +
                "      \"trackingAllowed\": \"true\",\n" +
                "      \"distanceFromClient\": \"3717.7103706379244\",\n" +
                "      \"currentlyTracking\": \"false\",\n" +
                "      \"totalPeopleTrackingCount\": \"0\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"ClosestLocation\": {\n" +
                "    \"id\": \"514943d0e4b0776ff69f714d\",\n" +
                "    \"city\": \"Portland\",\n" +
                "    \"state\": \"OR\",\n" +
                "    \"geoLoc\": {\n" +
                "      \"longitude\": \"-122.67620849609375\",\n" +
                "      \"latitude\": \"45.52345275878906\"\n" +
                "    },\n" +
                "    \"formattedAddress\": \"Portland, OR, USA\"\n" +
                "  }\n" +
                "}";
        */
        //String json = "[{\"kode\":\"002\",\"nama\":\"bambang gentolet\"},{\"kode\":\"012\",\"nama\":\"Algiz\"}]";
        String json = "[{\"kode\":\"002\",\"nama\":\"bambang gentolet\"},{\"kode\":\"012\",\"nama\":\"Algiz\"}]";

        currentList = getModuleList(json);
        display.display(currentList);

        display.setPresenter(this);

        panel.setWidget(display);
    }

    private List<BikeRide> getModuleList(String json) {
        String wrappedJson = json;

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
        Location location = testObjectParse(locationJson);
        Window.alert(JSODescriber.describe(location));

//        "      \"id\": \"51494f39e4b0776ff69f738d\"," +
//                "      \"bikeRideName\": \"2: Two Days in the future\"," +
//                "      \"rideStartTime\": \"1365486905310\"," +
//                "      \"location\": {" +
//                "        \"streetAddress\": \"650 NE Holladay St\"," +
//                "        \"city\": \"Portland\"," +
//                "        \"state\": \"OR\"," +
//                "        \"geoLoc\": {" +
//                "          \"longitude\": \"-122.65895080566406\"," +
//                "          \"latitude\": \"45.52901840209961\"" +
//                "        }," +
//                "        \"formattedAddress\": \"650 Northeast Holladay Street, Portland, OR 97232, USA\"" +



                //final Root composed = Root.overlay(json);

//        final Root composed = JsonUtils.safeEval(wrappedJson);
//        Window.alert("composed = " + composed);


//        JsArray<UserData> uda = JsonUtils.safeEval(wrappedJson);
//        Window.alert("uda size: "+uda.length());
//        int size = uda.length();
//        for(int index=0; index<size; index++) {
//            UserData userData = uda.get(index);
//            Window.alert("full :"+userData.getFullData());
//        }


//        Root uda = JsonUtils.safeEval(json);
//
//        Window.alert("string name: "+uda.toString());
//        Root cast = uda.cast();
//        Window.alert("cast is : "+cast);
//
//        Window.alert("cast source is : "+cast.toSource());
//        Window.alert("cast cast.getBikeRides():"+cast.getBikeRides());

//        for(BikeRide ride: cast.getBikeRides()) {
//            Window.alert("ride id :"+ride.getId());
//        }

        /*
        JSONValue jsonValue = JSONParser.parseStrict(json);
        JSONObject jsonObject = jsonValue.isObject(); // assert that this is an object
        if (jsonObject == null) {
            // uh oh, it wasn't an object after
            // do error handling here
            throw new RuntimeException("JSON payload did not describe an object");
        }

        Window.alert("casting");
        Root composed = jsonObject.getJavaScriptObject().cast();

        Window.alert("ok making list");
        Window.alert("tostr "+composed.toString());
        Window.alert("tosrc "+composed.toSource());
        Window.alert("brds "+composed.getBikeRides());
        */

        ArrayList<BikeRide> list = new ArrayList<BikeRide>();
//        list.addAll(composed.getBikeRides());
        return list;
    }

    private <T extends JavaScriptObject> T testObjectParse(String json) {
        if(!JsonUtils.safeToEval(json)) {
            Window.alert("woah nelly we're not safe to eval");
        }

        Window.alert("PepPep: "+json);

        T castingObject = JsonUtils.safeEval(json);
        return castingObject;
    }

    @Override
    public void onCellSelected(int index) {
        switch (index) {
            case 0:
                clientFactory.getPlaceController().goTo(new AccelerometerPlace());
                break;

            case 1:
                clientFactory.getPlaceController().goTo(new CameraPlace());
                break;

            case 2:
                clientFactory.getPlaceController().goTo(new CompassPlace());
                break;

            case 3:
                clientFactory.getPlaceController().goTo(new ConnectionPlace());
                break;

            case 4:
                clientFactory.getPlaceController().goTo(new ContactPlace());
                break;

            case 5:
                clientFactory.getPlaceController().goTo(new DevicePlace());
                break;

            case 6:
                clientFactory.getPlaceController().goTo(new EventPlace());
                break;
            case 7:
                clientFactory.getPlaceController().goTo(new FilePlace());
                break;
            case 8:
                clientFactory.getPlaceController().goTo(new GeolocationPlace());
                break;
            case 9:
                clientFactory.getPlaceController().goTo(new GMapPlace(clientFactory.getPhoneGap().getDevice().getUuid()));
                break;

            case 10:
                clientFactory.getPlaceController().goTo(new MediaPlace());
                break;
            case 11:
                clientFactory.getPlaceController().goTo(new NotificationPlace());
                break;

            case 12:
                clientFactory.getPlaceController().goTo(new InAppBrowserPlace());
                break;
        }

    }

    @Override
    public void onAboutButton() {
        clientFactory.getPlaceController().goTo(new AboutPlace());

    }

}
