package com.bikefunfinder.client.shared.Tools;

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.google.gwt.core.client.JsDate;
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
        }

        public String getTimeDisplay() {
            return timeDisplay;
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

        public SafeHtml getShortDescriptionForStart() {
            return getShortDescription(false);
        }

        public SafeHtml getShortDescriptionForBike() {
            return getShortDescription(true);
        }

        public SafeHtml getShortDescription() {
            SafeHtml safeHtml;
            if (distanceTrack != "") {
                safeHtml = getShortDescriptionForBike();
            } else {
                safeHtml = getShortDescriptionForStart();
            }
            return safeHtml;
        }

        private SafeHtml getShortDescription(boolean forBike) {
            SafeHtmlBuilder escapedData;
            SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
            StringBuilder htmlAsBuilder = new StringBuilder();

            //Build the left hand side of the bubble
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.DIV_RIDETIME);
            if(this.getTimeDisplay()!=null && !this.getTimeDisplay().isEmpty()) {
                safeHtmlBuilder.appendHtmlConstant(HtmlTools.P_TIME);
                safeHtmlBuilder.appendEscaped(this.getTimeDisplay());
                safeHtmlBuilder.appendHtmlConstant(HtmlTools.P_CLOSE_TAG);
            }
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.P_DISTANCE);
            if (forBike) {
                safeHtmlBuilder.appendEscaped(this.getDistanceTrack());
            } else {
                safeHtmlBuilder.appendEscaped(this.getDistance());
            }
            safeHtmlBuilder.appendHtmlConstant(" mi. away");
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.P_CLOSE_TAG);
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.DIV_CLOSE_TAG);

            //Build right side of bubble (top: Event Name) & (bottom: Current Event Address)
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.DIV_DESCRIPTION);
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.H1_RIDENAME);
            safeHtmlBuilder.appendEscaped(this.bikeRide.getBikeRideName());
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.H1_CLOSE_TAG);
            safeHtmlBuilder.appendHtmlConstant(HtmlTools.P_ADDRESS);
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

            if(bikeRide.isCurrentlyTracking()) {
                return "activeRide";
            }
            JsDate currentTime = JsDate.create();

            JsDate rideTime = JsDate.create(bikeRide.getRideStartTime());

            if (DateTools.isOldRide(this.bikeRide)) {
                return "oldRide";
            } else if (DateTools.isLeavingRide(this.bikeRide)) {
                return "leavingRide";
            } else if (DateTools.isCurrentRide(this.bikeRide) ) {
                return "currentRide";
            } else if (DateTools.isFutureRide(this.bikeRide)) {
                return "futureRide";
            } else {
                Logger.getLogger("").log(Level.SEVERE, "Invalid bike ride start time!");
                return "oldRide";
            }
        }
    }

}
