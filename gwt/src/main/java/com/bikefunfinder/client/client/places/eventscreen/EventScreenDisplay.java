package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.user.client.ui.IsWidget;

public interface EventScreenDisplay extends IsWidget {

    public void display(BikeRide bikeRide);
    public void setPresenter(Presenter presenter);

    public interface Presenter {

        public void backButtonSelected();
    }
}