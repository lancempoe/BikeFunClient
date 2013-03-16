package com.googlecode.gwtphonegap.showcase.client.compass;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CompassPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<CompassPlace> {

		@Override
		public CompassPlace getPlace(String token) {
			return new CompassPlace();
		}

		@Override
		public String getToken(CompassPlace place) {
			return null;
		}

	}
}
