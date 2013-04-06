package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class EventScreenPlace extends Place {
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