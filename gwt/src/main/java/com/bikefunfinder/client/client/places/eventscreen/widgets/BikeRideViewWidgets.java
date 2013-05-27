package com.bikefunfinder.client.client.places.eventscreen.widgets;
/*
 * @author: tneuwerth
 * @created 5/24/13 9:43 PM
 */

import com.bikefunfinder.client.client.places.createscreen.widgets.DisplaysBikeRideState;
import com.google.gwt.user.client.ui.Label;

public interface BikeRideViewWidgets extends DisplaysBikeRideState {

    public Label getBikeRideName();
    public Label getRideLeaderName();
    public Label getTargetAudience();
    public Label getTotalPeopleTrackingCount();
    public Label getCurrentlyTracking();
    public Label getFormattedAddress();
    public Label getDistanceFromClient();
    public Label getStartDateAndTime();
    public Label getDetails();
}
