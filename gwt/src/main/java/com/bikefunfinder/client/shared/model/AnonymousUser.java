package com.bikefunfinder.client.shared.model;
/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

public class AnonymousUser extends JavaScriptObject {

    protected AnonymousUser() {}


    public final native String getId() /*-{
		return this.id;
    }-*/;

    public final native DeviceAccounts getDeviceAccounts() /*-{
		return this.deviceAccounts;
    }-*/;

    public final native String getImagePath() /*-{
		return this.imagePath;
    }-*/;

    public final native String getUserName() /*-{
		return this.userName;
    }-*/;

    public final native Long getJoinedTimeStamp() /*-{
		return this.joinedTimeStamp;
    }-*/;

    public final native String getReadTipsForRideLeaders() /*-{
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

    public final native Long getTotalHostedBikeRideCount() /*-{
		return this.totalHostedBikeRideCount;
    }-*/;
    //public String id;
    //public DeviceAccounts deviceAccounts = new DeviceAccounts();
    //public String imagePath = "Images/Users/defaultUser.jpg"; //Default user image.
    //public String userName = "Anonymous"; //User should never change.
    //public Long joinedTimeStamp = new DateTime().withZone(DateTimeZone.UTC).toInstant().getMillis();

}
