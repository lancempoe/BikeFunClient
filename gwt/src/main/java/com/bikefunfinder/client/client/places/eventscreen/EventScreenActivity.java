package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.gmap.GMapPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.shared.Tools.NavigationHelper;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Tracking;
import com.bikefunfinder.client.shared.model.User;
import com.bikefunfinder.client.shared.request.management.AnnonymousUserCacheStrategy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class EventScreenActivity extends MGWTAbstractActivity implements EventScreenDisplay.Presenter {

private final ClientFactory<EventScreenDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private final AnonymousUser anonymousUser;
    private final User user;

    private BikeRide bikeRide;

    private String userName = "";
    private String userId = "";

    public EventScreenActivity(BikeRide bikeRide, User user, AnonymousUser anonymousUser) {
        this.bikeRide = bikeRide;
        this.user = user;
        this.anonymousUser = anonymousUser;

        if(bikeRide==null) {
            clientFactory.getPlaceController().goTo(new HomeScreenPlace());
            return;
        }

        final EventScreenDisplay display = this.clientFactory.getDisplay(this);
        setUserNameFields();
        display.resetState();
        display.display(bikeRide.getBikeRideName());
        setupDisplay(bikeRide);

        if (bikeRide.getRideLeaderId().equals(userId)) {
            display.displayEdit(true);
        } else {
            display.displayEdit(false);
        }
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final EventScreenDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        panel.setWidget(display);
    }

    private void setUserNameFields() {
        //Set the logged in user details
        if (user != null) {
            setUserDisplayElements(user.getId(), user.getUserName());
        } else if (anonymousUser!=null) {
            setUserDisplayElements(anonymousUser.getId(), anonymousUser.getUserName());
        }
    }

    private void setUserDisplayElements(String id, String name) {
        this.userId = id;
        this.userName = name;
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
        NavigationHelper.goToPriorScreen(clientFactory.getPlaceController());
    }

    @Override
    public void eventRideMapButtonSelected() {
        clientFactory.getPlaceController().goTo(new GMapPlace(this.bikeRide.getBikeRideName(), this.bikeRide));
    }

    @Override
    public void editRideButtonSelected() {
        clientFactory.getPlaceController().goTo(new CreateScreenPlace(this.bikeRide, null, AnnonymousUserCacheStrategy.INSTANCE.getCachedType()));
    }

    @Override
    public void copyRideButtonSelected() {
        BikeRide copiedBikeRide = GWT.create(BikeRide.class);
        copiedBikeRide.setBikeRideName(this.bikeRide.getBikeRideName());
        copiedBikeRide.setDetails(this.bikeRide.getDetails());
        copiedBikeRide.setLocation(this.bikeRide.getLocation());
        copiedBikeRide.setCityLocationId(this.bikeRide.getCityLocationId());
        copiedBikeRide.copyRideStartTime(this.bikeRide);
        copiedBikeRide.setTrackingAllowed(this.bikeRide.isTrackingAllowed());
        copiedBikeRide.setTargetAudience(this.bikeRide.getTargetAudience());
        clientFactory.getPlaceController().goTo(new CreateScreenPlace(copiedBikeRide, true, null, AnnonymousUserCacheStrategy.INSTANCE.getCachedType()));
    }
}