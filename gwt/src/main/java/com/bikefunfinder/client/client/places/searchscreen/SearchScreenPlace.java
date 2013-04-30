package com.bikefunfinder.client.client.places.searchscreen;
/*
 * @author: lancepoehler
 * @created 4/25/13 11:31 pm
 */

import com.bikefunfinder.client.shared.model.Query;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SearchScreenPlace extends Place {

    Query query;

    public SearchScreenPlace(Query query) {
        this.query = query;
    }

    public SearchScreenPlace() {
    }

    public Query getQuery() {
        return query;
    }

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