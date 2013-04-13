package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 7:54 PM
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;


public class BikeRide extends JavaScriptObject {

    protected BikeRide() {}


    public final native String getId() /*-{
		return this.id;
    }-*/;


    public final native String getBikeRideName() /*-{
        return this.bikeRideName;
    }-*/;

    public final native void setBikeRideName(String name) /*-{
        this.bikeRideName = name;
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

    public final native String getRideStartTime() /*-{
        return this.rideStartTime;
    }-*/;

    public final void setRideStartTime(Long time) {
       setRideStartTime(time.toString());
    }

    public final native void setRideStartTime(String time) /*-{
        this.rideStartTime = time;
    }-*/;

    public final String getRideStartTimeFormated() {
        Long unformattedStartTime = Long.parseLong(getRideStartTime());
        Date date = new Date(unformattedStartTime);
        DateTimeFormat fmt = DateTimeFormat.getFormat("EEEE, MMMM dd, yyyy");
        return fmt.format((date));
    }

    public final Date getRideStartTimeDate() {
        Long unformattedStartTime = Long.parseLong(getRideStartTime());
        Date date = new Date(unformattedStartTime);
        return date;
    }

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

    public final native void setRideLeaderName(String rideLeaderName) /*-{
        this.rideLeaderName = rideLeaderName;
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
