package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Tracking;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.IsWidget;

public interface EventScreenDisplay extends IsWidget {

    /**
     * This are the items that the display can provide
     */
    public void displayFailedToLoadRideMessage();
    public void display(BikeRide bikeRide);
    public void displayTrackings(JsArray<Tracking> trackings);
    public void resetState();
    public void display(String eventNameText);
    public void displayEdit(boolean display);

    /**
     * This is the contract for what the activity can provide
     */
    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public void backButtonSelected();
        public void eventRideMapButtonSelected();
        public void editRideButtonSelected();
        public void copyRideButtonSelected();
    }
}