package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 10:21 PM
 */

import com.google.gwt.core.client.JavaScriptObject;

public class DeviceAccount extends JavaScriptObject {
    protected DeviceAccount() {}

    public final native String getDeviceUUID() /*-{
        return this.deviceUUID;
    }-*/;

    public final native String getKey() /*-{
        return this.key;
    }-*/;
}