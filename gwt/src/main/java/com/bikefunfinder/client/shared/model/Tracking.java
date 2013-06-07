package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 7:58 PM
 */

import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
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

    public final native double getTrackingTime() /*-{
        return this.trackingTime;
    }-*/;

    public final JsDateWrapper createJsDateWrapperTrackingStartTime() {
        return new JsDateWrapper(getTrackingTime());
    }

    public final native String getTrackingUserId() /*-{
        return this.trackingUserId;
    }-*/;

    public final native String getTrackingUserName() /*-{
        return this.trackingUserName;
    }-*/;

    ///Setting details.

    public final native void setBikeRideId(String bikeRideId) /*-{
        this.bikeRideId = bikeRideId;
    }-*/;

    public final void setTrackingTime(Long time) {
        setTrackingTime(time.toString());
    }

    public final native void setTrackingTime(String time) /*-{
        this.trackingTime = time;
    }-*/;

    public final native void setGeoLoc(GeoLoc geoLoc) /*-{
        this.geoLoc = geoLoc;
    }-*/;

    public final native void setTrackingUserId(String trackingUserId) /*-{
        this.trackingUserId = trackingUserId;
    }-*/;

    public final native void setTrackingUserName(String trackingUserName) /*-{
        this.trackingUserName = trackingUserName;
    }-*/;




}
