package com.bikefunfinder.client.bootstrap.tablet;

import com.bikefunfinder.client.client.places.createscreen.CreateScreenActivity;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.editscreen.EditScreenActivity;
import com.bikefunfinder.client.client.places.editscreen.EditScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenActivity;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenActivity;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.client.places.loginscreen.LoginScreenActivity;
import com.bikefunfinder.client.client.places.loginscreen.LoginScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenActivity;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.usereventsscreen.UserEventsScreenActivity;
import com.bikefunfinder.client.client.places.usereventsscreen.UserEventsScreenPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.bikefunfinder.client.bootstrap.ClientFactory;


class TabletNavActivityMapper implements ActivityMapper {

    private final ClientFactory clientFactory;

    public TabletNavActivityMapper(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
        if(place instanceof CreateScreenPlace) {
            return new CreateScreenActivity(clientFactory);
        }
        if(place instanceof EditScreenPlace) {
            return new EditScreenActivity(clientFactory);
        }
        if(place instanceof EventScreenPlace) {
            return new EventScreenActivity(clientFactory);
        }
        if(place instanceof HomeScreenPlace) {
            return new HomeScreenActivity(clientFactory);
        }
        if(place instanceof LoginScreenPlace) {
            return new LoginScreenActivity(clientFactory);
        }
        if(place instanceof ProfileScreenPlace) {
            return new ProfileScreenActivity(clientFactory);
        }
        if(place instanceof UserEventsScreenPlace) {
            return new UserEventsScreenActivity(clientFactory);
        }

        return null;
    }
}