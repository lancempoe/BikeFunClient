package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class EventScreenPlace extends Place {
    final BikeRide bikeRide;

    public EventScreenPlace(BikeRide bikeRide) {
        this.bikeRide = bikeRide;
    }

    public EventScreenPlace() {
        this.bikeRide = null;
    }

    public BikeRide getBikeRide() {
        return bikeRide;
    }

    public static class Tokenizer implements PlaceTokenizer<EventScreenPlace> {

        RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();

        @Override
        public EventScreenPlace getPlace(String token) {

            for(BikeRide bikeRide : ramObjectCache.getHereAndNowBikeRideCache()) {
                if(bikeRide.getId()==token) {
                    return new EventScreenPlace(bikeRide);
                }
            }

            // actually here, we should call the service for the ride by id son.
            BikeRide blankBikeRide = GWT.create(BikeRide.class);
            return new EventScreenPlace(blankBikeRide);
        }

        @Override
        public String getToken(EventScreenPlace place) {
            if(null != place && null != place.getBikeRide()) {
                return place.getBikeRide().getId();
            }

            return null;
        }
    }
}