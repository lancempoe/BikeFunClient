package com.bikefunfinder.client.shared.model;
/*
 * @author: lancepoehler
 * @created 4/8/13 10:20 PM
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;

public class User extends JavaScriptObject {
    protected User() {}

    public final native String getId() /*-{
        return this.id;
    }-*/;

    public final native String getForeignId() /*-{
        return this.foreignId;
    }-*/;

    public final native String getForeignIdType() /*-{
        return this.foreignIdType;
    }-*/;

    public final native String getUserName() /*-{
        return this.userName;
    }-*/;

    public final native String getEmail() /*-{
        return this.email;
    }-*/;

    public final native JsArray<DeviceAccounts> getDeviceAccounts() /*-{ return this.DeviceAccounts; }-*/;

    public final native DeviceAccounts getDeviceAccount() /*-{
		return this.deviceAccount;
    }-*/;

    public final native Boolean getAccountActivated() /*-{
        return this.accountActivated;
    }-*/;

    public final native String getImagePath() /*-{
        return this.imagePath;
    }-*/;

    public final native String getJoinedTimeStamp() /*-{
        return this.joinedTimeStamp;
    }-*/;

    public final String getJoinedTimeStampFormated() {
        Long unformattedJoinedTimeStamp = Long.parseLong(getJoinedTimeStamp());
        Date date = new Date(unformattedJoinedTimeStamp);
        DateTimeFormat fmt = DateTimeFormat.getFormat("EEEE, MMMM dd, yyyy");
        return fmt.format((date));
    }

    public final Date getJoinedTimeStampDate() {
        Long unformattedJoinedTimeStamp = Long.parseLong(getJoinedTimeStamp());
        Date date = new Date(unformattedJoinedTimeStamp);
        return date;
    }

    public final native boolean getReadTipsForRideLeaders() /*-{
        return this.readTipsForRideLeaders;
    }-*/;

    public final native String getLatestActiveTimeStamp() /*-{
        return this.latestActiveTimeStamp;
    }-*/;

    public final String getLatestActiveTimeStampFormated() {
        Long unformattedLatestActiveTimeStamp = Long.parseLong(getLatestActiveTimeStamp());
        Date date = new Date(unformattedLatestActiveTimeStamp);
        DateTimeFormat fmt = DateTimeFormat.getFormat("EEEE, MMMM dd, yyyy");
        return fmt.format((date));
    }

    public final Date getLatestActiveTimeStampDate() {
        Long unformattedLatestActiveTimeStamp = Long.parseLong(getLatestActiveTimeStamp());
        Date date = new Date(unformattedLatestActiveTimeStamp);
        return date;
    }

    public final native String getTotalHostedBikeRideCount() /*-{
        return this.totalHostedBikeRideCount;
    }-*/;

//    public String id;
//    public String userName;
//    public String email;
//    public List<DeviceAccounts> deviceAccounts = new ArrayList<DeviceAccounts>();
//    public boolean accountActivated;
//    public String imagePath = "Images/Users/defaultUser.jpg"; //In the event that no image is provided.
//    public Long joinedTimeStamp = new DateTime().withZone(DateTimeZone.UTC).toInstant().getMillis();
//    public boolean readTipsForRideLeaders;
//
//    //Generated and send back.  not in DB
//    public List<String> hostedBikeRides;
//    public int hostedBikeRideCount;

}
