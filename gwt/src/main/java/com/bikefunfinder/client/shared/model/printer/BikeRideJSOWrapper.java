package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/22/13 5:24 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
class BikeRideJSOWrapper implements DescribeableAsString<BikeRide> {
    @Override
    public final String describeAsString(BikeRide jsoObject) {

        return "BikeRide(" +
                    "Id"+jsoObject.getId()+"," +
                    "BikeRideName"+jsoObject.getBikeRideName()+"," +
                    "CityLocationId"+jsoObject.getCityLocationId()+"," +
                    "Details"+jsoObject.getDetails()+"," +
                    "DistanceFromClient"+jsoObject.getDistanceFromClient()+"," +
                    "DistanceTrackFromClient"+jsoObject.getDistanceTrackFromClient()+"," +
                    //"ImagePath"+jsoObject.getImagePath()+"," +
                    "RideLeaderId"+jsoObject.getRideLeaderId()+"," +
                    "RideLeaderName"+jsoObject.getRideLeaderName()+"," +
                    "RideStartTime"+jsoObject.getRideStartTime()+"," +
                    "TargetAudience"+jsoObject.getTargetAudience()+"," +
                    "TotalPeopleTrackingCount"+jsoObject.getTotalPeopleTrackingCount()+"," +
                    "location"+JSODescriber.describe(jsoObject.getLocation())+
                ")";
    }
}
