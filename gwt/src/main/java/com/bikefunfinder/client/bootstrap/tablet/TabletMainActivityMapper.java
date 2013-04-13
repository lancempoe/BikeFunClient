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


class TabletMainActivityMapper implements ActivityMapper {

    private Place lastPlace;
    private Activity lastActivity;

    private final ActivityMapper activityMapperDelegate;

    public TabletMainActivityMapper(ActivityMapper activityMapperDelegate) {
        this.activityMapperDelegate = activityMapperDelegate;
    }

    @Override
    public Activity getActivity(Place place) {
        Activity activity = activityMapperDelegate.getActivity(place);
        lastPlace = place;
        lastActivity = activity;
        return activity;

    }
}
