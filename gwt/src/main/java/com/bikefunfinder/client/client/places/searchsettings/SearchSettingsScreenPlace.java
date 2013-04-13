package com.bikefunfinder.client.client.places.searchsettings;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:31 AM
 */

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SearchSettingsScreenPlace extends Place {
    public static class Tokenizer implements PlaceTokenizer<SearchSettingsScreenPlace> {

        @Override
        public SearchSettingsScreenPlace getPlace(String token) {
            return new SearchSettingsScreenPlace();
        }

        @Override
        public String getToken(SearchSettingsScreenPlace place) {
            return null;
        }
    }
}