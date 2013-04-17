package com.bikefunfinder.client.client.places.editscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.request.NewEventRequest;
import com.bikefunfinder.client.shared.request.UpdateEventRequest;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class EditScreenActivity extends MGWTAbstractActivity implements EditScreenDisplay.Presenter {
    private final ClientFactory clientFactory;

    public EditScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final EditScreenDisplay display = clientFactory.getEditScreenDisplay();

        display.setPresenter(this);

        panel.setWidget(display);
    }

    @Override
    public void onFormSelected(BikeRide br) {
        final EditScreenDisplay display = clientFactory.getEditScreenDisplay();
        final EventScreenDisplay eventDisplay = clientFactory.getEventScreenDisplay();

        UpdateEventRequest.Builder request = new UpdateEventRequest.Builder(new UpdateEventRequest.Callback() {
            @Override
            public void onError() {
                display.displayFailedToEditRideMessage();
            }

            @Override
            public void onResponseReceived() {
                //eventDisplay.display(br);
                //TODO REDIRECT TO THE BIKE RIDE PAGE.
            }
        });

//        request.root(br);
//        request.send();

    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }
}