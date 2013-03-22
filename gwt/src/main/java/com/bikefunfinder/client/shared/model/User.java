package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 10:20 PM
 */

import com.google.gwt.core.client.JavaScriptObject;

public class User extends JavaScriptObject {
    protected User() {}

    public final native String getId() /*-{
        return thist.id;
    }-*/;

    public final native String getUserName() /*-{
        return this.userName;
    }-*/;


//    public String email;
//    public List<DeviceAccounts> deviceAccounts = new ArrayList<DeviceAccounts>();
//    public boolean accountActivated;
//    public String imagePath = "Images/Users/defaultUser.jpg"; //In the event that no image is provided.
//    public Long joinedTimeStamp;
//    public boolean readTipsForRideLeaders;
//    public boolean readTermsOfAgreement;
//
//    //Generated and send back.  not in DB
//    public List<String> hostedBikeRides;
//    public int hostedBikeRideCount;

}
