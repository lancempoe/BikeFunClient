package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 10:19 PM
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Root extends JavaScriptObject {

    protected Root() {}

    public final native JsArray<BikeRide> getBikeRides() /*-{ return this.BikeRides; }-*/;
    public final native ClosestLocation getClosestLocation() /*-{ return this.ClosestLocation; }-*/;

}