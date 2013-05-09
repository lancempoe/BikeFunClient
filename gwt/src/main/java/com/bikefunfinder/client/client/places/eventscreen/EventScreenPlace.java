package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.Window;

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



        //test commit delete me
        @Override
        public EventScreenPlace getPlace(String token) {
            return new EventScreenPlace();
        }

        @Override
        public String getToken(EventScreenPlace place) {
            return null;
        }
    }
}