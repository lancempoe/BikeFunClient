package com.googlecode.gwtphonegap.showcase.client.device;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DevicePlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<DevicePlace> {

		@Override
		public DevicePlace getPlace(String token) {
			return new DevicePlace();
		}

		@Override
		public String getToken(DevicePlace place) {
			return null;
		}

	}
}