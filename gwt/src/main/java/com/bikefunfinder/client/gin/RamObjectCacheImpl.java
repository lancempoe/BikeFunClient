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

import java.util.List;
import java.util.logging.Logger;

public class RamObjectCacheImpl implements RamObjectCache {
    private Logger log = Logger.getLogger(getClass().getName());

    private AnonymousUser anonymousUser;
    private GeoLoc currentPhoneGeoLoc;
    private String currentBikeRideId = null;
    private BikeRide eventRequest;
    private Root root = null;
    private int mainScreenSize = 0;

    /**
     * This will be used so that a user can go to the ride page from the here and now page.
     * @return
     */
    @Override
    public BikeRide getCurrentBikeRide() {
        if(currentBikeRideId == null) {
            return null;
        }

        List<BikeRide> bikeRides = Extractor.getBikeRidesFrom(root);
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
    public Root getRoot() {
        return root;
    }

    @Override
    public void setRoot(Root root) {
        this.root = root;
    }

    @Override
    public int getMainScreenSize() {
        return mainScreenSize;
    }

    @Override
    public void setMainScreenSize(int mainScreenSize) {
        this.mainScreenSize = mainScreenSize;
    }

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
