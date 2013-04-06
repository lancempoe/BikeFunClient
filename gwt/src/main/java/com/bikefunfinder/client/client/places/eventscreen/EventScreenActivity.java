package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class EventScreenActivity extends MGWTAbstractActivity implements EventScreenDisplay.Presenter {
    private final ClientFactory clientFactory;

    public EventScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
}