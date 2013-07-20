package com.bikefunfinder.client.client.places.eventscreen.widgets;
/*
 * @author: tneuwerth
 * @created 5/8/13 9:55 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.bikefunfinder.client.shared.widgets.WidgetHelper;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class BikeRideViewWidgetsImpl implements BikeRideViewWidgets {

    public final Label bikeRideName = new HTML();
    public final Label rideLeaderName = new HTML();
    public final Label targetAudience = new HTML();
    public final Label details = new HTML();
    public final Label totalPeopleTrackingCount = new HTML();
    public final Label currentlyTracking = new HTML();
    public final Label formattedAddress = new HTML();
    public final Label distanceFromClient = new HTML();
    public final Label startDateAndTime = new HTML();

    public BikeRideViewWidgetsImpl() {
        bikeRideName.setWidth("100%");
//        rideLeaderName.setWidth("100%");
        targetAudience.setWidth("100%");
        details.setWidth("100%");
        totalPeopleTrackingCount.setWidth("100%");
        currentlyTracking.setWidth("100%");
        formattedAddress.setWidth("100%");
        distanceFromClient.setWidth("100%");
        startDateAndTime.setWidth("100%");
    }

    @Override
    public void setStateFrom(BikeRide bikeRide) {
        if(bikeRide==null) return; // failSafe but ugly;

        WidgetHelper.setSafeText(totalPeopleTrackingCount, String.valueOf(bikeRide.getTotalPeopleTrackingCount()));

        if(bikeRide.isCurrentlyTracking()) {
            WidgetHelper.setSafeText(currentlyTracking, "Yes");
        } else {
            WidgetHelper.setSafeText(currentlyTracking, "No");
        }

        //setSafeText(rideImage, bikeRide.getImagePath());
        if(bikeRide.getBikeRideName()!=null) {
            WidgetHelper.setSafeText(bikeRideName, bikeRide.getBikeRideName());
        }

        //We will put this back in once we have the ability to log in.  This is just causing confusion.
//        if(bikeRide.getRideLeaderName()!=null) {
//            WidgetHelper.setSafeText(rideLeaderName, bikeRide.getRideLeaderName());
//        }

        if(bikeRide.getTargetAudience()!=null) {
            for (ScreenConstants.TargetAudience target : ScreenConstants.TargetAudience.values()) {
                if (target.getDisplayName().equals(bikeRide.getTargetAudience())) {
                    targetAudience.setText(target.getDisplayName());
                    break;
                }
            }
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
            final String date = a.toString(ScreenConstants.DateFormatPrintPretty);
            final String time = a.toString(ScreenConstants.TimeFormatPrintPretty);
            return date + " at " + time;

        }
        return "";
    }

    @Override
    public void resetState() {

        totalPeopleTrackingCount.setText("");
        currentlyTracking.setText("No");
        formattedAddress.setText("");
        distanceFromClient.setText("");
        startDateAndTime.setText("");
    }

    @Override
    public Label getTotalPeopleTrackingCount() {
        return totalPeopleTrackingCount;
    }

    @Override
    public Label getCurrentlyTracking() {
        return currentlyTracking;
    }

    @Override
    public Label getFormattedAddress() {
        return formattedAddress;
    }

    @Override
    public Label getDistanceFromClient() {
        return distanceFromClient;
    }

    @Override
    public Label getStartDateAndTime() {
        return startDateAndTime;
    }

    @Override
    public Label getBikeRideName() {
        return bikeRideName;
    }

    @Override
    public Label getRideLeaderName() {
        return rideLeaderName;
    }

    @Override
    public Label getTargetAudience() {
        return targetAudience;
    }

    @Override
    public Label getDetails() {
        return details;
    }
}
