package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Tracking;
import com.google.gwt.core.client.JsArray;
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

    public EventScreenActivity(ClientFactory clientFactory, BikeRide bikeRide) {
        this.clientFactory = clientFactory;
        final EventScreenDisplay display = this.clientFactory.getDisplay(this);
        display.resetState();
        setupDisplay(bikeRide);
    }

    private void setupDisplay(BikeRide bikeRide) {
        EventScreenDisplay display = clientFactory.getDisplay(this);

        if(bikeRide==null) {
            return;
        }

        display.display(bikeRide);

        JsArray<Tracking> trackings = bikeRide.getCurrentTrackings();
        if(trackings!=null) {
            if(bikeRide.getRideLeaderTracking()!=null) {
                trackings.push(bikeRide.getRideLeaderTracking());
            }
        }
        display.displayTrackings(trackings);

    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }

}