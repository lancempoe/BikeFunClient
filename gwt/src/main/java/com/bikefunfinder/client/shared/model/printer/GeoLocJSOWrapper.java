package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/21/13 7:13 PM
 */

import com.bikefunfinder.client.shared.model.GeoLoc;

class GeoLocJSOWrapper implements DescribeableAsString<GeoLoc> {
    @Override
    public final String describeAsString(GeoLoc jsoObject) {
        final String latitude = (jsoObject==null) ? "null" : jsoObject.getLatitude();
        final String longitude = (jsoObject==null) ? "null" : jsoObject.getLongitude();

        return "GeoLoc(lat="+ latitude + ", " +
                      "long=" + longitude +")";

    }
}
