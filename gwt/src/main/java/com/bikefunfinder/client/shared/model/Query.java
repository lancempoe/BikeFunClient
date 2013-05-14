package com.bikefunfinder.client.shared.model;
/*
 * @author: lancepoehler
 * @created 4/06/13 5:54 PM
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class Query extends JavaScriptObject {

    protected Query() {}


    public final native String getQuery() /*-{
		return this.query;
    }-*/;

    public final native String getCity() /*-{
		return this.city;
    }-*/;

    public final native Long getDate() /*-{
		return this.date;
    }-*/;

    public final native String getTargetAudience() /*-{
		return this.targetAudience;
    }-*/;

    public final native String getRideLeaderId() /*-{
		return this.rideLeaderId;
    }-*/;

    public final native void setRideLeaderId(String rideLeaderId) /*-{
		this.rideLeaderId = rideLeaderId;
    }-*/;

    public final native void setQuery(String query) /*-{
        this.query = query;
    }-*/;

    public final native void setTargetAudience(String targetAudience) /*-{
        this.targetAudience = targetAudience;
    }-*/;

    public final native void setCity(String city) /*-{
        this.city = city;
    }-*/;

    //    public String query;
    //    public String city; //If not provided then use current location
    //    public Long date;
    //    public String targetAudience;
    //    public String rideLeaderId;
}
