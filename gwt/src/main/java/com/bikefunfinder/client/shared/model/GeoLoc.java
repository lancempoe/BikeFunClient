package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 7:57 PM
 */

import com.google.gwt.core.client.JavaScriptObject;

public class GeoLoc extends JavaScriptObject {
    protected GeoLoc() {}

    public final native String getLongitude() /*-{
        return this.longitude;
    }-*/;

    public final native String getLatitude() /*-{
        return this.latitude;
    }-*/;
}
