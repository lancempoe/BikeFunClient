package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/21/13 7:20 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Location;

public class JSODescriber {
    private static final DescribeableAsString<GeoLoc> geoLocJSOWrapper = new GeoLocJSOWrapper();
    private static final DescribeableAsString<Location> locationJSOWrapper = new LocationJSOWrapper();
    private static final DescribeableAsString<BikeRide> bikeRideJSOWrapper = new BikeRideJSOWrapper();

    public static final String describe(GeoLoc geoLoc) {
        return geoLocJSOWrapper.describeAsString(geoLoc);
    }

    public static final String describe(Location location) {
        return locationJSOWrapper.describeAsString(location);
    }

    public static final String describe(BikeRide bikeRide) {
        return bikeRideJSOWrapper.describeAsString(bikeRide);
    }
}
