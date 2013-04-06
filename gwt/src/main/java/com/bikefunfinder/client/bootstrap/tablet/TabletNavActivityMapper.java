package com.bikefunfinder.client.bootstrap.tablet;

import com.bikefunfinder.client.client.places.homescreen.HomeScreenActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.bikefunfinder.client.bootstrap.ClientFactory;


class TabletNavActivityMapper implements ActivityMapper {

    private final ClientFactory clientFactory;
    private HomeScreenActivity overviewActivity;

    public TabletNavActivityMapper(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    private HomeScreenActivity getOverviewActivity() {
        if (overviewActivity == null) {
            overviewActivity = new HomeScreenActivity(clientFactory);
        }
        return overviewActivity;
    }

    @Override
    public Activity getActivity(Place place) {
        return getOverviewActivity();
    }
}
