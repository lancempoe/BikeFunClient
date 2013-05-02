package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay.Presenter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


/**
 * Created with IntelliJ IDEA.
 * User: nathanfrost
 * Date: 4/14/13
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class BikeRideClickHandler implements ClickHandler {
    private final Presenter presenter;
    private final BikeRide bikeRide;
    private final HomeRefreshPullHandler homeRefreshPullHandler;

    public BikeRideClickHandler(Presenter presenter,
                                BikeRide bikeRide,
                                HomeRefreshPullHandler homeRefreshPullHandler) {
        this.presenter = presenter;
        this.bikeRide = bikeRide;
        this.homeRefreshPullHandler = homeRefreshPullHandler;
    }

    @Override
    public void onClick(ClickEvent event) {
        if(homeRefreshPullHandler == null ||
           homeRefreshPullHandler.isRefreshActionHappening()) {
            return;
        }

        presenter.onRideClick(bikeRide);

    }
}