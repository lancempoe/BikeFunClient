package com.googlecode.gwtphonegap.showcase.client.geolocation;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GeolocationPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<GeolocationPlace> {

		@Override
		public GeolocationPlace getPlace(String token) {
			return new GeolocationPlace();
		}

		@Override
		public String getToken(GeolocationPlace place) {
			return null;
		}

	}
}