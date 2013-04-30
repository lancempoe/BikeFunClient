package com.bikefunfinder.client.bootstrap;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:57 AM
 */

import com.bikefunfinder.client.client.places.createscreen.CreateScreenActivity;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenActivity;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenActivity;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenActivity;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenActivity;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.bikefunfinder.client.client.places.gmap.*;

public class ActivityMapperDelegate implements ActivityMapper {

    private final ClientFactory clientFactory;

    public ActivityMapperDelegate(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
        if(place instanceof CreateScreenPlace) {
            return new CreateScreenActivity(clientFactory);
        } else if(place instanceof EventScreenPlace) {
            return new EventScreenActivity(clientFactory);
        } else if(place instanceof HomeScreenPlace) {
            return new HomeScreenActivity(clientFactory, ((HomeScreenPlace) place).getRoot());
        } else if(place instanceof ProfileScreenPlace) {
            return new ProfileScreenActivity(clientFactory);
        } else if(place instanceof SearchScreenPlace) {
            return new SearchScreenActivity(clientFactory, ((SearchScreenPlace) place).getQuery());
        } else if(place instanceof GMapPlace) {
            return new GMapActivity(clientFactory);
        }

        return null;
    }
}