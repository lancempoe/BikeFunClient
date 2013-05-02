package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CreateScreenPlace extends Place {
    public static class Tokenizer implements PlaceTokenizer<CreateScreenPlace> {

        @Override
        public CreateScreenPlace getPlace(String token) {
            return new CreateScreenPlace();
        }

        @Override
        public String getToken(CreateScreenPlace place) {
            return null;
        }
    }
}