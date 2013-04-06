package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplayGwtImpl;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;


public class ClientFactoryGwtImpl implements ClientFactory {

    private final PhoneGap phoneGap;
    private SimpleEventBus eventBus;
    private PlaceController placeController;
    private HomeScreenDisplay homeScreenDisplay;

    public ClientFactoryGwtImpl(PhoneGap phoneGap) {
        eventBus = new SimpleEventBus();

        placeController = new PlaceController(eventBus);
        this.phoneGap = phoneGap;
    }

    @Override
    public PhoneGap getPhoneGap() {
        return phoneGap;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }


    @Override
    public CreateScreenDisplay getCreateScreenDisplay() {
        if (createScreenDisplay == null) {
            createScreenDisplay = new CreateScreenDisplayGwtImpl();
        }
        return createScreenDisplay;
    }

    @Override
    public HomeScreenDisplay getHomeScreenDisplay() {
        if (homeScreenDisplay == null) {
            homeScreenDisplay = new HomeScreenDisplayGwtImpl();
        }
        return homeScreenDisplay;
    }
}
