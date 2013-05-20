package com.bikefunfinder.client.gin;
/*
 * @author: tneuwerth
 * @created 5/15/13 6:14 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.google.gwt.core.client.GWT;

import java.util.ArrayList;
import java.util.List;

public class RamObjectCacheImpl implements RamObjectCache {
    private final List<BikeRide> bikeRideList = new ArrayList<BikeRide>();
    private BikeRide currentBikeRide;
    private GeoLoc currentPhoneGeoLoc;

    @Override
    public List<BikeRide> getHereAndNowBikeRideCache() {
        return bikeRideList;
    }

    @Override
    public void setHereAndNowBikeRideCache(List<BikeRide> bikeRides) {
        bikeRideList.clear();
        bikeRideList.addAll(bikeRides);
    }

    @Override
    public BikeRide getCurrentBikeRide() {
        return currentBikeRide;
    }

    @Override
    public void setCurrentBikeRide(BikeRide bikeRide) {
        this.currentBikeRide = bikeRide;
    }

    @Override
    public GeoLoc getCurrentPhoneGeoLoc() {
        return currentPhoneGeoLoc;
    }

    @Override
    public void setCurrentPhoneGeoLoc(GeoLoc phoneGeoLoc) {
        this.currentPhoneGeoLoc = phoneGeoLoc;
    }
}
