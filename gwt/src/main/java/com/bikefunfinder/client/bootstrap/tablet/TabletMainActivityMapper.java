package com.bikefunfinder.client.bootstrap.tablet;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.bikefunfinder.client.bootstrap.ClientFactory;


class TabletMainActivityMapper implements ActivityMapper {

    private final ClientFactory clientFactory;

    private Place lastPlace;
    private Activity lastActivity;

    public TabletMainActivityMapper(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;

    }

    @Override
    public Activity getActivity(Place place) {
        Activity activity = getActivity(lastPlace, place);
        lastPlace = place;
        lastActivity = activity;
        return activity;

    }

    private Activity getActivity(Place lastPlace, Place newPlace) {


        return null;
    }

}
