package com.bikefunfinder.client.gin;
/*
 * @author: tneuwerth
 * @created 5/15/13 6:08 PM
 */

import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;

import java.util.List;

public interface RamObjectCache {

    public AnonymousUser getAnonymousUser();
    public void setAnonymousUser(AnonymousUser anonymousUser);

    public GeoLoc getCurrentPhoneGeoLoc();
    public void setCurrentPhoneGeoLoc(GeoLoc phoneGeoLoc);

    public BikeRide getCurrentBikeRide();
    public void setCurrentBikeRide(BikeRide bikeRide);

    public void updateRide(BikeRide bikeRide);
    public void deleteRide(String bikeRideId);

    ///////

    public Root getSearchByTimeOfDay();
    public void setSearchByTimeOfDay(Root root);

    public Root getSearchByTimeOfDayForProfile();
    public void setSearchByTimeOfDayForProfile(Root root);

    public Root getSearchByProximity();
    public void setSearchByProximity(Root root);

    public Root getSearchByQuery();
    public void setSearchByQuery(Root root);

    public BikeRide getEventRequest();
    public void setEventRequest(BikeRide bikeRide);
}
