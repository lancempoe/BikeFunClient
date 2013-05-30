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
import java.util.logging.Level;
import java.util.logging.Logger;

public class RamObjectCacheImpl implements RamObjectCache {
    private Logger log = Logger.getLogger(getClass().getName());

    private final List<BikeRide> bikeRideList = new ArrayList<BikeRide>();
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

    private String currentBikeRideId = null;
    @Override
    public BikeRide getCurrentBikeRide() {
        if(currentBikeRideId == null) {
            return null;
        }

        log.log(Level.ALL, "BikeRide list size" + bikeRideList.size() + ", " +
                           "hunting for "+currentBikeRideId);
        for(BikeRide bikeRide: bikeRideList) {
            String bikeRideId = bikeRide.getId();
            if(bikeRideId == currentBikeRideId) {
                return bikeRide;
            }
        }
        return null;
    }

    @Override
    public void setCurrentBikeRide(BikeRide newBikeRide) {
        if(newBikeRide==null || newBikeRide.getId()==null) {
            return;
        }

        currentBikeRideId = newBikeRide.getId();

        updateRide(newBikeRide);
    }

    @Override
    public void updateRide(BikeRide newBikeRide) {
        List<BikeRide> newBikeRideList = new ArrayList<BikeRide>();
        newBikeRideList.add(newBikeRide);

        for(BikeRide bikeRide: bikeRideList) {
            String bikeRideId = bikeRide.getId();
            if(bikeRideId!=bikeRide.getId()) {
                newBikeRideList.add(bikeRide);
            }
        }

        bikeRideList.clear();
        bikeRideList.addAll(newBikeRideList);
    }

    @Override
    public void deleteRide(String rideIdToDelete) {
        if(rideIdToDelete == null) {
            return;
        }

        List<BikeRide> newBikeRideList = new ArrayList<BikeRide>();

        for(BikeRide bikeRide: bikeRideList) {
            if(rideIdToDelete!=bikeRide.getId()) {
                newBikeRideList.add(bikeRide);
            }
        }

        bikeRideList.clear();
        bikeRideList.addAll(newBikeRideList);
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
