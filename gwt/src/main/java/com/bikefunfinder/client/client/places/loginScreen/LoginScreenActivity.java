package com.bikefunfinder.client.client.places.loginscreen;
/*
 * @author: tneuwerth
 * @created 4/4/13 10:55 AM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class LoginScreenActivity extends MGWTAbstractActivity implements LoginScreenDisplay.Presenter {
    private final ClientFactory clientFactory;

    public LoginScreenActivity(ClientFactory clientFactory) {
            this.clientFactory = clientFactory;
    }
}