package com.bikefunfinder.client.client.places.createscreen.widgets;
/*
 * @author: tneuwerth
 * @created 5/24/13 9:48 PM
 */

import com.google.gwt.user.client.ui.*;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.MTextArea;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public interface BikeRideCreateWidgets extends DisplaysBikeRideState {
    public MTextBox getBikeRideId();
    public MTextBox getBikeRideName();
    public MTextBox getLocationAddress();
    public MTextBox getLocationCity();
    public MTextBox getLocationState();
    public MCheckBox getTrackingAllowed();
    public ListBox getTargetAudience();
    public MTextBox getStartDate();
    public MTextBox getStartTime();
    public MTextArea getDetails();
}
