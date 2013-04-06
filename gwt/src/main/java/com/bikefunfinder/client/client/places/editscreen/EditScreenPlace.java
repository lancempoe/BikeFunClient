package com.bikefunfinder.client.client.places.editscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class EditScreenPlace extends Place {
    public static class Tokenizer implements PlaceTokenizer<EditScreenPlace> {
        //test commit delete me
        @Override
        public EditScreenPlace getPlace(String token) {
            return new EditScreenPlace();
        }

        @Override
        public String getToken(EditScreenPlace place) {
            return null;
        }
    }
}
