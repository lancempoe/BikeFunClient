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
import com.bikefunfinder.client.client.places.gmaphomescreen.GMapHomeActivity;
import com.bikefunfinder.client.client.places.gmaphomescreen.GMapHomePlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenActivity;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenActivity;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenActivity;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.bikefunfinder.client.shared.Tools.NativeUtilities;
import com.bikefunfinder.client.shared.Tools.NavigationHelper;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.request.management.AnnonymousUserCacheStrategy;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class ActivityMapperDelegate implements ActivityMapper {

    private static Place currentPlace;
    private static Activity lastActivity;

    @Override
    public Activity getActivity(Place place) {

        if(currentPlace !=null && (currentPlace.getClass().equals(place.getClass()))) {
            return lastActivity;
        }

        //this is somewhat of a hack. The idea being that in android if you bail from the maps page
        //via the back button on your phone, here we very much need to ensure that those timers and
        //refresh activities do not hang around. That would be disastrous!
        GMapActivity.cancelTimersAndAnyOutstandingActivities();

        currentPlace = place;

        if(place instanceof CreateScreenPlace) {
            final CreateScreenPlace createScreenPlace = (CreateScreenPlace) place;
            BikeRide bikeRide = createScreenPlace.getBikeRide();
            boolean isCopied = createScreenPlace.getIsCopied();
            lastActivity = new CreateScreenActivity(bikeRide, isCopied, createScreenPlace.getUser(), createScreenPlace.getAnonymousUser());
            if (isCopied || bikeRide == null) {
                NativeUtilities.trackPage("Create Screen");
            } else {
                NativeUtilities.trackPage("Update Screen");
            }

        } else if(place instanceof EventScreenPlace) {
            final EventScreenPlace eventScreenPlace = (EventScreenPlace) place;
            lastActivity = new EventScreenActivity(eventScreenPlace.getBikeRide(), null, AnnonymousUserCacheStrategy.INSTANCE.getCachedType());
            NativeUtilities.trackPage("Event Screen");

        } else if(place instanceof HomeScreenPlace) {
            final HomeScreenPlace homeScreenPlace = (HomeScreenPlace) place;
            lastActivity =  new HomeScreenActivity();
            resetNavigationStack();
            NavigationHelper.setHomeScreenPlace(homeScreenPlace);
            NativeUtilities.trackPage("Home Screen");

        } else if(place instanceof ProfileScreenPlace) {
            final ProfileScreenPlace profileScreenPlace = (ProfileScreenPlace) place;
            if (profileScreenPlace.getUser() != null) {
                lastActivity =  new ProfileScreenActivity(profileScreenPlace.getUser());
                NativeUtilities.trackPage("Profile Screen (User)");
            } else  {
                lastActivity =  new ProfileScreenActivity(profileScreenPlace.getAnonymousUser());
                NativeUtilities.trackPage("Profile Screen (Anonymous)");
            }

        } else if(place instanceof SearchScreenPlace) {
            final SearchScreenPlace searchScreenPlace = (SearchScreenPlace) place;
            lastActivity =  new SearchScreenActivity(searchScreenPlace.getQuery());
            NativeUtilities.trackPage("Search Screen");

        } else if(place instanceof GMapPlace) {
            final GMapPlace gMapPlace = (GMapPlace) place;
            lastActivity =  new GMapActivity(gMapPlace.getPageName(),
                                             gMapPlace.getBikeRide(), null, AnnonymousUserCacheStrategy.INSTANCE.getCachedType());
            NativeUtilities.trackPage("Event Map Screen");

        } else if(place instanceof GMapHomePlace) {
            final GMapHomePlace gMapHomePlace = (GMapHomePlace) place;
            lastActivity =  new GMapHomeActivity();
            resetNavigationStack();
            NavigationHelper.setGMapHomePlace(gMapHomePlace);
            NativeUtilities.trackPage("Here & Now Screen");

        }
        NavigationHelper.pushCurrentPlace(place);

        return lastActivity;
    }

    private void resetNavigationStack() {
        //clear out just in case
        if (!NavigationHelper.isNavigationStackEmpty()) {
            NavigationHelper.clearNavigationStack();
        }
    }
}