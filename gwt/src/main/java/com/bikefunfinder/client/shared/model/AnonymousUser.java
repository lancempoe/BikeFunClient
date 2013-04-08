package com.bikefunfinder.client.shared.model;
/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
import com.google.gwt.core.client.JavaScriptObject;

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

    //public String id;
    //public DeviceAccounts deviceAccounts = new DeviceAccounts();
    //public String imagePath = "Images/Users/defaultUser.jpg"; //Default user image.
    //public String userName = "Anonymous"; //User should never change.
    //public Long joinedTimeStamp = new DateTime().withZone(DateTimeZone.UTC).toInstant().getMillis();

}
