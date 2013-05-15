package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.Injector;
import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.bootstrap.db.DBKeys;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.BikeRideStorage;
import com.bikefunfinder.client.shared.model.User;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.Window;

public class EventScreenPlace extends Place {
    final BikeRide bikeRide;

    public EventScreenPlace(BikeRide bikeRide) {
        this.bikeRide = bikeRide;
    }

    public EventScreenPlace() {
        this.bikeRide = null;
    }

    public BikeRide getBikeRide() {
        return bikeRide;
    }

    public static class Tokenizer implements PlaceTokenizer<EventScreenPlace> {

        @Override
        public EventScreenPlace getPlace(String token) {

            ClientFactory clientFactory = Injector.INSTANCE.getSimpleWidget();
            String bikeRideStorageJson = clientFactory.getStoredValue(DBKeys.BIKE_RIDE_STORAGE);

            if(bikeRideStorageJson!=null && !bikeRideStorageJson.isEmpty()) {
                BikeRide bikeRideStorage = Utils.castJsonTxtToJSOObject(bikeRideStorageJson);

//                final JsArray<BikeRide> bikeRideJsArray = bikeRideStorage.getBikeRides();
//                for(int index = 0; index < bikeRideJsArray.length(); index++) {
//                    BikeRide br = bikeRideJsArray.get(index);
                    if(bikeRideStorage.getId()==token) {
                        return new EventScreenPlace(bikeRideStorage);
                    }
//                }
            }
            BikeRide blankBikeRide = GWT.create(BikeRide.class);
            return new EventScreenPlace(blankBikeRide);
        }

        @Override
        public String getToken(EventScreenPlace place) {
            if(null != place && null != place.getBikeRide()) {
                return place.getBikeRide().getId();
            }

            return null;
        }
    }
}