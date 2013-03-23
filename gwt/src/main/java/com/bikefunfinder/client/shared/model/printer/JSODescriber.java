package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/21/13 7:20 PM
 */

import com.bikefunfinder.client.shared.model.*;

public class JSODescriber {
    private static final DescribeableAsString<GeoLoc> geoLocJSOWrapper = new GeoLocJSOWrapper();
    private static final DescribeableAsString<Location> locationJSOWrapper = new LocationJSOWrapper();
    private static final DescribeableAsString<BikeRide> bikeRideJSOWrapper = new BikeRideJSOWrapper();
    private static final DescribeableAsString<ClosestLocation> closestLocationJSOWrapper = new ClosestLocationJSOWrapper();

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
}
