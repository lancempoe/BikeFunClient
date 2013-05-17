package com.bikefunfinder.client.client.places.createscreen.shared;
/*
 * @author: tneuwerth
 * @created 5/8/13 9:55 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.widgets.WidgetHelper;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class BikeRideViewWidgets extends BikeRideCreateWidgets {
    public final MTextBox totalPeopleTrackingCount = new MTextBox();
    public final MTextBox currentlyTracking = new MTextBox();
    public final MTextBox formattedAddress = new MTextBox();
    public final MTextBox distanceFromClient = new MTextBox();
    public final MTextBox startDateAndTime = new MTextBox();


    public BikeRideViewWidgets(boolean readOnly) {
        super();
        totalPeopleTrackingCount.setReadOnly(readOnly);
        currentlyTracking.setReadOnly(readOnly);
        bikeRideName.setReadOnly(readOnly);
        rideLeaderName.setReadOnly(readOnly);
        targetAudience.setEnabled(readOnly);
        formattedAddress.setReadOnly(readOnly);
        distanceFromClient.setReadOnly(readOnly);
        startDateAndTime.setReadOnly(readOnly);
        details.setReadOnly(readOnly);
        details.setVisibleLines(5);
    }

    public void setWidgetsFrom(BikeRide bikeRide) {
        if(bikeRide==null) return; // failSafe but ugly;

        WidgetHelper.setSafeText(totalPeopleTrackingCount, String.valueOf(bikeRide.getTotalPeopleTrackingCount()));

        if(bikeRide.isCurrentlyTracking()!=null &&
           !bikeRide.isCurrentlyTracking().isEmpty() &&
           bikeRide.getCurrentTrackings().equals("True")) {
            WidgetHelper.setSafeText(currentlyTracking, "Yes");
        } else {
            WidgetHelper.setSafeText(currentlyTracking, "No");
        }

        //setSafeText(rideImage, bikeRide.getImagePath());
        WidgetHelper.setSafeText(bikeRideName, bikeRide.getBikeRideName());
        WidgetHelper.setSafeText(rideLeaderName, bikeRide.getRideLeaderName());

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
            WidgetHelper.setSafeText(formattedAddress, bikeRide.getLocation().getFormattedAddress());
        }

        String distanceFromClientString = "";
        if(bikeRide.getDistanceFromClient()!=null) {
            distanceFromClientString = ScreenConstants.DISTANCE_FORMAT.format(
                    Double.parseDouble(bikeRide.getDistanceFromClient()));
        }

        if(distanceFromClient!=null) {
            WidgetHelper.setSafeText(distanceFromClient, distanceFromClientString + " Miles");
        }

        if(bikeRide.createJsDateWrapperRideStartTime()!=null) {
            final String timeText = bikeRide.createJsDateWrapperRideStartTime().toString(ScreenConstants.DateFormatPrintPretty) +
                                    " at " +
                                    bikeRide.createJsDateWrapperRideStartTime().toString(ScreenConstants.xJSDateOnlyTimeFormat);
            WidgetHelper.setSafeText(startDateAndTime, timeText);
        } else {
            WidgetHelper.setSafeText(startDateAndTime, " ");
        }

        WidgetHelper.setSafeValue(details, bikeRide.getDetails());
    }

    public void resetState() {
        super.resetState();
        totalPeopleTrackingCount.setText("");
        currentlyTracking.setText("No");
        formattedAddress.setText("");
        distanceFromClient.setText("");
        startDateAndTime.setText("");
    }
}
