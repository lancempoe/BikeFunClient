package com.bikefunfinder.client.client.places.createscreen.shared;
/*
 * @author: tneuwerth
 * @created 5/8/13 9:55 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.MIntegerBox;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase;

public class BikeRideViewWidgets extends BikeRideCreateWidgets {
    public final MIntegerBox totalPeopleTrackingCount = new MIntegerBox();
    public final MCheckBox currentlyTracking = new MCheckBox();
    public final MTextBox formattedAddress = new MTextBox();
    public final MTextBox distanceFromClient = new MTextBox();


    public BikeRideViewWidgets(boolean readOnly) {
        super();
        totalPeopleTrackingCount.setReadOnly(readOnly);
        currentlyTracking.setReadOnly(readOnly);
        bikeRideName.setReadOnly(readOnly);
        rideLeaderName.setReadOnly(readOnly);
        targetAudience.setEnabled(false); //false on purpose
        formattedAddress.setReadOnly(readOnly);
        distanceFromClient.setReadOnly(readOnly);
        startTime.setReadOnly(readOnly);
        details.setReadOnly(readOnly);
        details.setVisibleLines(10);
    }

    public void setWidgetsFrom(BikeRide bikeRide) {
        if(bikeRide==null) return; // failSafe but ugly;

        setSafeText(totalPeopleTrackingCount, bikeRide.getTotalPeopleTrackingCount());

        if(bikeRide.isCurrentlyTracking()!=null && !bikeRide.isCurrentlyTracking().isEmpty()) {
            currentlyTracking.setValue(Boolean.parseBoolean(bikeRide.isCurrentlyTracking()));
        }

        //setSafeText(rideImage, bikeRide.getImagePath());
        setSafeText(bikeRideName, bikeRide.getBikeRideName());
        setSafeText(rideLeaderName, bikeRide.getRideLeaderName());
        //setSafeText(targetAudience, bikeRide.getTargetAudience());


        if(bikeRide.getLocation()!=null) {
            setSafeText(formattedAddress, bikeRide.getLocation().getFormattedAddress());
        }

        final String distanceFromClientString = ScreenConstants.DISTANCE_FORMAT.format(bikeRide.getDistanceFromClient());
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

        setSafeText(details, bikeRide.getDetails());
    }

    private static void setSafeText(MValueBoxBase widget, String text) {
        if(text!=null && !text.isEmpty()) {
            widget.setText(text);
        }
    }

    public void resetState() {
        super.resetState();
        totalPeopleTrackingCount.setText("");
        currentlyTracking.setValue(false);
        formattedAddress.setText("");
        distanceFromClient.setText("");
    }
}
