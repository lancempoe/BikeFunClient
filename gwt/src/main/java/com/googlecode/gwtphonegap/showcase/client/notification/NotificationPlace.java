package com.googlecode.gwtphonegap.showcase.client.notification;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NotificationPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<NotificationPlace> {

		@Override
		public NotificationPlace getPlace(String token) {
			return new NotificationPlace();
		}

		@Override
		public String getToken(NotificationPlace place) {
			return null;
		}

	}
}