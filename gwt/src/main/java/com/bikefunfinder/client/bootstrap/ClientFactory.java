package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenDisplay;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenDisplay;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.bikefunfinder.client.client.places.gmap.*;

public interface ClientFactory {
    public PhoneGap getPhoneGap();

    public PlaceController getPlaceController();

    public EventBus getEventBus();

    public CreateScreenDisplay getCreateScreenDisplay();

    public HomeScreenDisplay getHomeScreenDisplay();

    public ProfileScreenDisplay getProfileScreenDisplay();

    public EventScreenDisplay getEventScreenDisplay();

    public SearchScreenDisplay getSearchScreenDisplay();

    public GMapDisplay getHereAndNowDisplay();

    public String getStoredValue(String value);

    public void validateValidUser();
}