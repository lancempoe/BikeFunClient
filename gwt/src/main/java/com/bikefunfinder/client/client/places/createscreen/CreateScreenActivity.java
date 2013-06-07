package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.bootstrap.db.DBKeys;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NativeUtilities;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.request.*;
import com.bikefunfinder.client.shared.request.converters.NoOpResponseObject;
import com.bikefunfinder.client.shared.request.ratsnest.CacheStrategy;
import com.bikefunfinder.client.shared.request.ratsnest.GeoLocCacheStrategy;
import com.bikefunfinder.client.shared.request.ratsnest.WebServiceResponseConsumer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

public class CreateScreenActivity extends MGWTAbstractActivity implements CreateScreenDisplay.Presenter {

    private final ClientFactory<CreateScreenDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private boolean existingEvent = false;
    private final AnonymousUser anonymousUser;
    private final User user;

    public CreateScreenActivity(BikeRide bikeRide, User user, AnonymousUser anonymousUser) {
        this.user = user;
        this.anonymousUser = anonymousUser;

        final CreateScreenDisplay display = clientFactory.getDisplay(this);

        if (bikeRide != null) {
            display.setUserNameOnDisplay("Updating Ride with: " + getUserName());
            existingEvent = true; //typeIsUpdate
            display.populateWithExistingBikeRideDetails(bikeRide);
        } else {
            display.setUserNameOnDisplay("Creating Ride with: " + getUserName());
        }

        display.setVisibilityOfButtons(existingEvent);
        if (existingEvent) {
            NativeUtilities.trackPage("Update Screen");
        } else {
            NativeUtilities.trackPage("Create Screen");
        }
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

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final CreateScreenDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        if (!existingEvent) { display.resetState(); }
        panel.setWidget(display);
    }

    @Override
    public void onCreateSelected(final BikeRide createFormBikeRideState) {

        createFormBikeRideState.setRideLeaderId(getUserId());
        createFormBikeRideState.setRideLeaderName(getUserName());

        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                final NewEventRequest.Builder request = new NewEventRequest.Builder(new WebServiceResponseConsumer<BikeRide>() {
                    @Override
                    public void onResponseReceived(final BikeRide bikeRide) {
                        clientFactory.getPlaceController().goTo(new EventScreenPlace(bikeRide));
                    }
                });
                request.bikeRide(createFormBikeRideState).latitude(geoLoc).longitude(geoLoc).send();
            }
        }, GeoLocCacheStrategy.INSTANCE));
    }

    @Override
    public void onUpdateSelected(final Root root) {
        if (user != null) {
            root.setUser(user);
        } else {
            root.setAnonymousUser(anonymousUser);
        }

        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                final UpdateEventRequest.Builder updateBikeRideRequest = new UpdateEventRequest.Builder(new WebServiceResponseConsumer<BikeRide>() {
                    @Override
                    public void onResponseReceived(BikeRide bikeRide) {
                        clientFactory.getPlaceController().goTo(new EventScreenPlace(bikeRide));
                    }
                });
                updateBikeRideRequest.root(root).latitude(geoLoc).longitude(geoLoc).send();
            }
        }, GeoLocCacheStrategy.INSTANCE));

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

    private void DeleteEvent(final BikeRide bikeRide) {
        final DeleteEventRequest.Builder request = new DeleteEventRequest.Builder(new WebServiceResponseConsumer<NoOpResponseObject>() {
            @Override
            public void onResponseReceived(NoOpResponseObject noOpResponseObject) {
                clientFactory.getPlaceController().goTo(new HomeScreenPlace());
            }
        });

        request.root(BuildRoot(bikeRide)).send();
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