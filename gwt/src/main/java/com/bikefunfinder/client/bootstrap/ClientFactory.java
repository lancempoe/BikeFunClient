package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;


public interface ClientFactory {
    public PhoneGap getPhoneGap();

    public PlaceController getPlaceController();

    public EventBus getEventBus();

    public HomeScreenDisplay getHomeScreenDisplay();

}
