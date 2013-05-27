package com.bikefunfinder.client.client.places.createscreen.widgets;
/*
 * @author: tneuwerth
 * @created 5/8/13 10:50 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Location;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;

import java.util.Date;

public class BikeRideCreateUtils {
    public static BikeRide createBikeRideFromState(BikeRideCreateWidgets rideCreateWidgets) throws IllegalArgumentException {
        try {
            return createBikeRideObjectFromInputs(rideCreateWidgets);
        } catch(IllegalArgumentException e) { //Should never happen again.
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot read the date and time input.", e);
        }
    }

    private static BikeRide createBikeRideObjectFromInputs(BikeRideCreateWidgets rideCreateWidgets) {
        BikeRide br = GWT.create(BikeRide.class);

        if(null != rideCreateWidgets.getBikeRideId()&&
           null != rideCreateWidgets.getBikeRideId().getText() &&
           !rideCreateWidgets.getBikeRideId().getText().isEmpty()) {

            br.setId(rideCreateWidgets.getBikeRideId().getText());
        }


        br.setBikeRideName(rideCreateWidgets.getBikeRideName().getText());
        br.setTargetAudience(rideCreateWidgets.getTargetAudience().getValue(
            rideCreateWidgets.getTargetAudience().getSelectedIndex())
        );
        br.setLocation(createLocationFrom(rideCreateWidgets));
        br.setDetails(rideCreateWidgets.getDetails().getText());
        br.setTrackingAllowed(rideCreateWidgets.getTrackingAllowed().getValue());

        if (!rideCreateWidgets.getStartDate().getText().isEmpty() &&
            !rideCreateWidgets.getStartTime().getText().isEmpty()) {

            final String combinedDateTimeText =
                    rideCreateWidgets.getStartDate().getText() + " " +
                    rideCreateWidgets.getStartTime().getText();

            DateTimeFormat dtf = DateTimeFormat.getFormat(ScreenConstants.gwtDateAndTimeCombined);
            Date date = dtf.parse(combinedDateTimeText);

            br.setRideStartTime(date.getTime());
    }
        return br;
    }

    private static Location createLocationFrom(BikeRideCreateWidgets rideCreateWidgets) {
        Location location = GWT.create(Location.class);
        location.setCity(rideCreateWidgets.getLocationCity().getText());
        location.setState(rideCreateWidgets.getLocationState().getText());
        location.setStreetAddress(rideCreateWidgets.getLocationAddress().getText());
        return location;
    }

    native static String getDatePickerText() /*-{
        return $wnd.getDatePickerText();
    }-*/;

    native static String getTimePickerText() /*-{
        return $wnd.getTimePickerText();
    }-*/;
}