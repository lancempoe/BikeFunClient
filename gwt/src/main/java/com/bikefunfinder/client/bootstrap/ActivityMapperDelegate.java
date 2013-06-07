package com.bikefunfinder.client.bootstrap;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:57 AM
 */

import com.bikefunfinder.client.client.places.createscreen.CreateScreenActivity;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenActivity;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.gmap.GMapActivity;
import com.bikefunfinder.client.client.places.gmap.GMapPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenActivity;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenActivity;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenActivity;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.shared.request.ratsnest.AnnonymousUserCacheStrategy;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class ActivityMapperDelegate implements ActivityMapper {

    private final ClientFactory clientFactory = Injector.INSTANCE.getClientFactory();
    private static Place lastSeen;
    private static Activity lastActivity;

    @Override
    public Activity getActivity(Place place) {
        if(lastSeen!=null && (lastSeen.getClass().equals(place.getClass()))) {
            return lastActivity;
        }


        clientFactory.shutoffGmapTimerToBeSafe();

        lastSeen = place;

        if(place instanceof CreateScreenPlace) {
            final CreateScreenPlace createScreenPlace = (CreateScreenPlace) place;
            lastActivity = new CreateScreenActivity(createScreenPlace.getBikeRide(), createScreenPlace.getUser(), createScreenPlace.getAnonymousUser());

        } else if(place instanceof EventScreenPlace) {
            final EventScreenPlace eventScreenPlace = (EventScreenPlace) place;
            lastActivity = new EventScreenActivity(eventScreenPlace.getBikeRide(),
                                                   eventScreenPlace.getWasConstructedById(), null, AnnonymousUserCacheStrategy.INSTANCE.getCachedType());

        } else if(place instanceof HomeScreenPlace) {
            final HomeScreenPlace homeScreenPlace = (HomeScreenPlace) place;
            lastActivity =  new HomeScreenActivity(homeScreenPlace.getRoot(), homeScreenPlace.getUsage());

        } else if(place instanceof ProfileScreenPlace) {
            final ProfileScreenPlace profileScreenPlace = (ProfileScreenPlace) place;
            if (profileScreenPlace.getUser() != null) {
                lastActivity =  new ProfileScreenActivity(profileScreenPlace.getUser());
            } else  {
                lastActivity =  new ProfileScreenActivity(profileScreenPlace.getAnonymousUser());
            }

        } else if(place instanceof SearchScreenPlace) {
            final SearchScreenPlace searchScreenPlace = (SearchScreenPlace) place;
            lastActivity =  new SearchScreenActivity(searchScreenPlace.getQuery());

        } else if(place instanceof GMapPlace) {
            final GMapPlace gMapPlace = (GMapPlace) place;
            lastActivity =  new GMapActivity(gMapPlace.getPageName(),
                                             gMapPlace.getBikeRide(), null, AnnonymousUserCacheStrategy.INSTANCE.getCachedType());

        }

        return lastActivity;
    }
}