package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/21/13 7:27 PM
 */

import com.bikefunfinder.client.shared.model.Location;

class LocationJSOWrapper implements DescribeableAsString<Location> {
    @Override
    public final String describeAsString(Location jsoObject) {

         return "Location(" +
                "city"+jsoObject.getCity()+"," +
                "country"+jsoObject.getCountry()+"," +
                "formattedAddress"+jsoObject.getFormattedAddress()+"," +
                "id"+jsoObject.getId()+"," +
                "state"+jsoObject.getState()+"," +
                "streetAddress"+jsoObject.getStreetAddress()+"," +
                "zip"+jsoObject.getZip()+"," +
                "geoLoc"+JSODescriber.describe(jsoObject.getGeoLoc())+")";
    }
}
