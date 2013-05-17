package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 4:22 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.user.client.ui.IsWidget;

public interface CreateScreenDisplay extends IsWidget {

    /**
     * This are the items that the display can provide
     */
    public void resetState();
    public void displayFailedToCreateRideMessage();
    public void display(BikeRide bikeRide);
    public void display(String userName);
    public void setVisibilityOfButtons(boolean existingEvent);

    /**
     * This is the contract for what the activity can provide
     */
    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public void onCreateSelected(BikeRide br);
        public void onUpdateSelected(Root root);
        public void onDeleteSelected(BikeRide bikeRide);
        public void backButtonSelected();
        public void onTimeSelected();
    }
}
