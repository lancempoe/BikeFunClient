package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 7:57 PM
 */

import com.google.gwt.core.client.JavaScriptObject;

public class GeoLoc extends JavaScriptObject {
    protected GeoLoc() {}

    public final native double getLongitude() /*-{
        return this.longitude;
    }-*/;
    public final native void setLongitude(double longitude) /*-{
        this.longitude = longitude;
    }-*/;

    public final native double getLatitude() /*-{
        return this.latitude;
    }-*/;

    public final native void setLatitude(double latitude) /*-{
        this.latitude = latitude;
    }-*/;


}
