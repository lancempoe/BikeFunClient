package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/22/13 10:51 PM
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class ClosestLocation extends JavaScriptObject {

    protected ClosestLocation() {}

    public final native String getId() /*-{
		return this.id;
    }-*/;

    public final native String getCity() /*-{
		return this.city;
    }-*/;

    public final native String getState() /*-{
		return this.state;
    }-*/;

    public final native GeoLoc getGeoLoc() /*-{
		return this.geoLoc;
    }-*/;

    public final native String getLongitude() /*-{
		return this.longitude;
    }-*/;

    public final native String getLatitude() /*-{
		return this.latitude;
    }-*/;

    public final native String getFormattedAddress() /*-{
		return this.formattedAddress;
    }-*/;

    public final String getJSON()
    {
        return new JSONObject(this).toString();
    }
}