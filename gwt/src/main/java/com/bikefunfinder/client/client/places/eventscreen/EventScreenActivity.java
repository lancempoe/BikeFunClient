package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.bootstrap.db.DBKeys;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.bikefunfinder.client.client.places.gmap.GMapPlace;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.shared.Tools.NativeUtilities;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Tracking;
import com.bikefunfinder.client.shared.model.User;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.request.AnonymousRequest;
import com.bikefunfinder.client.shared.request.ratsnest.AnnonymousUserCacheStrategy;
import com.bikefunfinder.client.shared.request.ratsnest.WebServiceResponseConsumer;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.MGWT;

public class EventScreenActivity extends MGWTAbstractActivity implements EventScreenDisplay.Presenter {

private final ClientFactory<EventScreenDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private final AnonymousUser anonymousUser;
    private final User user;
    private final boolean cameFromGmap;

    private BikeRide bikeRide;

    private String userName = "";
    private String userId = "";

    public EventScreenActivity(BikeRide bikeRide, boolean cameFromGmap, User user, AnonymousUser anonymousUser) {
        this.cameFromGmap = cameFromGmap;
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
        setupDisplay(this.bikeRide);

        if (this.bikeRide.getRideLeaderId().equals(userId)) {
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

        NativeUtilities.trackPage("Event Screen");
    }

    @Override
    public void backButtonSelected() {
        if(cameFromGmap) {
            clientFactory.getPlaceController().goTo(new GMapPlace("Here & Now"));
            return;
        }

        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }

    @Override
    public void eventRideMapButtonSelected() {
        clientFactory.getPlaceController().goTo(new GMapPlace(this.bikeRide.getBikeRideName(), this.bikeRide));
    }

    @Override
    public void editRideButtonSelected() {
        clientFactory.getPlaceController().goTo(new CreateScreenPlace(this.bikeRide, null, AnnonymousUserCacheStrategy.INSTANCE.getCachedType()));
    }
}