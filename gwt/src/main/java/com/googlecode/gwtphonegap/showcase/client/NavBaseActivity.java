package com.googlecode.gwtphonegap.showcase.client;

import com.google.gwt.place.shared.PlaceController;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.event.ShowMasterEvent;

public abstract class NavBaseActivity extends MGWTAbstractActivity {

	private com.google.web.bindery.event.shared.EventBus eventBus;
	private PlaceController placeController;

	public NavBaseActivity(ClientFactory clientFactory) {
		this.eventBus = clientFactory.getEventBus();
		this.placeController = clientFactory.getPlaceController();
	}

	public void onBackButtonPressed() {
		if (MGWT.getOsDetection().isTablet()) {
			eventBus.fireEvent(new ShowMasterEvent("nav"));
		} else {
			placeController.goTo(new OverviewPlace());
		}
	}

}
