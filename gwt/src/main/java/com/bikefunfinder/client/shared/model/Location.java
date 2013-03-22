package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 7:55 PM
 */

import com.google.gwt.core.client.JavaScriptObject;

public class Location extends JavaScriptObject {
    protected Location() {}

    public final native String getId() /*-{
        return this.id;
    }-*/;

    public final native  String getStreetAddress() /*-{
        return this.streetAddress;
    }-*/;

    public final native  String getCity() /*-{
        return this.city;
    }-*/;

    public final native  String getState() /*-{
        return this.state;
    }-*/;

    public final native  String getZip() /*-{
        return this.zip;
    }-*/;

    public final native  String getCountry() /*-{
        return this.country;
    }-*/;

    public final native  GeoLoc getGeoLoc() /*-{
        return this.geoLoc;
    }-*/;

    public final native  String getFormattedAddress() /*-{
        return this.formattedAddress;
    }-*/;
}