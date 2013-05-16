package com.bikefunfinder.client.gin;
/*
 * @author: tneuwerth
 * @created 5/15/13 6:14 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;

import java.util.ArrayList;
import java.util.List;

public class RamObjectCacheImpl implements RamObjectCache {
    private final List<BikeRide> bikeRideList = new ArrayList<BikeRide>();

    @Override
    public List<BikeRide> getHereAndNowBikeRideCache() {
        return bikeRideList;
    }

    @Override
    public void setHereAndNowBikeRideCache(List<BikeRide> bikeRides) {
        bikeRideList.clear();
        bikeRideList.addAll(bikeRides);
    }
}
