package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Query;
import com.bikefunfinder.client.shared.model.Tracking;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

import java.util.ArrayList;
import java.util.List;

public class EventScreenActivity extends MGWTAbstractActivity implements EventScreenDisplay.Presenter {

private final ClientFactory<EventScreenDisplay> clientFactory;
private final BikeRide bikeRide;

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final EventScreenDisplay display = clientFactory.getDisplay(this);

        display.setPresenter(this);

        panel.setWidget(display);

        display.display(bikeRide);
        JsArray<Tracking> trackings = bikeRide.getCurrentTrackings();
        trackings.push(bikeRide.getRideLeaderTracking());
        display.display(trackings);

    }
    public EventScreenActivity(ClientFactory clientFactory, BikeRide bikeRide) {
        this.clientFactory = clientFactory;
        this.bikeRide = bikeRide;
    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }

}