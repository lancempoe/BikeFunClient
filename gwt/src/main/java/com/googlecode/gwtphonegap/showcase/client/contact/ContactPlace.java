package com.googlecode.gwtphonegap.showcase.client.contact;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ContactPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<ContactPlace> {

		@Override
		public ContactPlace getPlace(String token) {
			return new ContactPlace();
		}

		@Override
		public String getToken(ContactPlace place) {
			return null;
		}

	}
}