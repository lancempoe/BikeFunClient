package com.bikefunfinder.client.client.places.editscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class EditScreenActivity extends MGWTAbstractActivity implements EditScreenDisplay.Presenter {
    private final ClientFactory clientFactory;

    public EditScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
}