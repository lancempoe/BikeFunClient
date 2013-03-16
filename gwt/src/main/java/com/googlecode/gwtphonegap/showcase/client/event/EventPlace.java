package com.googlecode.gwtphonegap.showcase.client.event;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class EventPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<EventPlace> {

		@Override
		public EventPlace getPlace(String token) {
			return new EventPlace();
		}

		@Override
		public String getToken(EventPlace place) {
			return null;
		}

	}
}