package com.bikefunfinder.client.gin;
/*
 * @author: tneuwerth
 * @created 5/15/13 6:14 PM
 */

import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.helper.Extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RamObjectCacheImpl implements RamObjectCache {
    private Logger log = Logger.getLogger(getClass().getName());

    private GeoLoc currentPhoneGeoLoc;
    private String currentBikeRideId = null;
    private AnonymousUser anonymousUser;

    private Root lastTimeOfDay = null;
    private Root lastTimeOfDayForProfile = null;
    private Root lastSearchByProximity = null;
    private Root lastSearchByQuery = null;

    //

    /**
     * This will be used so that a user can go to the ride page from the here and now page.
     * @return
     */
    @Override
    public BikeRide getCurrentBikeRide() {
        if(currentBikeRideId == null) {
            return null;
        }

        List<BikeRide> bikeRides = Extractor.getBikeRidesFrom(lastTimeOfDay);
        for(BikeRide bikeRide: bikeRides) {
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
    public void updateRide(BikeRide updatedBikeRide) {
        //You really don't know if the new bike ride should
        //even be in the main list.  What if you create a different city?
        //For now I have commented out the code.  I don't believe it is doing what we hoped.
        //Simply refresh to get the good data just like fb or twitter.
    }

    @Override
    public void deleteRide(String rideIdToDelete) {
        //For now I have commented out the code.  I don't believe it is doing what we hoped.
        //Simply refresh to get the good data just like fb or twitter.
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
    public Root getSearchByQuery() {
        return lastSearchByQuery;
    }

    @Override
    public void setSearchByQuery(Root root) {
        lastSearchByQuery = root;
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

    @Override
    public AnonymousUser getAnonymousUser() {
        return anonymousUser;
    }

    @Override
    public void setAnonymousUser(AnonymousUser anonymousUser) {
        this.anonymousUser = anonymousUser;
    }
}
