package com.bikefunfinder.client.shared.model.json;
/*
 * @author: tneuwerth
 * @created 4/25/13 7:35 PM
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.Window;

public class Utils {
    private Utils() {
        throw new RuntimeException("hey, this is private no touchies!");
    }

    public static <T extends JavaScriptObject> T castJsonTxtToJSOObject(String json) {
        if(!JsonUtils.safeToEval(json)) {
            Window.alert("Woah nelly.  Unknown date.");
        }

        //Window.alert("PepPep: "+json);
        //Window.alert("jsonLenght: "+json.length());


        T castingObject = JsonUtils.safeEval(json);
        return castingObject;
    }


    //Indeed just for shits.
    private String justForShits() {
        String geoJson = "{\"longitude\":\"-122.65895080566406\",\"latitude\":\"45.52901840209961\"}";
        //GeoLoc geoLoc = castJsonTxtToJSOObject(geoJson);
        //Window.alert(JSODescriber.describe(geoLoc));

        String locationJson = "{ " +
                " \"streetAddress\": \"650 NE Holladay St\"," +
                " \"city\": \"Portland\"," +
                " \"state\": \"OR\"," +
                " \"geoLoc\": " +geoJson+", "+
                " \"formattedAddress\": \"650 Northeast Holladay Street, Portland, OR 97232, USA\"" +
                " }";
//        Location location = castJsonTxtToJSOObject(locationJson);
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
//        BikeRide bikeRide = castJsonTxtToJSOObject(bikeRideJson);
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
//        ClosestLocation closestLocation = castJsonTxtToJSOObject(closestLocationJson);
//        Window.alert(JSODescriber.describe(closestLocation));


        String rootJson = "{" +
                "  \"BikeRides\": [ "+ bikeRideJson + ","+ bikeRideJson + "  ]," +
                "  \"ClosestLocation\": "+closestLocationJson+"," +
                "  \"formattedAddress\": \"Portland, OR, USA\"" +
                "}";

//        Root root = castJsonTxtToJSOObject(rootJson);
//        Window.alert(JSODescriber.describe(root));
        return rootJson;
    }



}
