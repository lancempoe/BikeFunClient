package com.bikefunfinder.client.client.places.usereventsscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class UserEventsScreenPlace extends Place {
    public static class Tokenizer implements PlaceTokenizer<UserEventsScreenPlace> {
        //test commit delete me
        @Override
        public UserEventsScreenPlace getPlace(String token) {
            return new UserEventsScreenPlace();
        }

        @Override
        public String getToken(UserEventsScreenPlace place) {
            return null;
        }
    }
}
