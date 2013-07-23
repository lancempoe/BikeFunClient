package com.bikefunfinder.client.shared.model.helper;
/*
 * @author: tneuwerth
 * @created 4/26/13 8:21 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.core.client.JsArray;

import java.util.ArrayList;
import java.util.List;

public class Extractor {

    public static List<BikeRide> getBikeRidesFrom(Root root) {
        ArrayList<BikeRide> list = new ArrayList<BikeRide>();
        if (root != null) {
            JsArray<BikeRide> bikeRides = root.getBikeRides();
            final int numBikeRides = bikeRides.length();
            for(int index=0; index< numBikeRides; index++) {
                list.add(bikeRides.get(index));
            }
        }
        return list;
    }

}
