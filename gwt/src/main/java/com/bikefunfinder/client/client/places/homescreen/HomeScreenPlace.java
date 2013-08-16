package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class HomeScreenPlace extends Place {

    public static final class Tokenizer implements PlaceTokenizer<HomeScreenPlace> {
        @Override
        public HomeScreenPlace getPlace(String token) {
            return new HomeScreenPlace();
        }

        @Override
        public String getToken(HomeScreenPlace place) {
            return null;
        }
    }
}