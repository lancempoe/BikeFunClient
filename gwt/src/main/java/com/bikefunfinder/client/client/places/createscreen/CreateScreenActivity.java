package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.bootstrap.db.DBKeys;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.User;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.request.NewEventRequest;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class CreateScreenActivity extends MGWTAbstractActivity implements CreateScreenDisplay.Presenter {
    private final ClientFactory<CreateScreenDisplay> clientFactory;
    private String userName = "";
    private String userId = "";

    public CreateScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;

        //Set the logged in user details
        if (clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER) != null) {
            AnonymousUser anonymousUser = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER));
            this.userId = anonymousUser.getId();
            this.userName = anonymousUser.getUserName();
        }
        else if (clientFactory.getStoredValue(DBKeys.USER) != null) {
            User user = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.USER));
            this.userId = user.getId();
            this.userName = user.getUserName();
        }
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final CreateScreenDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        panel.setWidget(display);
        display.display(userName);
    }

    @Override
    public void onFormSelected(BikeRide br) {
        final CreateScreenDisplay display = clientFactory.getDisplay(this);

        NewEventRequest.Builder request = new NewEventRequest.Builder(new NewEventRequest.Callback() {
            @Override
            public void onError() {
                display.displayFailedToCreateRideMessage();
            }

            @Override
            public void onResponseReceived(BikeRide bikeRide) {
                clientFactory.getPlaceController().goTo(new EventScreenPlace(bikeRide));
            }
        });

        br.setRideLeaderId(userId);
        br.setRideLeaderName(userName);

        request.bikeRide(br);
        request.send();

    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }

    @Override
    public void onTimeSelected() {

    }

    native void showTimeModal() /*-{
        //$('#timepicker').mobiscroll('show');
    }-*/;
}