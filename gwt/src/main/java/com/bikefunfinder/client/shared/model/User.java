package com.bikefunfinder.client.shared.model;
/*
 * @author: lancepoehler
 * @created 4/8/13 10:20 PM
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

public class User extends JavaScriptObject {
    protected User() {}

    public final native String getId() /*-{
        return this.id;
    }-*/;

    public final native OAuth getOAuth() /*-{
		return this.oAuth;
    }-*/;

    public final native String getUserName() /*-{
        return this.userName;
    }-*/;

    public final native String getEmail() /*-{
        return this.email;
    }-*/;

    public final native JsArray<DeviceAccount> getDeviceAccounts() /*-{ return this.DeviceAccounts; }-*/;

    public final native DeviceAccount getDeviceAccount() /*-{
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
        Long unformattedLatestActiveTimeStamp = Long.parseLong(getJoinedTimeStamp());
        Date date = new Date(unformattedLatestActiveTimeStamp);
        DateTimeFormat fmt = DateTimeFormat.getFormat("EEEE, MMMM dd, yyyy");
        return fmt.format((date));
    }

    public final Date getLatestActiveTimeStampDate() {
        Long unformattedLatestActiveTimeStamp = Long.parseLong(getJoinedTimeStamp());
        Date date = new Date(unformattedLatestActiveTimeStamp);
        return date;
    }

    public final native int getTotalHostedBikeRideCount() /*-{
        return this.totalHostedBikeRideCount;
    }-*/;

//    public String id;
//    public OAuth oAuth;
//    public String userName;
//    public String email;
//    public List<DeviceAccount> deviceAccounts = new ArrayList<DeviceAccount>();
//    public DeviceAccount deviceAccount = new DeviceAccount();
//    public boolean accountActivated = true;
//    public String imagePath;
//    public Long joinedTimeStamp = new DateTime().withZone(DateTimeZone.UTC).toInstant().getMillis();
//    public boolean readTipsForRideLeaders = false;
//    public Long latestActiveTimeStamp = new DateTime().withZone(DateTimeZone.UTC).toInstant().getMillis();
//    public int totalHostedBikeRideCount;

}
