package com.bikefunfinder.client.client.places.gmaphomescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMapHomePlace extends Place {

    public static final class Tokenizer implements PlaceTokenizer<GMapHomePlace> {
        @Override
        public GMapHomePlace getPlace(String token) {
            return new GMapHomePlace();
        }

        @Override
        public String getToken(GMapHomePlace place) {
            return null;
        }
    }
}
