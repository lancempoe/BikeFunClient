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
import com.bikefunfinder.client.gin.Injector;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.bikefunfinder.client.client.places.gmap.*;
import com.google.gwt.user.client.Window;

public class ActivityMapperDelegate implements ActivityMapper {

    private final ClientFactory clientFactory;
    private static Place lastSeen;
    private static Activity lastActivity;

    public ActivityMapperDelegate() {
        this.clientFactory = Injector.INSTANCE.getClientFactory();
        this.clientFactory.refreshUserAccount();
    }

    @Override
    public Activity getActivity(Place place) {
        if(lastSeen!=null && (lastSeen.getClass().equals(place.getClass()))) {
            return lastActivity;
        }

        lastSeen = place;

        if(place instanceof CreateScreenPlace) {
            lastActivity = new CreateScreenActivity(clientFactory, ((CreateScreenPlace) place).getBikeRide());
        } else if(place instanceof EventScreenPlace) {
            lastActivity = new EventScreenActivity(((EventScreenPlace) place).getBikeRide(),
                                                   ((EventScreenPlace) place).getWasConstructedById());
        } else if(place instanceof HomeScreenPlace) {
            lastActivity =  new HomeScreenActivity(clientFactory, ((HomeScreenPlace) place).getRoot());
        } else if(place instanceof ProfileScreenPlace) {
            if (((ProfileScreenPlace) place).getUser() != null) {
                lastActivity =  new ProfileScreenActivity(clientFactory, ((ProfileScreenPlace) place).getUser());
            } else  {
                lastActivity =  new ProfileScreenActivity(clientFactory, ((ProfileScreenPlace) place).getAnonymousUser());
            }
        } else if(place instanceof SearchScreenPlace) {
            lastActivity =  new SearchScreenActivity(clientFactory, ((SearchScreenPlace) place).getQuery());
        } else if(place instanceof GMapPlace) {
            lastActivity =  new GMapActivity(((GMapPlace)place).getPageName(),
                                             ((GMapPlace)place).getBikeRide());
        }

        return lastActivity;
    }
}