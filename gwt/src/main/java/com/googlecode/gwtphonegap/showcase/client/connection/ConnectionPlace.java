package com.googlecode.gwtphonegap.showcase.client.connection;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ConnectionPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<ConnectionPlace> {

		@Override
		public ConnectionPlace getPlace(String token) {
			return new ConnectionPlace();
		}

		@Override
		public String getToken(ConnectionPlace place) {
			return null;
		}

	}
}