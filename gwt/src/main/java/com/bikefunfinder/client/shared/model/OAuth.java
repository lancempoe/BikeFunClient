package com.bikefunfinder.client.shared.model;
/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/14/13
 * Time: 7:13 PM
 */

import com.google.gwt.core.client.JavaScriptObject;

public class OAuth extends JavaScriptObject {

    protected OAuth() {}


    public final native String getForeignId() /*-{
		return this.foreignId;
    }-*/;

    public final native String getForeignIdType() /*-{
		return this.foreignIdType;
    }-*/;

    public final native Long getAccessToken() /*-{
		return this.accessToken;
    }-*/;


//    public String foreignId;
//    public String foreignIdType; //Of type ForeignIdType only.
//    public String accessToken;
}
