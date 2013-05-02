package com.bikefunfinder.client.shared.widgets;
/*
 * @author: tneuwerth
 * @created 5/1/13 10:26 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenDisplay;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class CommonActivity<ME extends ClientFactory> extends MGWTAbstractActivity {

    private final ClientFactory<ME> clientFactory = null;

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final ME display = clientFactory.getDisplay(this);
        //display.setPresenter(this);
        //panel.setWidget(display);
    }
}
