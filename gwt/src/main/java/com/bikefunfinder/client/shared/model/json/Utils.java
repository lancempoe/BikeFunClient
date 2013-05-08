package com.bikefunfinder.client.shared.model.json;
/*
 * @author: tneuwerth
 * @created 4/25/13 7:35 PM
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.Window;


import java.util.Date;

public class Utils {
    private static final int MILLISECONDS_IN_A_DAY = 86400000;
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
    public static String getTestingRootNodeJson(int numberOfRides) {

        Date date = new Date();

        int dayCounterIndex=0;

        StringBuilder stringBuilder = new StringBuilder();
        for(int index=0;index<numberOfRides;index++) {
            stringBuilder.append(buildBikeRideJson(Long.toString(date.getTime())));
            if(index<(numberOfRides-1)) {
                stringBuilder.append(",");
            }

            if(dayCounterIndex>=7) {
                dayCounterIndex = 0;
                date.setTime(date.getTime()+MILLISECONDS_IN_A_DAY);
            } else {
                dayCounterIndex++;
            }
        }

        String rootJson = "{" +
                "  \"BikeRides\": [ "+ stringBuilder.toString() + "  ]," +
                "  \"ClosestLocation\": "+buildClosestLocationJson()+"," +
                "  \"formattedAddress\": \"Portland, OR, USA\"" +
                "}";

//        Root root = castJsonTxtToJSOObject(rootJson);
//        Window.alert(JSODescriber.describe(root));
        return rootJson;
    }

    private static String buildClosestLocationJson() {
        return "{" +
                    "    \"id\": \"514943d0e4b0776ff69f714d\"," +
                    "    \"city\": \"Portland\"," +
                    "    \"state\": \"OR\"," +
                    "    \"geoLoc\": {" +
                    "      \"longitude\": \"-122.67620849609375\"," +
                    "      \"latitude\": \"45.52345275878906\"" +
                    "    }," +
                    "    \"formattedAddress\": \"Portland, OR, USA\"" +
                    "  }";
    }

    private static String buildGeoLocationJson() {
        return "{\"longitude\":\"-122.65895080566406\",\"latitude\":\"45.52901840209961\"}";
    }

    private static String buildLocationJson(String geoJson) {
        return "{ " +
                    " \"streetAddress\": \"650 NE Holladay St\"," +
                    " \"city\": \"Portland\"," +
                    " \"state\": \"OR\"," +
                    " \"geoLoc\": " +geoJson+", "+
                    " \"formattedAddress\": \"650 Northeast Holladay Street, Portland, OR 97232, USA\"" +
                    " }";
    }

    private static String buildBikeRideJson(String startTimeMillies) {

        String geoJson = buildGeoLocationJson();
        String locationJson = buildLocationJson(geoJson);

        return "{" +
                    " \"id\": \"51494f39e4b0776ff69f738d\"," +
                    " \"bikeRideName\": \"2: Two Days in the future\"," +
                    " \"rideStartTime\": "+startTimeMillies+"," +
                    " \"location\": "+locationJson+"," +
                    " \"imagePath\": \"Images\\/BikeRides\\/defaultBikeRide.jpg\"," +
                    " \"trackingAllowed\": \"true\"," +
                    " \"distanceFromClient\": \"3717.7103706379244\"," +
                    " \"currentlyTracking\": \"false\"," +
                    " \"totalPeopleTrackingCount\": \"0\"" +
                    "}";
    }


}
