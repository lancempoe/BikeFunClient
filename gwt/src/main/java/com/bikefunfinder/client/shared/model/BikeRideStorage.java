package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 5/14/13 7:20 PM
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class BikeRideStorage extends JavaScriptObject {

    protected BikeRideStorage() {}

    public final native JsArray<BikeRide> getBikeRides() /*-{
    	return this.bikeRides;
    }-*/;

    public final native void setBikeRides(JsArray<BikeRide> bikeRides) /*-{
        this.bikeRides = bikeRides;
    }-*/;
}
