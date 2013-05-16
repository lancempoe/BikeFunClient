package com.bikefunfinder.client.gin;
/*
 * @author: tneuwerth
 * @created 5/15/13 6:08 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;

import java.util.List;

public interface RamObjectCache {
    List<BikeRide> getHereAndNowBikeRideCache();
    void setHereAndNowBikeRideCache(List<BikeRide> bikeRides);


}
