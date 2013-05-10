package com.bikefunfinder.client.client.places.gmap;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMapPlace  extends Place {
    public static final class Tokenizer implements PlaceTokenizer<GMapPlace> {
        @Override
        public GMapPlace getPlace(String token) {
            return new GMapPlace(null);
        }

        @Override
        public String getToken(GMapPlace place) {
            return null;
        }
    }

    private final String pageName;

    public GMapPlace(final String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}
