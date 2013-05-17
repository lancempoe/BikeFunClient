package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/21/13 7:27 PM
 */

import com.bikefunfinder.client.shared.model.Location;

class LocationJSOWrapper implements DescribeableAsString<Location> {
    @Override
    public final String describeAsString(Location jsoObject) {

        final String city = (jsoObject==null) ? "null" :jsoObject.getCity();
        final String country = (jsoObject==null) ? "null" :jsoObject.getCountry();
        final String formattedAddress = (jsoObject==null) ? "null" :jsoObject.getFormattedAddress();
        final String id = (jsoObject==null) ? "null" :jsoObject.getId();
        final String state = (jsoObject==null) ? "null" :jsoObject.getState();
        final String streetAddress = (jsoObject==null) ? "null" :jsoObject.getStreetAddress();
        final String zip = (jsoObject==null) ? "null" :jsoObject.getZip();
        final String describe = (jsoObject==null) ? "null" :JSODescriber.describe(jsoObject.getGeoLoc());
        return "Location(" +
                "city"+ city +"," +
                "country"+ country +"," +
                "formattedAddress"+ formattedAddress +"," +
                "id"+ id +"," +
                "state"+ state +"," +
                "streetAddress"+ streetAddress +"," +
                "zip"+ zip +"," +
                "geoLoc"+ describe +")";
    }
}
