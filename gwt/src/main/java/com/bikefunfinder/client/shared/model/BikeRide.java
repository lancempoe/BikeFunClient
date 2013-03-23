package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 7:54 PM
 */

import com.google.gwt.core.client.JavaScriptObject;

public class BikeRide extends JavaScriptObject {

    protected BikeRide() {}


    public final native String getId() /*-{
		return this.id;
    }-*/;


    public final native String getBikeRideName() /*-{
        return this.bikeRideName;
    }-*/;

    public final native String getDetails() /*-{
        return this.details;
    }-*/;

    public final native String getRideLeaderId() /*-{
        return this.rideLeaderId;
    }-*/;

    public final native String getTargetAudience() /*-{
        return this.targetAudience;
    }-*/;

    public final native String getRideStartTime() /*-{
        return this.rideStartTime;
    }-*/;

    public final native Location getLocation() /*-{
        return this.location;
    }-*/;

    public final native String getCityLocationId() /*-{
        return this.cityLocationId;
    }-*/;

    public final native String getImagePath() /*-{
        return this.imagePath;
    }-*/;

    public final native String isTrackingAllowed() /*-{
        return this.trackingAllowed;
    }-*/;

    public final native String getRideLeaderName() /*-{
        return this.rideLeaderName;
    }-*/;

    public final native String getDistanceFromClient() /*-{
        return this.distanceFromClient;
    }-*/;

    public final native String isCurrentlyTracking() /*-{
        return this.currentlyTracking;
    }-*/;

    public final native String getTotalPeopleTrackingCount() /*-{
        return this.totalPeopleTrackingCount;
    }-*/;


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
//
//    //Generated and send back.  not in DB
//    public String rideLeaderName;
//    public Double distanceFromClient;
//    public boolean currentlyTracking = false; //Default value
//    public long totalPeopleTrackingCount = 0; //Default value
//
//    //Generated and sent back to ride page.  Not saved in same collection
//    public Tracking rideLeaderTracking;
//    public List<Tracking> currentTrackings = new ArrayList<Tracking>();
}
