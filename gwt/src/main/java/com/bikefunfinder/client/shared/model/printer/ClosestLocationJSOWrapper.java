package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/22/13 11:05 PM
 */

import com.bikefunfinder.client.shared.model.ClosestLocation;

public class ClosestLocationJSOWrapper implements DescribeableAsString<ClosestLocation> {
    @Override
    public final String describeAsString(ClosestLocation jsoObject) {

        final String id = (jsoObject==null) ? "null" :jsoObject.getId();
        final String city = (jsoObject==null) ? "null" :jsoObject.getCity();
        final String state = (jsoObject==null) ? "null" :jsoObject.getState();
        final String geoLocationJson = (jsoObject==null) ? "null" :JSODescriber.describe(jsoObject.getGeoLoc());
        final String longitude = (jsoObject==null) ? "null" :jsoObject.getLongitude();
        final String latitude = (jsoObject==null) ? "null" :jsoObject.getLatitude();
        final String formattedAddress = (jsoObject==null) ? "null" :jsoObject.getFormattedAddress();
        return "ClosestLocation(" +
                    "Id"+ id +"," +
                    "City"+ city +"," +
                    "State"+ state +"," +
                    "GeoLoc"+ geoLocationJson +"," +
                    "longitude"+ longitude +"," +
                    "latitude"+ latitude +"," +
                    "formattedAddress"+ formattedAddress +"," +
                ")";
    }
}