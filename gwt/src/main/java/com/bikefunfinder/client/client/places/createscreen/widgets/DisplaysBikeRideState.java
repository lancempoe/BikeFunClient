package com.bikefunfinder.client.client.places.createscreen.widgets;
/*
 * @author: tneuwerth
 * @created 5/24/13 10:16 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;

public interface DisplaysBikeRideState {
    public void setStateFrom(BikeRide bikeRide);
    public void resetState();
}
