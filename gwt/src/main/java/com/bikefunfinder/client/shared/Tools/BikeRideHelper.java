package com.bikefunfinder.client.shared.Tools;

import com.bikefunfinder.client.client.places.gmaphomescreen.GMapHomeDisplayGwtImpl;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 6/23/13
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BikeRideHelper {

    private static String OLD_RIDE = "oldRide";
    private static String LEAVING_RIDE = "leavingRide";
    private static String ACTIVE_RIDE = "activeRide";
    private static String CURRENT_RIDE = "currentRide";
    private static String FUTURE_RIDE = "futureRide";

    public static String getAddressToPrintForStart(BikeRide bikeRide) {
        return getAddressToPrint(bikeRide, false);
    }

    public static String getAddressToPrintForBike(BikeRide bikeRide) {
        return getAddressToPrint(bikeRide, true);
    }

    private static String getAddressToPrint(BikeRide bikeRide, boolean forBike) {
        String address = "";
        if (bikeRide.getDistanceTrackFromClient() != null) {
            if (forBike) {
                address = ScreenConstants.LIVE_TRACK;
            } else {
                address = ScreenConstants.STARTING_POINT_EVENT_MOVED;
            }
        } else {
            if(bikeRide.getLocation()!=null) {
                address = bikeRide.getLocation().getFormattedAddress();
            }
        }
        return address;
    }

    public static class Content {
        private final BikeRide bikeRide;
        private final String timeDisplay;
        private final String dateTimeDisplay;
        private final String distance;
        private final String distanceTrack;

        public Content(BikeRide bikeRide) {
            this.bikeRide = bikeRide;

            if(bikeRide.getDistanceTrackFromClient() != null) {
                this.distanceTrack = ScreenConstants.DISTANCE_FORMAT.format(
                        Double.parseDouble(bikeRide.getDistanceTrackFromClient()));
            } else {
                this.distanceTrack = "";
            }

            if(bikeRide.getDistanceFromClient()!=null) {
                this.distance = ScreenConstants.DISTANCE_FORMAT.format(
                        Double.parseDouble(bikeRide.getDistanceFromClient()));
            } else {
                this.distance ="";
            }

            JsDateWrapper bikeRideDate = bikeRide.createJsDateWrapperRideStartTime();
            this.timeDisplay = bikeRideDate.toString(ScreenConstants.TimeFormatPrintPretty);
            this.dateTimeDisplay = bikeRideDate.toString(ScreenConstants.DateFormatForGmapsWidget);
        }

        public String getTimeDisplay() {
            return timeDisplay;
        }

        public String getDateTimeDisplay() {
            return dateTimeDisplay;
        }

        public String getDistance() {
            return distance;
        }

        public String getDistanceTrack() {
            return distanceTrack;
        }

        public BikeRide getBikeRide() {
            return bikeRide;
        }

        public SafeHtml getShortDescriptionForStart(boolean forMap) {
            return getShortDescription(false, forMap);
        }

        public SafeHtml getShortDescriptionForBike(boolean forMap) {
            return getShortDescription(true, forMap);
        }

        public SafeHtml getShortDescription(boolean forMap) {
            SafeHtml safeHtml;
            if (distanceTrack != "") {
                safeHtml = getShortDescriptionForBike(forMap);
            } else {
                safeHtml = getShortDescriptionForStart(forMap);
            }
            return safeHtml;
        }

        private boolean hasTime() {
            return this.getTimeDisplay()!=null && !this.getTimeDisplay().isEmpty()
                    && this.getDateTimeDisplay()!=null && !this.getDateTimeDisplay().isEmpty();
        }

        private SafeHtml getShortDescription(boolean forBike, boolean forMap) {
            SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();

            //Build the left hand side of the bubble
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.DIV_RIDETIME);
            if(this.hasTime()) {
                safeHtmlBuilder.appendHtmlConstant(HtmlTools.P_TIME);
                safeHtmlBuilder.appendEscaped(getTimeDisplay());
                safeHtmlBuilder.appendHtmlConstant(HtmlTools.P_CLOSE_TAG);
            }
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.P_DISTANCE);
            if (forBike) {
                safeHtmlBuilder.appendEscaped(this.getDistanceTrack());
            } else {
                safeHtmlBuilder.appendEscaped(this.getDistance());
            }
            safeHtmlBuilder.appendHtmlConstant(" mi. away"); //TODO NATE: we use to wrap this in a span tag but right now it is falling out of the box so.
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.P_CLOSE_TAG);
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.DIV_CLOSE_TAG);

            //Build right side of bubble (top: Event Name) & (bottom: Current Event Address)
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.DIV_DESCRIPTION);
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.H1_RIDENAME);
            safeHtmlBuilder.appendEscaped(this.bikeRide.getBikeRideName());
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.H1_CLOSE_TAG);
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.P_ADDRESS);
            if(forMap) {
                safeHtmlBuilder.appendEscapedLines(this.getDateTimeDisplay() + "\n");
            }
            if (forBike) {
                safeHtmlBuilder.appendEscaped(BikeRideHelper.getAddressToPrintForBike(this.bikeRide));
            } else {
                safeHtmlBuilder.appendEscaped(BikeRideHelper.getAddressToPrintForStart(this.bikeRide));
            }
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.P_CLOSE_TAG);
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.DIV_CLOSE_TAG);

            return safeHtmlBuilder.toSafeHtml();
        }

        public String getBikeRideListItemCssClass() {

            //-------------
            // - Grey: Old Rides
            // - Green: isTracking
            // - Green: StartTime is between 1 hours before now
            // - White: Everything in the Future past 1 hour
            //-------------
            return stageOfBikeRide(this.bikeRide);
        }
    }

    public static boolean isHereAndNow(BikeRide bikeRide) {
        boolean isHereAndNow = false;
        String stageOfRide = stageOfBikeRide(bikeRide);
        if (!stageOfRide.equals(OLD_RIDE) && !stageOfRide.equals(FUTURE_RIDE)) {
            if ((bikeRide.isCurrentlyTracking() && Double.parseDouble(bikeRide.getDistanceTrackFromClient()) <= GMapHomeDisplayGwtImpl.MAP_HOME_RADIUS) ||
                    (!bikeRide.isCurrentlyTracking() && Double.parseDouble(bikeRide.getDistanceFromClient()) <= GMapHomeDisplayGwtImpl.MAP_HOME_RADIUS)) {
                isHereAndNow = true;
            }
        }
        return isHereAndNow;
    }

    public static boolean isOldRide(BikeRide bikeRide) {
        boolean isOldRide = false;
        String stageOfRide = stageOfBikeRide(bikeRide);
        if (stageOfRide.equals(OLD_RIDE)) {
            isOldRide = true;
        }
        return isOldRide;
    }


    private static String stageOfBikeRide(BikeRide bikeRide) {
        if(bikeRide.isCurrentlyTracking()) {
            return ACTIVE_RIDE;
        } else if (DateTools.isOldRide(bikeRide)) {
            return OLD_RIDE;
        } else if (DateTools.isLeavingRide(bikeRide)) {
            return LEAVING_RIDE;
        } else if (DateTools.isCurrentRide(bikeRide) ) {
            return CURRENT_RIDE;
        } else if (DateTools.isFutureRide(bikeRide)) {
            return FUTURE_RIDE;
        } else {
            Logger.getLogger("").log(Level.SEVERE, "Invalid bike ride start time!");
            return OLD_RIDE;
        }
    }

}
