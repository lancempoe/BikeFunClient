package com.googlecode.gwtphonegap.showcase.client.gmap;

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
        public GMapPlace getPlace(final String token) {
            final String[] strings = token.split(",");
            return new GMapPlace(strings[0]);
        }

        @Override
        public String getToken(final GMapPlace place) {
            final StringBuilder builder = new StringBuilder();
            builder.append(place.getDeviceId());
            return builder.toString();
        }
    }

    private final String deviceId;

    public GMapPlace(final String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
