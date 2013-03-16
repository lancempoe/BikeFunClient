package com.googlecode.gwtphonegap.showcase.client.camera;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CameraPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<CameraPlace> {

		@Override
		public CameraPlace getPlace(String token) {
			return new CameraPlace();
		}

		@Override
		public String getToken(CameraPlace place) {
			return null;
		}

	}
}
