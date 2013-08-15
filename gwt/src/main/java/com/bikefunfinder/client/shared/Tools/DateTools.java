package com.bikefunfinder.client.shared.Tools;

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.core.client.JsDate;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 6/23/13
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateTools {

    public static boolean isOldRide(BikeRide bikeRide) {
        JsDate currentTime = JsDate.create();
        boolean isOldRide= false;
        if(currentTime.getTime() + ScreenConstants.TimeOffsetBeforeItsOld > bikeRide.getRideStartTime())
        {
            isOldRide = true;
        }
        return isOldRide;
    }

    public static boolean isLeavingRide(BikeRide bikeRide) {
        JsDate currentTime = JsDate.create();
        boolean isLeavingRide= false;
        if(!isOldRide(bikeRide) &&
          ((currentTime.getTime() + ScreenConstants.TimeOffsetBeforeItsLeaving) > bikeRide.getRideStartTime())) {
            //Warning Ride is leaving any moment
            isLeavingRide = true;
        }
        return isLeavingRide;
    }

    public static boolean isCurrentRide(BikeRide bikeRide) {
        JsDate currentTime = JsDate.create();
        boolean isCurrentRide= false;
        if(!isLeavingRide(bikeRide) &&
                ((currentTime.getTime() + ScreenConstants.TimeOffsetBeforeItsCurrent) > bikeRide.getRideStartTime())) {
            //Current Ride
            isCurrentRide = true;
        }
        return isCurrentRide;
    }

    public static boolean isFutureRide(BikeRide bikeRide) {
        JsDate currentTime = JsDate.create();
        boolean isFutureRide= false;
        if((currentTime.getTime() + ScreenConstants.TimeOffsetBeforeItsCurrent) <= bikeRide.getRideStartTime()) {
            //Current Ride
            isFutureRide = true;
        }
        return isFutureRide;
    }
}
