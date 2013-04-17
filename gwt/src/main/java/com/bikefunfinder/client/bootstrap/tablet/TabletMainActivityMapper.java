package com.bikefunfinder.client.bootstrap.tablet;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;


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
