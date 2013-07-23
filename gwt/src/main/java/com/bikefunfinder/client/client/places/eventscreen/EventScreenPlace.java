package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import java.util.List;

public class EventScreenPlace extends Place {
    final BikeRide bikeRide;
    final boolean wasConstructedById;

    public EventScreenPlace(BikeRide bikeRide) {
        this(bikeRide, false);
    }

    private EventScreenPlace(BikeRide bikeRide, boolean wasConstructedById) {
        this.wasConstructedById = wasConstructedById;
        this.bikeRide = bikeRide;
    }

    public EventScreenPlace() {
        this(null, false);
    }

    public BikeRide getBikeRide() {
        return bikeRide;
    }

    public boolean getWasConstructedById() {
        return wasConstructedById;
    }

    public static class Tokenizer implements PlaceTokenizer<EventScreenPlace> {

        RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();

        @Override
        public EventScreenPlace getPlace(String token) {

            List<BikeRide> bikeRides = Extractor.getBikeRidesFrom(ramObjectCache.getSearchByTimeOfDay());
            for(BikeRide bikeRide : bikeRides) {
                if(bikeRide.getId()==token) {
                    return new EventScreenPlace(bikeRide, true);
                }
            }

            return null;
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