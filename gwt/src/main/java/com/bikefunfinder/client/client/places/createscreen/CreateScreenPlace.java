package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CreateScreenPlace extends Place {

    private final BikeRide bikeRide;

    public CreateScreenPlace() {
        bikeRide = null;
    }

    public CreateScreenPlace(BikeRide bikeRide) {
        this.bikeRide = bikeRide;
    }

    public BikeRide getBikeRide() {
        return bikeRide;
    }

    public static class Tokenizer implements PlaceTokenizer<CreateScreenPlace> {

        @Override
        public CreateScreenPlace getPlace(String token) {
            return new CreateScreenPlace();
        }

        @Override
        public String getToken(CreateScreenPlace place) {
            return null;
        }
    }
}