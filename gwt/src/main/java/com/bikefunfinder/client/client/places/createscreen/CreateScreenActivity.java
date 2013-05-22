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
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.request.DeleteEventRequest;
import com.bikefunfinder.client.shared.request.NewEventRequest;
import com.bikefunfinder.client.shared.request.UpdateEventRequest;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

public class CreateScreenActivity extends MGWTAbstractActivity implements CreateScreenDisplay.Presenter {
    private final ClientFactory<CreateScreenDisplay> clientFactory;
    private AnonymousUser anonymousUser;
    private User user;
    private boolean existingEvent = false;

    public CreateScreenActivity(ClientFactory<CreateScreenDisplay> clientFactory, BikeRide bikeRide) {
        this.clientFactory = clientFactory;
        setUserNameFields(clientFactory);
        clientFactory.getDisplay(this).display(getUserName());

        final CreateScreenDisplay display = clientFactory.getDisplay(this);
        if (bikeRide != null) {
            clientFactory.getDisplay(this).display("Updating Ride with: " + getUserName());
            existingEvent = true; //typeIsUpdate
            display.display(bikeRide);
        } else {
            clientFactory.getDisplay(this).display("Creating Ride with: " + getUserName());
        }

        display.setVisibilityOfButtons(existingEvent);
    }

    private String getUserName() {
        String userName = "";
        if (user != null) {
            userName = user.getUserName();
        } else if (anonymousUser != null) {
            userName = anonymousUser.getUserName();
        }
        return userName;
    }

    private String getUserId() {
        String userId = "";
        if (user != null) {
            userId = user.getId();
        } else if (anonymousUser != null) {
            userId = anonymousUser.getId();
        }
        return userId;
    }

    private void setUserNameFields(ClientFactory<CreateScreenDisplay> clientFactory) {
        //Set the logged in user details
        if (clientFactory.getStoredValue(DBKeys.USER) != null) {
            User user = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.USER));
            this.user = user;
        }
        else if (clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER) != null) {
            AnonymousUser anonymousUser = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER));
            this.anonymousUser = anonymousUser;
        }
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final CreateScreenDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        if (!existingEvent) { display.resetState(); }
        panel.setWidget(display);
    }

    @Override
    public void onCreateSelected(BikeRide br) {
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

        br.setRideLeaderId(getUserId());
        br.setRideLeaderName(getUserName());

        request.bikeRide(br);

        DeviceTools.getPhoneGeoLoc(clientFactory, new NonPhoneGapGeoLocCallback() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                request.latitude(geoLoc).longitude(geoLoc).send();
            }

            @Override
            public void onFailure(GeoLoc geoLoc) {
                //TODO Show Error?  Defaulting location
                request.latitude(geoLoc).longitude(geoLoc).send();
            }
        });
    }

    @Override
    public void onUpdateSelected(final Root root) {

        final UpdateEventRequest.Builder request = new UpdateEventRequest.Builder(new UpdateEventRequest.Callback() {
            @Override
            public void onError() {
                //At this point the message has already been displayed to the user.
            }

            @Override
            public void onResponseReceived(BikeRide bikeRide) {
                clientFactory.refreshUserAccount();
                clientFactory.getPlaceController().goTo(new EventScreenPlace(bikeRide));
            }
        });

        if (user != null) {
            root.setUser(user);
        } else {
            root.setAnonymousUser(anonymousUser);
        }
        request.root(root);

        DeviceTools.getPhoneGeoLoc(clientFactory, new NonPhoneGapGeoLocCallback() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                request.latitude(geoLoc).longitude(geoLoc).send();
            }

            @Override
            public void onFailure(GeoLoc geoLoc) {
                //TODO Show Error?  Defaulting location
                request.latitude(geoLoc).longitude(geoLoc).send();
            }
        });

    }

    @Override
    public void onDeleteSelected(final BikeRide bikeRide) {
        Dialogs.confirm("Warning:", "Are you sure you want to delete this event?", new ConfirmDialog.ConfirmCallback() {
            @Override
            public void onOk() {
                DeleteEvent(bikeRide);
            }

            @Override
            public void onCancel() {
                //Ignore.  The user choose to not delete.
            }
        });
    }

    private void DeleteEvent(BikeRide bikeRide) {
        final DeleteEventRequest.Builder request = new DeleteEventRequest.Builder(new DeleteEventRequest.Callback() {
            @Override
            public void onError() {
                //At this point the message has already been displayed to the user.
            }

            @Override
            public void onResponseReceived() {
                clientFactory.refreshUserAccount();
                clientFactory.getPlaceController().goTo(new HomeScreenPlace());
            }
        });

        request.root(BuildRoot(bikeRide));
        request.send();
    }

    private Root BuildRoot(BikeRide bikeRide) {
        Root root = GWT.create(Root.class);
        JsArray<BikeRide> bikeRides = JsArray.createArray().cast();
        bikeRides.push(bikeRide);
        root.setBikeRides(bikeRides);

        if (user != null) {
            root.setUser(user);
        } else {
            root.setAnonymousUser(anonymousUser);
        }
        return root;
    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }

    @Override
    public void onTimeSelected() {

    }
}