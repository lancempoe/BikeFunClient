package com.bikefunfinder.client.client.places.searchscreen;
/*
 * @author: lancepoehler
 * @created 4/25/13 11:31 pm
 */

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SearchScreenPlace extends Place {
    public static class Tokenizer implements PlaceTokenizer<SearchScreenPlace> {

        @Override
        public SearchScreenPlace getPlace(String token) {
            return new SearchScreenPlace();
        }

        @Override
        public String getToken(SearchScreenPlace place) {
            return null;
        }
    }
}