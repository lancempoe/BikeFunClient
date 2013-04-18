package com.bikefunfinder.client.shared.model;

/**
 * Created with IntelliJ IDEA.
 * User: nathanfrost
 * Date: 4/17/13
 * Time: 7:13 PM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;

public class XDate extends JavaScriptObject implements Comparable<XDate> {

    protected XDate() {}

    @Override
    public final int compareTo(XDate bikeDate)  {
        return compareTo(bikeDate.getTime());
    }

    public final native void setNewXDate(double rideStartTime) /*-{
        this.xDate = new $wnd.XDate(rideStartTime);
    }-*/;

    private final native int compareTo(Double rideStartTime) /*-{
        if(this.xDate.getTime() == rideStartTime)
        {
            return 0;
        }
        if(this.xDate.getTime() > rideStartTime)
        {
            return 1;
        }
        if(this.xDate.getTime() < rideStartTime)
        {
            return -1;
        }
    }-*/;
    public final native Double getTime() /*-{
        return this.xDate.getTime();
    }-*/;

    public final native String toString(String xDateFormat) /*-{
        return this.xDate.toString(xDateFormat);
    }-*/;

    public final JsDate jsDate()
    {
        return JsDate.create(getTime());
    }

}
