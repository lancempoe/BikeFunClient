package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 10:19 PM
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Root extends JavaScriptObject {

    protected Root() {}

    public final native JsArray<BikeRide> getBikeRides() /*-{ return this.BikeRides; }-*/;
    public final native ClosestLocation getClosestLocation() /*-{ return this.ClosestLocation; }-*/;
    public final native User getUser() /*-{ return this.User; }-*/;
    public final native AnonymousUser getAnonymousUser() /*-{ return this.AnonymousUser; }-*/;

    public final native void setBikeRides(JsArray<BikeRide> BikeRides) /*-{
        this.BikeRides = BikeRides;
    }-*/;

    public final native void setAnonymousUser(AnonymousUser AnonymousUser) /*-{
        this.AnonymousUser = AnonymousUser;
    }-*/;

    public final native void setUser(User User) /*-{
        this.User = User;
    }-*/;

}