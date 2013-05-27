package com.bikefunfinder.client.gin;
/*
 * @author: tneuwerth
 * @created 5/15/13 6:08 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;

import java.util.List;

public interface RamObjectCache {
    public List<BikeRide> getHereAndNowBikeRideCache();
    public void setHereAndNowBikeRideCache(List<BikeRide> bikeRides);

    public BikeRide getCurrentBikeRide();
    public void setCurrentBikeRide(BikeRide bikeRide);

    public void updateRide(BikeRide bikeRide);
    public void deleteRide(String bikeRideId);

    public GeoLoc getCurrentPhoneGeoLoc();
    public void setCurrentPhoneGeoLoc(GeoLoc phoneGeoLoc);
}
