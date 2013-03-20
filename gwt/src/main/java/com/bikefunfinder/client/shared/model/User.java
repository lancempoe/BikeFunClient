package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/19/13 10:20 PM
 */

import java.util.ArrayList;
import java.util.List;

public class User {

    public String id;
    public String userName;
    public String email;
    public List<DeviceAccounts> deviceAccounts = new ArrayList<DeviceAccounts>();
    public boolean accountActivated;
    public String imagePath = "Images/Users/defaultUser.jpg"; //In the event that no image is provided.
    public Long joinedTimeStamp;
    public boolean readTipsForRideLeaders;
    public boolean readTermsOfAgreement;

    //Generated and send back.  not in DB
    public List<String> hostedBikeRides;
    public int hostedBikeRideCount;

}
