package com.googlecode.gwtphonegap.showcase.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class TabletNavActivityMapper implements ActivityMapper {

	private final ClientFactory clientFactory;
	private OverviewActivity overviewActivity;

	public TabletNavActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	private OverviewActivity getOverviewActivity() {
		if (overviewActivity == null) {
			overviewActivity = new OverviewActivity(clientFactory);
		}
		return overviewActivity;
	}

	@Override
	public Activity getActivity(Place place) {
		return getOverviewActivity();
	}
}
