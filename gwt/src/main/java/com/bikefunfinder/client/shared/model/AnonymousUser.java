package com.bikefunfinder.client.shared.model;
/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;

import java.util.Date;

public class AnonymousUser extends JavaScriptObject {

    protected AnonymousUser() {}


    public final native String getId() /*-{
		return this.id;
    }-*/;

    public final native DeviceAccount getDeviceAccount() /*-{
		return this.deviceAccount;
    }-*/;

    public final native String getImagePath() /*-{
		return this.imagePath;
    }-*/;

    public final native String getUserName() /*-{
		return this.userName;
    }-*/;

    public final native double getJoinedTimeStamp() /*-{
        return this.joinedTimeStamp;
    }-*/;

    public final JsDateWrapper createJsDateWrapperJoinedTimeStamp() {
        JsDateWrapper bd = JsDateWrapper.create(getJoinedTimeStamp());
        return bd;
    }

    public final native boolean getReadTipsForRideLeaders() /*-{
        return this.readTipsForRideLeaders;
    }-*/;

    public final native String getLatestActiveTimeStamp() /*-{
        return this.latestActiveTimeStamp;
    }-*/;

    public final native int getTotalHostedBikeRideCount() /*-{
        return this.totalHostedBikeRideCount;
    }-*/;

    public final String getJSON()
    {
        return new JSONObject(this).toString();
    }


//    public String id;
//    public DeviceAccount deviceAccount = new DeviceAccount();
//    public String imagePath;
//    public String userName = "Anonymous"; //User should never change.
//    public Long joinedTimeStamp = new DateTime().withZone(DateTimeZone.UTC).toInstant().getMillis();
//    public boolean readTipsForRideLeaders = false;
//    public Long latestActiveTimeStamp = new DateTime().withZone(DateTimeZone.UTC).toInstant().getMillis();
//    public int totalHostedBikeRideCount;

}
