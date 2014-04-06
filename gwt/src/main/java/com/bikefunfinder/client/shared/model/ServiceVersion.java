package com.bikefunfinder.client.shared.model;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by lancepoehler on 4/3/14.
 */
public class ServiceVersion extends JavaScriptObject {
    protected ServiceVersion() {}

    public final native String getVersion() /*-{
        return this.version;
    }-*/;
}
