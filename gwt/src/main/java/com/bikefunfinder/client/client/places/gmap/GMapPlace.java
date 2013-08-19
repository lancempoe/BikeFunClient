package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.shared.model.BikeRide;
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
    private final BikeRide bikeRide;
    private final String pageName;

    public static final class Tokenizer implements PlaceTokenizer<GMapPlace> {
        @Override
        public GMapPlace getPlace(final String token) {
            //THIS SHOULD NEVER HAPPEN.  talk to tim as to why.
            return null;
        }

        @Override
        public String getToken(final GMapPlace place) {
            //THIS IS CALLED WHEN GOING INTO THE EVENT PAGE.  NOT THE MAP.  ??
            final StringBuilder builder = new StringBuilder();
            builder.append(place.getPageName());
            return builder.toString();
        }
    }

    public GMapPlace(final String pageName, BikeRide bikeRide) {
        this.pageName = pageName;
        this.bikeRide = bikeRide;
    }

    public String getPageName() {
        return pageName;
    }

    public BikeRide getBikeRide() {
        return bikeRide;
    }
}
