package com.bikefunfinder.client.client.places.homescreen;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class HomeScreenPlace extends Place {
    public static class Tokenizer implements PlaceTokenizer<HomeScreenPlace> {
         //test commit delete me
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
