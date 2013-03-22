package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 10:19 PM
 */

import com.google.gwt.core.client.JavaScriptObject;

import java.util.List;

public class Root extends JavaScriptObject {

    protected Root() {}

    public final native List<BikeRide> getBikeRides() /*-{ return this.BikeRides; }-*/;

}