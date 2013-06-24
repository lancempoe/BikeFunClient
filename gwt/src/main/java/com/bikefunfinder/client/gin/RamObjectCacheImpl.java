package com.bikefunfinder.client.gin;
/*
 * @author: tneuwerth
 * @created 5/15/13 6:14 PM
 */

import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RamObjectCacheImpl implements RamObjectCache {
    private Logger log = Logger.getLogger(getClass().getName());

    private final List<BikeRide> hereAndNowBikeRideList = new ArrayList<BikeRide>();
    private final List<BikeRide> timeOfDayBikeRideList = new ArrayList<BikeRide>();

    private GeoLoc currentPhoneGeoLoc;
    private String currentBikeRideId = null;

    private Root lastTimeOfDay = null;
    private Root lastTimeOfDayForProfile = null;
    private Root lastSearchByProximity = null;

    @Override
    public void setHereAndNowBikeRideCache(List<BikeRide> bikeRides) {
        hereAndNowBikeRideList.clear();
        hereAndNowBikeRideList.addAll(bikeRides);
    }

    @Override
    public void setTimeOfDayBikeRideCache(List<BikeRide> bikeRides) {
        timeOfDayBikeRideList.clear();
        timeOfDayBikeRideList.addAll(bikeRides);
    }

    @Override
    public List<BikeRide> getHereAndNowBikeRideCache() {
        //We could save some ram by simply using root.  This may be faster though.
        return hereAndNowBikeRideList;
    }

    @Override
    public List<BikeRide> getTimeOfDayBikeRideCache() {
        //We could save some ram by simply using root.  This may be faster though.
        return timeOfDayBikeRideList;
    }

    /**
     * This will be used so that a user can go to the ride page from the here and now page.
     * @return
     */
    @Override
    public BikeRide getCurrentBikeRide() {
        if(currentBikeRideId == null) {
            return null;
        }

        log.log(Level.ALL, "BikeRide list size" + timeOfDayBikeRideList.size() + ", " +
                           "hunting for "+currentBikeRideId);
        for(BikeRide bikeRide: timeOfDayBikeRideList) {
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

        for(BikeRide bikeRide: timeOfDayBikeRideList) {
            String bikeRideId = bikeRide.getId();
            if(bikeRideId!=bikeRide.getId()) {
                newBikeRideList.add(bikeRide);
            }
        }

        timeOfDayBikeRideList.clear();
        timeOfDayBikeRideList.addAll(newBikeRideList);
    }

    @Override
    public void deleteRide(String rideIdToDelete) {
        if(rideIdToDelete == null) {
            return;
        }

        List<BikeRide> newBikeRideList = new ArrayList<BikeRide>();

        for(BikeRide bikeRide: timeOfDayBikeRideList) {
            if(rideIdToDelete!=bikeRide.getId()) {
                newBikeRideList.add(bikeRide);
            }
        }

        timeOfDayBikeRideList.clear();
        timeOfDayBikeRideList.addAll(newBikeRideList);
    }

    @Override
    public GeoLoc getCurrentPhoneGeoLoc() {
        return currentPhoneGeoLoc;
    }

    @Override
    public void setCurrentPhoneGeoLoc(GeoLoc phoneGeoLoc) {
        this.currentPhoneGeoLoc = phoneGeoLoc;
    }

    @Override
    public Root getSearchByTimeOfDay() {
        return lastTimeOfDay;
    }

    @Override
    public void setSearchByTimeOfDay(Root root) {
        lastTimeOfDay = root;
    }

    @Override
    public Root getSearchByTimeOfDayForProfile() {
        return lastTimeOfDayForProfile;
    }

    @Override
    public void setSearchByTimeOfDayForProfile(Root root) {
        lastTimeOfDayForProfile = root;
    }

    @Override
    public Root getSearchByProximity() {
        return lastSearchByProximity;
    }

    @Override
    public void setSearchByProximity(Root root) {
        lastSearchByProximity = root;
    }


    private BikeRide eventRequest;
    @Override
    public BikeRide getEventRequest() {
        return eventRequest;
    }

    @Override
    public void setEventRequest(BikeRide bikeRide) {
        this.eventRequest = bikeRide;
    }

    private AnonymousUser anonymousUser;
    @Override
    public AnonymousUser getAnonymousUser() {
        return anonymousUser;
    }

    @Override
    public void setAnonymousUser(AnonymousUser anonymousUser) {
        this.anonymousUser = anonymousUser;
    }
}
