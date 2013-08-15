package com.bikefunfinder.client.gin;
/*
 * @author: tneuwerth
 * @created 5/15/13 6:08 PM
 */

import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;

public interface RamObjectCache {

    public AnonymousUser getAnonymousUser();
    public void setAnonymousUser(AnonymousUser anonymousUser);

    public GeoLoc getCurrentPhoneGeoLoc();
    public void setCurrentPhoneGeoLoc(GeoLoc phoneGeoLoc);

    public Root getRoot();
    public void setRoot(Root root);

    public int getMainScreenSize();
    public void setMainScreenSize(int size);

    public BikeRide getEventRequest();
    public void setEventRequest(BikeRide bikeRide);

    public BikeRide getCurrentBikeRide();
    public void setCurrentBikeRide(BikeRide bikeRide);

}
