package com.bikefunfinder.client.bootstrap.tablet;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;


class TabletNavActivityMapper implements ActivityMapper {

    private final ActivityMapper activityMapperDelegate;

    public TabletNavActivityMapper(ActivityMapper activityMapperDelegate) {
        this.activityMapperDelegate = activityMapperDelegate;
    }

    @Override
    public Activity getActivity(Place place) {
        return activityMapperDelegate.getActivity(place);
    }
}