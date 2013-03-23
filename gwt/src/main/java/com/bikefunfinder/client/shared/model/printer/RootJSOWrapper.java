package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/22/13 11:25 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.core.client.JsArray;

public class RootJSOWrapper implements DescribeableAsString<Root> {
    @Override
    public final String describeAsString(Root jsoObject) {
        StringBuilder rootDescriptionSB = new StringBuilder();

        rootDescriptionSB.append("Root(");
            rootDescriptionSB.append("BikeRides[");
                rootDescriptionSB.append(
                    decomposeBikeRidesIntoJsonString(jsoObject.getBikeRides())
                );
            rootDescriptionSB.append("],");
            rootDescriptionSB.append("ClosestLocation(");
                rootDescriptionSB.append(JSODescriber.describe(jsoObject.getClosestLocation()));
            rootDescriptionSB.append(")");
        rootDescriptionSB.append(")"); // end root description
        return  rootDescriptionSB.toString();
    }

    private String decomposeBikeRidesIntoJsonString(JsArray<BikeRide> bikeRides) {
        final StringBuilder arraySB = new StringBuilder();
        final int numBikeRides = bikeRides.length();
        for(int index=0; index< numBikeRides; index++) {
            BikeRide ride = bikeRides.get(index);

            arraySB.append(JSODescriber.describe(ride));
            arraySB.append(")");
            if( index< (numBikeRides-1)) {
                arraySB.append(",");
            }
        }
        return arraySB.toString();
    }
}


