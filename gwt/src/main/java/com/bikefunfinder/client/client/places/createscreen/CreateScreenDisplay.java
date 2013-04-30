package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 4:22 PM
 */

import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.User;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.util.List;

public interface CreateScreenDisplay extends IsWidget {

    /**
     * This are the items that the display can provide
     */
    public void displayFailedToCreateRideMessage();
    public void display(BikeRide bikeRide);
    public void setUserName(String userName);

    /**
     * This is the contract for what the activity can provide
     */
    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public void onFormSelected(BikeRide br);
        public void backButtonSelected();
        public void onTimeSelected();
    }
}
