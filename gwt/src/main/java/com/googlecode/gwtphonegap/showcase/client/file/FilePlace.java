package com.googlecode.gwtphonegap.showcase.client.file;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class FilePlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<FilePlace> {

		@Override
		public FilePlace getPlace(String token) {
			return new FilePlace();
		}

		@Override
		public String getToken(FilePlace place) {
			return null;
		}

	}
}