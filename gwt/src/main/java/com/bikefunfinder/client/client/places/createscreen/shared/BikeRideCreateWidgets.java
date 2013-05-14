package com.bikefunfinder.client.client.places.createscreen.shared;
/*
 * @author: tneuwerth
 * @created 5/8/13 10:28 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.MTextArea;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class BikeRideCreateWidgets {
    public final MTextBox bikeRideName = new MTextBox();
    public final MTextBox rideLeaderName = new MTextBox();
    public final MTextBox locationAddress = new MTextBox();
    public final MTextBox locationCity = new MTextBox();
    public final MTextBox locationState = new MTextBox();
    public final MCheckBox trackingAllowed = new MCheckBox();
    public final MListBox targetAudience = new MListBox();
    public final MTextBox startDate = new MTextBox();
    public final MTextBox startTime = new MTextBox();
    public final MTextArea details = new MTextArea();

    public BikeRideCreateWidgets() {
        for(ScreenConstants.TargetAudience enumVal: ScreenConstants.TargetAudience.values()) {
            targetAudience.addItem(enumVal.getDisplayName());
        }

        startTime.getElement().setId("timepicker");
        startDate.getElement().setId("datepicker");

    }

    public void resetState() {
        bikeRideName.setText("");
        rideLeaderName.setText("");
        locationAddress.setText("");
        locationCity.setText("");
        locationState.setText("");
        trackingAllowed.setValue(false);
        targetAudience.setSelectedIndex(0);
        startDate.setText("");
        startTime.setText("");
        details.setText("");
        details.setVisibleLines(5);
    }
}
