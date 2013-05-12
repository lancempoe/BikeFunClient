package com.bikefunfinder.client.client.places.createscreen.shared;
/*
 * @author: tneuwerth
 * @created 5/8/13 9:55 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.user.client.ui.ListBox;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.MIntegerBox;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase;

public class BikeRideViewWidgets extends BikeRideCreateWidgets {
    public final MIntegerBox totalPeopleTrackingCount = new MIntegerBox();
    public final MTextBox currentlyTracking = new MTextBox();
    public final MTextBox formattedAddress = new MTextBox();
    public final MTextBox distanceFromClient = new MTextBox();


    public BikeRideViewWidgets(boolean readOnly) {
        super();
        totalPeopleTrackingCount.setReadOnly(readOnly);
        currentlyTracking.setReadOnly(readOnly);
        bikeRideName.setReadOnly(readOnly);
        rideLeaderName.setReadOnly(readOnly);
        targetAudience.setEnabled(readOnly);
        formattedAddress.setReadOnly(readOnly);
        distanceFromClient.setReadOnly(readOnly);
        startTime.setReadOnly(readOnly);
        details.setReadOnly(readOnly);
        details.setVisibleLines(10);
    }

    public void setWidgetsFrom(BikeRide bikeRide) {
        if(bikeRide==null) return; // failSafe but ugly;

        setSafeText(totalPeopleTrackingCount, bikeRide.getTotalPeopleTrackingCount());

        if(bikeRide.isCurrentlyTracking()!=null &&
           !bikeRide.isCurrentlyTracking().isEmpty() &&
           bikeRide.getCurrentTrackings().equals("True")) {
            setSafeText(currentlyTracking, "Yes");
        } else {
            setSafeText(currentlyTracking, "No");
        }

        //setSafeText(rideImage, bikeRide.getImagePath());
        setSafeText(bikeRideName, bikeRide.getBikeRideName());
        setSafeText(rideLeaderName, bikeRide.getRideLeaderName());

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

        if(bikeRide.getLocation()!=null) {
            setSafeText(formattedAddress, bikeRide.getLocation().getFormattedAddress());
        }

        String distanceFromClientString = "";
        if(bikeRide.getDistanceFromClient()!=null) {
            distanceFromClientString = ScreenConstants.DISTANCE_FORMAT.format(
                    Double.parseDouble(bikeRide.getDistanceFromClient()));
        }

        if(distanceFromClient!=null) {
            setSafeText(distanceFromClient, distanceFromClientString + " Miles");
        }

        if(bikeRide.createJsDateWrapperRideStartTime()!=null) {
            final String timeText = bikeRide.createJsDateWrapperRideStartTime().toString(ScreenConstants.DateFormatPrintPretty) +
                                    " at " +
                                    bikeRide.createJsDateWrapperRideStartTime().toString(ScreenConstants.TimeFormatPrintPretty);
            setSafeText(startTime, timeText);
        } else {
            setSafeText(startTime, " ");
        }

        setSafeValue(details, bikeRide.getDetails());
    }

    private static void setSafeText(MValueBoxBase widget, String text) {
        if(text!=null && !text.isEmpty()) {
            widget.setText(text);
        }
    }

    private static void setSafeValue(MValueBoxBase widget, String text) {
        if(text!=null && !text.isEmpty()) {
            widget.setValue(text);
        }
    }

    public void resetState() {
        super.resetState();
        totalPeopleTrackingCount.setText("");
        currentlyTracking.setText("No");
        formattedAddress.setText("");
        distanceFromClient.setText("");
    }
}
