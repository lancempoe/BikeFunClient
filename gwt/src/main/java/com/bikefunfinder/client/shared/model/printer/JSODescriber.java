package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/21/13 7:20 PM
 */

import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Location;

public class JSODescriber {
    private static final GeoLocJSOWrapper geoLocJSOWrapper = new GeoLocJSOWrapper();
    private static final LocationJSOWrapper locationJSOWrapper = new LocationJSOWrapper();

    public static final String describe(GeoLoc geoLoc) {
        return geoLocJSOWrapper.describeAsString(geoLoc);
    }

    public static final String describe(Location location) {
        return locationJSOWrapper.describeAsString(location);
    }
}
