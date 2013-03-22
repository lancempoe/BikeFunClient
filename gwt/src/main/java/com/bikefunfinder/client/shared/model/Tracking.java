package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 7:58 PM
 */

import com.google.gwt.core.client.JavaScriptObject;

public class Tracking extends JavaScriptObject {

    protected Tracking() {}

    public final native String getId() /*-{
        return this.id;
    }-*/;

    public final native String getBikeRideId() /*-{
        return this.bikeRideId;
    }-*/;

    public final native GeoLoc getGeoLoc() /*-{
        return this.geoLoc;
    }-*/;

    public final native String getTrackingTime() /*-{
        return this.trackingTime;
    }-*/;

    public final native String getTrackingUserId() /*-{
        return this.trackingUserId;
    }-*/;

    public final native String getTrackingUserName() /*-{
        return this.trackingUserName;
    }-*/;

}
