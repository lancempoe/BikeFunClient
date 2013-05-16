package com.googlecode.gwtphonegap.showcase.client;

import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.google.gwt.place.shared.PlaceController;
import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.overview.OverviewPlace;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.event.ShowMasterEvent;

public abstract class NavBaseActivity extends MGWTAbstractActivity {

    private final Injector injector = Injector.INSTANCE;
    private com.google.web.bindery.event.shared.EventBus eventBus;
    private PlaceController placeController;

    public NavBaseActivity() {
        ClientFactory clientFactory = injector.getClientFactory();
        this.eventBus = clientFactory.getEventBus();
        this.placeController = clientFactory.getPlaceController();
    }

    public void onBackButtonPressed() {
//        if (MGWT.getOsDetection().isTablet()) {
//            eventBus.fireEvent(new ShowMasterEvent("nav"));
//        } else {
            placeController.goTo(new HomeScreenPlace());
//        }
    }

}
