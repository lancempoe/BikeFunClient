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
import com.google.gwt.user.client.Window;

public class ActivityMapperDelegate implements ActivityMapper {

    private final ClientFactory clientFactory;
    private static Place lastSeen;
    private static Activity lastActivity;

    public ActivityMapperDelegate(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
        if(lastSeen!=null && (lastSeen.getClass().equals(place.getClass()))) {
            return lastActivity;
        }

        lastSeen=place;

        if(place instanceof CreateScreenPlace) {
            lastActivity = new CreateScreenActivity(clientFactory);
        } else if(place instanceof EventScreenPlace) {
            lastActivity = new EventScreenActivity(clientFactory, ((EventScreenPlace) place).getBikeRide());
            Window.alert("after eventscreenactivity!");
        } else if(place instanceof HomeScreenPlace) {
            lastActivity =  new HomeScreenActivity(clientFactory, ((HomeScreenPlace) place).getRoot());
        } else if(place instanceof ProfileScreenPlace) {
            lastActivity =  new ProfileScreenActivity(clientFactory);
        } else if(place instanceof SearchScreenPlace) {
            lastActivity =  new SearchScreenActivity(clientFactory, ((SearchScreenPlace) place).getQuery());
        } else if(place instanceof GMapPlace) {
            lastActivity =  new GMapActivity(clientFactory);
        }

        return lastActivity;
    }
}