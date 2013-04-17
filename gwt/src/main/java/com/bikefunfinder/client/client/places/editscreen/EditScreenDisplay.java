package com.bikefunfinder.client.client.places.editscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 4:22 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.user.client.ui.IsWidget;

public interface EditScreenDisplay extends IsWidget {

    /**
     * This are the items that the display can provide
     */
    public void displayFailedToEditRideMessage();
    public void displayResponse();

    /**
     * This is the contract for what the activity can provide
     */
    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public void onFormSelected(BikeRide br);
        public void backButtonSelected();
    }
}
