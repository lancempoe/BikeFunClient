package com.bikefunfinder.client.client.places.createscreen.shared;
/*
 * @author: tneuwerth
 * @created 5/8/13 9:55 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
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
        if(bikeRide.getBikeRideName()!=null) {
            WidgetHelper.setSafeText(bikeRideName, bikeRide.getBikeRideName());
        }

        if(bikeRide.getRideLeaderName()!=null) {
            WidgetHelper.setSafeText(rideLeaderName, bikeRide.getRideLeaderName());
        }

        int targetAudienceOrderCount = 0;
        if(bikeRide.getTargetAudience()!=null) {
            for (ScreenConstants.TargetAudience target : ScreenConstants.TargetAudience.values()) {
                if (target.getDisplayName().equals(bikeRide.getTargetAudience())) {
                    targetAudienceOrderCount = target.getOrderCount();
                    break;
                }
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
            final String timeText = buildTimeString(bikeRide);
            WidgetHelper.setSafeText(startDateAndTime, timeText);
        } else {
            WidgetHelper.setSafeText(startDateAndTime, " ");

        }

        WidgetHelper.setSafeValue(details, bikeRide.getDetails());
    }

    private String buildTimeString(BikeRide bikeRide) {

        if(bikeRide.createJsDateWrapperRideStartTime()!=null) {

            JsDateWrapper a = bikeRide.createJsDateWrapperRideStartTime();
            final String s1 = a.toString(ScreenConstants.DateFormatPrintPretty);
            final String time = a.toString(ScreenConstants.TimeFormatPrintPretty);
            return time + " at " + time;

        }
        return "";
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
