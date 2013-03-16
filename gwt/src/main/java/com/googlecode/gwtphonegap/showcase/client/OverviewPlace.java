package com.googlecode.gwtphonegap.showcase.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class OverviewPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<OverviewPlace> {

		@Override
		public OverviewPlace getPlace(String token) {
			return new OverviewPlace();
		}

		@Override
		public String getToken(OverviewPlace place) {
			return null;
		}

	}
}
