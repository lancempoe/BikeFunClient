package com.bikefunfinder.client.client.places.createscreen.shared;
/*
 * @author: tneuwerth
 * @created 5/8/13 10:50 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Location;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;

import java.util.Date;

public class BikeRideCreateUtils {
    public static BikeRide createBikeRideFromState(BikeRideCreateWidgets rideCreateWidgets) throws IllegalArgumentException {
        try {
            return createBikeRideObjectFromInputs(rideCreateWidgets);
        } catch(IllegalArgumentException e) { //Should never happen again.
            throw new IllegalArgumentException("Cannot read the date and time input.");
        }
    }

    private static BikeRide createBikeRideObjectFromInputs(BikeRideCreateWidgets rideCreateWidgets) {
        BikeRide br = GWT.create(BikeRide.class);
        br.setBikeRideName(rideCreateWidgets.bikeRideName.getText());
        br.setTargetAudience(rideCreateWidgets.targetAudience.getValue(rideCreateWidgets.targetAudience.getSelectedIndex()));
        br.setLocation(createLocationFrom(rideCreateWidgets));
        br.setDetails(rideCreateWidgets.details.getValue());
        br.setTrackingAllowed(rideCreateWidgets.trackingAllowed.getValue());

        if (!rideCreateWidgets.startDate.getText().isEmpty() &&
            !rideCreateWidgets.startDate.getText().isEmpty()) {
            DateTimeFormat dtf = DateTimeFormat.getFormat(ScreenConstants.DateFormat +
                    " " +
                    ScreenConstants.TimeFormat);
            Date date = dtf.parse(rideCreateWidgets.startDate.getText() + " " +
                                  rideCreateWidgets.startTime.getText());
            br.setRideStartTime(date.getTime());
    }
        return br;
    }

    private static Location createLocationFrom(BikeRideCreateWidgets rideCreateWidgets) {
        Location location = GWT.create(Location.class);
        location.setCity(rideCreateWidgets.locationCity.getText());
        location.setState(rideCreateWidgets.locationState.getText());
        location.setStreetAddress(rideCreateWidgets.locationAddress.getText());
        return location;
    }

    native static String getDatePickerText() /*-{
        return $wnd.getDatePickerText();
    }-*/;

    native static String getTimePickerText() /*-{
        return $wnd.getTimePickerText();
    }-*/;
}