package com.bikefunfinder.client.shared.model;
/*
 * @author: lancepoehler
 * @created 4/8/13 10:20 PM
 */

import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;

import java.util.Date;

public class User extends JavaScriptObject {
    protected User() {}

    public final native String getId() /*-{
        return this.id;
    }-*/;

    public final native String setId(String id) /*-{
        this.id = id;
    }-*/;

    public final native OAuth getOAuth() /*-{
		return this.oAuth;
    }-*/;

    public final native void setOAuth(OAuth oAuth) /*-{
		this.oAuth = oAuth;
    }-*/;

    public final native String getUserName() /*-{
        return this.userName;
    }-*/;

    public final native void setUserName(String userName) /*-{
        this.userName = userName;
    }-*/;

    public final native String getEmail() /*-{
        return this.email;
    }-*/;

    public final native void setEmail(String email) /*-{
        this.email = email;
    }-*/;

    public final native JsArray<DeviceAccount> getDeviceAccounts() /*-{ return this.DeviceAccounts; }-*/;

    public final native DeviceAccount getDeviceAccount() /*-{
		return this.deviceAccount;
    }-*/;

    public final native void setDeviceAccount(DeviceAccount deviceAccount) /*-{
		this.deviceAccount = DeviceAccount;
    }-*/;

    public final native Boolean getAccountActivated() /*-{
        return this.accountActivated;
    }-*/;

    public final native void setAccountActivated(Boolean accountActivated) /*-{
        this.accountActivated = accountActivated;
    }-*/;

    public final native String getImagePath() /*-{
        return this.imagePath;
    }-*/;

    public final native void getImagePath(String imagePath) /*-{
        this.imagePath = imagePath;
    }-*/;

    public final native double getJoinedTimeStamp() /*-{
        return this.joinedTimeStamp;
    }-*/;

    public final JsDateWrapper createJsDateWrapperJoinedTimeStamp() {
        return new JsDateWrapper(getJoinedTimeStamp());
    }

    public final native boolean getReadTipsForRideLeaders() /*-{
        return this.readTipsForRideLeaders;
    }-*/;

    public final native void setReadTipsForRideLeaders(boolean readTipsForRideLeaders) /*-{
        this.readTipsForRideLeaders = readTipsForRideLeaders;
    }-*/;

    public final native String getLatestActiveTimeStamp() /*-{
        return this.latestActiveTimeStamp;
    }-*/;

    public final native void setLatestActiveTimeStamp(String latestActiveTimeStamp) /*-{
        this.latestActiveTimeStamp = latestActiveTimeStamp;
    }-*/;

    public final native double getLatestActiveTime() /*-{
        return this.latestActiveTimeStamp;
    }-*/;

    public final JsDateWrapper createJsDateWrapperLatestActiveTime() {
        return new JsDateWrapper(getLatestActiveTime());
    }

    public final native int getTotalHostedBikeRideCount() /*-{
        return this.totalHostedBikeRideCount;
    }-*/;

    public final native void setTotalHostedBikeRideCount(int totalHostedBikeRideCount) /*-{
        this.totalHostedBikeRideCount = totalHostedBikeRideCount;
    }-*/;

    public final String getJSON()
    {
        return new JSONObject(this).toString();
    }

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
