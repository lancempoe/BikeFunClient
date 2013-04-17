package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.editscreen.EditScreenDisplay;
import com.bikefunfinder.client.client.places.editscreen.EditScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenDisplay;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenDisplay;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.usereventsscreen.UserEventsScreenDisplay;
import com.bikefunfinder.client.client.places.usereventsscreen.UserEventsScreenDisplayGwtImpl;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;


public class ClientFactoryGwtImpl implements ClientFactory {

    private final PhoneGap phoneGap;
    private SimpleEventBus eventBus;
    private PlaceController placeController;
    private HomeScreenDisplay homeScreenDisplay;
    private CreateScreenDisplay createScreenDisplay;
    private ProfileScreenDisplay profileScreenDisplay;
    private SearchScreenDisplay searchScreenDisplay;
    private EventScreenDisplay eventScreenDisplay;
    private EditScreenDisplay editScreenDisplay;
    private UserEventsScreenDisplay userEventsScreenDisplay;

    public ClientFactoryGwtImpl(PhoneGap phoneGap) {
        this.phoneGap = phoneGap;

        eventBus = new SimpleEventBus();
        placeController = new PlaceController(eventBus);
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

    @Override
    public ProfileScreenDisplay getProfileScreenDisplay() {
        if (profileScreenDisplay == null) {
            profileScreenDisplay = new ProfileScreenDisplayGwtImpl();
        }
        return profileScreenDisplay;
    }

    @Override
    public EventScreenDisplay getEventScreenDisplay() {
        if(eventScreenDisplay == null) {
            eventScreenDisplay = new EventScreenDisplayGwtImpl();
        }
        return eventScreenDisplay;
    }

    @Override
    public EditScreenDisplay getEditScreenDisplay() {
        if (editScreenDisplay == null) {
            editScreenDisplay = new EditScreenDisplayGwtImpl();
        }
        return editScreenDisplay;
    }

    @Override
    public SearchScreenDisplay getSearchScreenDisplay() {
        if (searchScreenDisplay == null) {
            searchScreenDisplay = new SearchScreenDisplayGwtImpl();
        }
        return searchScreenDisplay;
    }

    @Override
    public UserEventsScreenDisplay getUserEventsScreenDisplay() {
        if(userEventsScreenDisplay == null) {
            userEventsScreenDisplay = new UserEventsScreenDisplayGwtImpl();
        }
        return userEventsScreenDisplay;
    }
}