package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 7:54 PM
 */

import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;


public class BikeRide extends JavaScriptObject {

    protected BikeRide() {}

    public final native String setId(String id) /*-{
		this.id = id;
    }-*/;

    public final native String getId() /*-{
		return this.id;
    }-*/;

    public final native String getBikeRideName() /*-{
        return this.bikeRideName;
    }-*/;

    public final native void setBikeRideName(String bikeRideName) /*-{
        this.bikeRideName = bikeRideName;
    }-*/;

    public final native String getDetails() /*-{
        return this.details;
    }-*/;

    public final native void setDetails(String details) /*-{
        this.details = details;
    }-*/;

    public final native String getRideLeaderId() /*-{
        return this.rideLeaderId;
    }-*/;

    public final native void setRideLeaderId(String id) /*-{
        this.rideLeaderId = id;
    }-*/;

    public final native String getTargetAudience() /*-{
        return this.targetAudience;
    }-*/;

    public final native void setTargetAudience(String targetAudience) /*-{
        this.targetAudience = targetAudience;
    }-*/;

    public final native double getRideStartTime() /*-{
        return this.rideStartTime;
    }-*/;

    public final JsDateWrapper createJsDateWrapperRideStartTime() {
        return new JsDateWrapper(getRideStartTime());
    }

    public final void setRideStartTime(Long time) {
       setRideStartTime(time.toString());
    }

    public final native void copyRideStartTime(BikeRide bikeRide) /*-{
        this.rideStartTime = bikeRide.rideStartTime;
    }-*/;

    public final native void setRideStartTime(String time) /*-{
        this.rideStartTime = time;
    }-*/;

    public final native Location getLocation() /*-{
        return this.location;
    }-*/;

    public final native void setLocation(Location location) /*-{
        this.location = location;
    }-*/;

    public final native String getCityLocationId() /*-{
        return this.cityLocationId;
    }-*/;

    public final native void setCityLocationId(String cityLocationId) /*-{
        this.cityLocationId = cityLocationId;
    }-*/;

    public final native String getImagePath() /*-{
        return this.imagePath;
    }-*/;

    public final native String setImagePath(String imagePath) /*-{
        this.imagePath = imagePath;
    }-*/;
    public final native boolean isTrackingAllowed() /*-{
        return this.trackingAllowed;
    }-*/;

    public final native void setTrackingAllowed(boolean isTrackingAllowed) /*-{
        this.trackingAllowed = isTrackingAllowed;
    }-*/;

    public final native String getRideLeaderName() /*-{
        return this.rideLeaderName;
    }-*/;

    public final native String setRideLeaderName(String rideLeaderName) /*-{
        this.rideLeaderName = rideLeaderName;
    }-*/;

    public final native String getDistanceFromClient() /*-{
        return this.distanceFromClient;
    }-*/;

    public final native void setDistanceFromClient(String distanceFromClient) /*-{
        this.distanceFromClient = distanceFromClient;
    }-*/;

    public final native String getDistanceTrackFromClient() /*-{
        return this.distanceTrackFromClient;
    }-*/;

    public final native void setDistanceTrackFromClient(String distanceTrackFromClient) /*-{
        this.distanceTrackFromClient = distanceTrackFromClient;
    }-*/;

    public final native boolean isCurrentlyTracking() /*-{
        return this.currentlyTracking;
    }-*/;

    public final native int getTotalPeopleTrackingCount() /*-{
        return this.totalPeopleTrackingCount;
    }-*/;

    public final native Tracking getRideLeaderTracking() /*-{
        return this.rideLeaderTracking;
    }-*/;

    public final native JsArray<Tracking> getCurrentTrackings() /*-{
    	return this.currentTrackings;
    }-*/;
    
 	public final String getJSON() {
        return new JSONObject(this).toString();
    }

    //    public String id;
//    public String bikeRideName;
//    public String details;
//    public String rideLeaderId;
//    public String targetAudience;
//    public Long rideStartTime;
//    public Location location;
//    public String cityLocationId;
//    public String imagePath = "Images/BikeRides/defaultBikeRide.jpg"; //In the event that no image is provided.
//    public boolean trackingAllowed = true; //Default tracking is turned on.
//    public String rideLeaderName;
//
//    //Generated and send back.  not in DB
//    public Double distanceFromClient;
//    public boolean currentlyTracking = false; //Default value
//    public long totalPeopleTrackingCount = 0; //Default value
//    public Double distanceTrackFromClient;
//
//    //Generated and sent back to ride page.  Not saved in same collection
//    public Tracking rideLeaderTracking;
//    public List<Tracking> currentTrackings = new ArrayList<Tracking>();
}
