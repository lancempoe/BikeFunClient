package com.bikefunfinder.client.client.places.usereventsscreen;

import com.bikefunfinder.client.client.places.usereventsscreen.UserEventsScreenDisplay.Presenter;
import com.bikefunfinder.client.shared.model.BikeRide;
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
    private Presenter presenter;
    private BikeRide bikeRide;
    public BikeRideClickHandler(Presenter presenter, BikeRide bikeRide)
    {
        this.presenter = presenter;
        this.bikeRide = bikeRide;
    }
    @Override
    public void onClick(ClickEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
        presenter.onRideClick(bikeRide);
    }
}
