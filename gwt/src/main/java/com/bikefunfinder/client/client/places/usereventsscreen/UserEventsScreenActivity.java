package com.bikefunfinder.client.client.places.usereventsscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class UserEventsScreenActivity extends MGWTAbstractActivity implements UserEventsScreenDisplay.Presenter {
    private final ClientFactory clientFactory;

    public UserEventsScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
}