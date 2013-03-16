package com.googlecode.gwtphonegap.showcase.client.media;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MediaPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<MediaPlace> {

		@Override
		public MediaPlace getPlace(String token) {
			return new MediaPlace();
		}

		@Override
		public String getToken(MediaPlace place) {
			return null;
		}

	}
}