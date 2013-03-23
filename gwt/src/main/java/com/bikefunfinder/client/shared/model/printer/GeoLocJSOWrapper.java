package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/21/13 7:13 PM
 */

import com.bikefunfinder.client.shared.model.GeoLoc;

class GeoLocJSOWrapper implements DescribeableAsString<GeoLoc> {
    @Override
    public final String describeAsString(GeoLoc jsoObject) {
        return "GeoLoc(lat="+jsoObject.getLatitude() + ", " +
                      "long=" + jsoObject.getLongitude()+")";

    }
}
