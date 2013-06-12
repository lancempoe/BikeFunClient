package com.bikefunfinder.client.client.places.createscreen.widgets;
/*
 * @author: tneuwerth
 * @created 5/8/13 10:28 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.widgets.MobiInputBox;
import com.bikefunfinder.client.shared.widgets.WidgetHelper;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.MTextArea;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class BikeRideCreateWidgetsImpl implements BikeRideCreateWidgets {
    public final MTextBox bikeRideId = new MTextBox(); //This is not used on the page... hidden field with values.. replace with injection code in time.
    public final MTextBox bikeRideName = new MTextBox();
    public final MTextBox rideLeaderName = new MTextBox();
    public final MTextBox locationAddress = new MTextBox();
    public final MTextBox locationCity = new MTextBox();
    public final MTextBox locationState = new MTextBox();
    public final MCheckBox trackingAllowed = new MCheckBox();
    public final MListBox targetAudience = new MListBox();
    public final MobiInputBox startDate = new MobiInputBox("datepicker");
    public final MobiInputBox startTime = new MobiInputBox("timepicker");
    public final MTextArea details = new MTextArea();

    public BikeRideCreateWidgetsImpl() {
        for(ScreenConstants.TargetAudience target: ScreenConstants.TargetAudience.values()) {
            targetAudience.addItem(target.getDisplayName());
        }
    }

    @Override
    public MTextBox getBikeRideId() {
        return bikeRideId;
    }

    @Override
    public MTextBox getBikeRideName() {
        return bikeRideName;
    }

    @Override
    public MTextBox getLocationAddress() {
        return locationAddress;
    }

    @Override
    public MTextBox getLocationCity() {
        return locationCity;
    }

    @Override
    public MTextBox getLocationState() {
        return locationState;
    }

    @Override
    public MCheckBox getTrackingAllowed() {
        return trackingAllowed;
    }

    @Override
    public MListBox getTargetAudience() {
        return targetAudience;
    }

    @Override
    public MTextBox getStartDate() {
        return startDate;
    }

    @Override
    public MTextBox getStartTime() {
        return startTime;
    }

    @Override
    public MTextArea getDetails() {
        return details;
    }

    @Override
    public void setStateFrom(BikeRide bikeRide) {
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
        WidgetHelper.setSafeValue(startDate, bikeRide.createJsDateWrapperRideStartTime().toString(ScreenConstants.xJSDateOnlyDateFormat));
        WidgetHelper.setSafeValue(startTime, bikeRide.createJsDateWrapperRideStartTime().toString(ScreenConstants.xJSDateOnlyTimeFormat));
        WidgetHelper.setSafeValue(details, bikeRide.getDetails());
        WidgetHelper.setSafeText(bikeRideId, bikeRide.getId());

    }

    @Override
    public void resetState() {
        bikeRideName.setText("");
        rideLeaderName.setText("");
        locationAddress.setText("");
        locationCity.setText("");
        locationState.setText("");
        trackingAllowed.setValue(true);
        targetAudience.setSelectedIndex(0);
        startDate.setText("");
        startTime.setText("");
        details.setValue("");
        details.setVisibleLines(10);
        bikeRideId.setText("");
    }
}
