package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfileScreenPlace extends Place {
    public static class Tokenizer implements PlaceTokenizer<ProfileScreenPlace> {

        @Override
        public ProfileScreenPlace getPlace(String token) {
            return new ProfileScreenPlace();
        }

        @Override
        public String getToken(ProfileScreenPlace place) {
            return null;
        }
    }
}
