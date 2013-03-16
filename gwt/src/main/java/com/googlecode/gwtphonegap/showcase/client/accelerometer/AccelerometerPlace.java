package com.googlecode.gwtphonegap.showcase.client.accelerometer;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class AccelerometerPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<AccelerometerPlace> {

		@Override
		public AccelerometerPlace getPlace(String token) {
			return new AccelerometerPlace();
		}

		@Override
		public String getToken(AccelerometerPlace place) {
			return null;
		}

	}
}
