package com.bikefunfinder.client.client.places.createscreen.shared;
/*
 * @author: tneuwerth
 * @created 5/8/13 10:28 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.widgets.WidgetHelper;
import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.MTextArea;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class BikeRideCreateWidgets {
    public final MTextBox bikeRideId = new MTextBox(); //This is not used on the page... hidden field with values.. replace with injection code in time.
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
        for(ScreenConstants.TargetAudience target: ScreenConstants.TargetAudience.values()) {
            targetAudience.addItem(target.getDisplayName());
        }

        startTime.getElement().setId("timepicker");
        startDate.getElement().setId("datepicker");
    }

    public void setWidgetsFrom(BikeRide bikeRide) {
        if(bikeRide==null) return; // failSafe but ugly;

        WidgetHelper.setSafeText(bikeRideName, bikeRide.getBikeRideName());

        int targetAudienceOrderCount = 0;
        for (ScreenConstants.TargetAudience target : ScreenConstants.TargetAudience.values()) {
            if (target.getDisplayName().equals(bikeRide.getTargetAudience())) {
                targetAudienceOrderCount = target.getOrderCount();
                break;
            }
        }
        if(bikeRide.getTargetAudience()!=null && !bikeRide.getTargetAudience().isEmpty()) {
            this.targetAudience.setSelectedIndex(targetAudienceOrderCount);
        }

        WidgetHelper.setSafeValue(trackingAllowed, bikeRide.isTrackingAllowed());
        WidgetHelper.setSafeText(locationAddress, bikeRide.getLocation().getStreetAddress());
        WidgetHelper.setSafeText(locationCity, bikeRide.getLocation().getCity());
        WidgetHelper.setSafeText(locationState, bikeRide.getLocation().getState());
        WidgetHelper.setSafeValue(startDate, bikeRide.createJsDateWrapperRideStartTime().toString(ScreenConstants.DateFormat));
        WidgetHelper.setSafeValue(startTime, bikeRide.createJsDateWrapperRideStartTime().toString(ScreenConstants.TimeFormat));
        WidgetHelper.setSafeValue(details, bikeRide.getDetails());
        WidgetHelper.setSafeText(bikeRideId, bikeRide.getId());

    }

    public void resetState() {
        bikeRideName.setText("");
        rideLeaderName.setText("");
        locationAddress.setText("");
        locationCity.setText("");
        locationState.setText("");
        trackingAllowed.setValue(true);
        targetAudience.setSelectedIndex(0);
        startDate.setText("");
        startDate.setValue("");
        //startDate.getElement().setInnerText(""); //Due to the way this is set
        startTime.setText("");
        startTime.setValue("");
        //startTime.getElement().setInnerText(""); //Due to the way this is set
        details.setValue("");
        details.setVisibleLines(5);
        bikeRideId.setText("");
    }
}
