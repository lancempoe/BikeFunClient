package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class ProfileScreenActivity extends MGWTAbstractActivity implements ProfileScreenDisplay.Presenter {
    private final ClientFactory clientFactory;

    public ProfileScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
}