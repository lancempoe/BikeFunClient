package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/21/13 7:20 PM
 */

import com.bikefunfinder.client.shared.model.*;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class JSODescriber {
    private static final DescribeableAsString<GeoLoc> geoLocJSOWrapper = new GeoLocJSOWrapper();
    private static final DescribeableAsString<Location> locationJSOWrapper = new LocationJSOWrapper();
    private static final DescribeableAsString<BikeRide> bikeRideJSOWrapper = new BikeRideJSOWrapper();
    private static final DescribeableAsString<ClosestLocation> closestLocationJSOWrapper = new ClosestLocationJSOWrapper();
    private static final DescribeableAsString<Root> rootJSOWrapper = new RootJSOWrapper();



    public static final String describe(GeoLoc geoLoc) {
        return geoLocJSOWrapper.describeAsString(geoLoc);
    }

    public static final String describe(Location location) {
        return locationJSOWrapper.describeAsString(location);
    }

    public static final String describe(BikeRide bikeRide) {
        return bikeRideJSOWrapper.describeAsString(bikeRide);
    }

    public static final String describe(ClosestLocation closestLocation) {
        return closestLocationJSOWrapper.describeAsString(closestLocation);
    }

    public static final String describe(Root root) {
        return rootJSOWrapper.describeAsString(root);
    }

    public static final String toJSON(JavaScriptObject jso) {
        return new JSONObject(jso).toString();
    }

}
