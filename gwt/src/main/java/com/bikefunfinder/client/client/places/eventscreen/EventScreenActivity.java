package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class EventScreenActivity extends MGWTAbstractActivity implements EventScreenDisplay.Presenter {
    private final ClientFactory<EventScreenDisplay> clientFactory;

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final EventScreenDisplay display = clientFactory.getDisplay(this);

        display.setPresenter(this);

        panel.setWidget(display);
    }
    public EventScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }

}