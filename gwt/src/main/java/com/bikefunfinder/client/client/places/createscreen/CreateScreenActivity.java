package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.bootstrap.db.DBKeys;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeolocationCallback;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
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

    public CreateScreenActivity(ClientFactory<CreateScreenDisplay> clientFactory) {
        this.clientFactory = clientFactory;
        setUserNameFields(clientFactory);

        clientFactory.getDisplay(this).display(userName);
    }

    private void setUserNameFields(ClientFactory<CreateScreenDisplay> clientFactory) {
        //Set the logged in user details
        if (clientFactory.getStoredValue(DBKeys.USER) != null) {
            User user = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.USER));
            setUserDisplayElements(user.getId(), user.getUserName());
        }
        else if (clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER) != null) {
            AnonymousUser anonymousUser = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER));
            setUserDisplayElements(anonymousUser.getId(), anonymousUser.getUserName());
        }
    }

    private void setUserDisplayElements(String id, String name) {
        this.userId = id;
        this.userName = name;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final CreateScreenDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        display.resetState();
        panel.setWidget(display);
    }

    @Override
    public void onFormSelected(BikeRide br) {
        final CreateScreenDisplay display = clientFactory.getDisplay(this);

        final NewEventRequest.Builder request = new NewEventRequest.Builder(new NewEventRequest.Callback() {
            @Override
            public void onError() {
                //display.displayFailedToCreateRideMessage();
                //At this point the message has already been displayed to the user.
            }

            @Override
            public void onResponseReceived(BikeRide bikeRide) {
                clientFactory.refreshUserAccount();
                clientFactory.getPlaceController().goTo(new EventScreenPlace(bikeRide));
            }
        });

        br.setRideLeaderId(userId);
        br.setRideLeaderName(userName);

        request.bikeRide(br);

        DeviceTools.getPhoneGeoLoc(clientFactory, new NonPhoneGapGeolocationCallback() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                request.latitude(geoLoc).longitude(geoLoc).send();
            }

            @Override
            public void onFailure(GeoLoc geoLoc) {
                request.latitude(geoLoc).longitude(geoLoc).send();
            }
        });


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