package com.googlecode.gwtphonegap.showcase.client.inappbrowser;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class InAppBrowserPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<InAppBrowserPlace> {

		@Override
		public InAppBrowserPlace getPlace(String token) {
			return new InAppBrowserPlace();
		}

		@Override
		public String getToken(InAppBrowserPlace place) {
			return null;
		}

	}
}