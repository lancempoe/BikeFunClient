package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/22/13 11:05 PM
 */

import com.bikefunfinder.client.shared.model.ClosestLocation;

public class ClosestLocationJSOWrapper implements DescribeableAsString<ClosestLocation> {
    @Override
    public final String describeAsString(ClosestLocation jsoObject) {

        return "ClosestLocation(" +
                    "Id"+jsoObject.getId()+"," +
                    "City"+jsoObject.getCity()+"," +
                    "State"+jsoObject.getState()+"," +
                    "GeoLoc"+JSODescriber.describe(jsoObject.getGeoLoc())+"," +
                    "longitude"+jsoObject.getLongitude()+"," +
                    "latitude"+jsoObject.getLatitude()+"," +
                    "formattedAddress"+jsoObject.getFormattedAddress()+"," +
                ")";
    }
}