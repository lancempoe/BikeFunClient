package com.bikefunfinder.client.client.places.loginscreen;
/*
 * @author: tneuwerth
 * @created 4/4/13 10:56 AM
 */

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoginScreenPlace extends Place {
    public static class Tokenizer implements PlaceTokenizer<LoginScreenPlace> {
        //test commit delete me
        @Override
        public LoginScreenPlace getPlace(String token) {
            return new LoginScreenPlace();
        }

        @Override
        public String getToken(LoginScreenPlace place) {
            return null;
        }
    }
}