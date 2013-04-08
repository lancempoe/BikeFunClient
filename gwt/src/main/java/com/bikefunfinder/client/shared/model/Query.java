package com.bikefunfinder.client.shared.model;
/*
 * @author: lancepoehler
 * @created 4/06/13 5:54 PM
 */

import com.google.gwt.core.client.JavaScriptObject;

public class Query extends JavaScriptObject {

    protected Query() {}


    public final native String getQuery() /*-{
		return this.query;
    }-*/;

    public final native String getCityLocationId() /*-{
		return this.cityLocationId;
    }-*/;

    public final native Long getDate() /*-{
		return this.date;
    }-*/;

    public final native String getTargetAudience() /*-{
		return this.targetAudience;
    }-*/;

    //    public String query;
    //    public String cityLocationId; //If not provided then use current location
    //    public Long date;
    //    public String targetAudience;
}
