package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.request.NewEventRequest;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class CreateScreenActivity extends MGWTAbstractActivity implements CreateScreenDisplay.Presenter {
    private final ClientFactory clientFactory;

    public CreateScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final CreateScreenDisplay display = clientFactory.getCreateScreenDisplay();

        display.setPresenter(this);

        panel.setWidget(display);
    }

    @Override
    public void onFormSelected(BikeRide br) {
        final CreateScreenDisplay display = clientFactory.getCreateScreenDisplay();

        NewEventRequest.Builder request = new NewEventRequest.Builder(new NewEventRequest.Callback() {
            @Override
            public void onError() {
                display.displayFailedToCreateRideMessage();
            }

            @Override
            public void onResponseReceived(Response response) {
                display.displayResponse(response);
            }
        });

        request.bikeRide(br);
        request.send();

    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }
}