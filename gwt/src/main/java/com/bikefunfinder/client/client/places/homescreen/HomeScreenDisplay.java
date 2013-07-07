package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.client.places.fullmenuscreen.FullMenuScreenPresenter;
import com.bikefunfinder.client.shared.Tools.DateTools;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface HomeScreenDisplay extends IsWidget {

    public void display(List<BikeRide> list);
    public void setTitle(String cityNameText);
    public void setPresenter(Presenter presenter);

    public interface Presenter extends FullMenuScreenPresenter {
        public void onRideClick(BikeRide bikeRide);

        public void onHereAndNowButton();
        public void onExpiredRidesButton();
        public void onTimeAndDayButton();

        public void refreshTimeAndDayReq(NotifyTimeAndDayCallback callback);
        public interface NotifyTimeAndDayCallback {
            void onError();
            void onResponseReceived();
        }
    }

    public class Header {
        private final String name;

        public Header(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public class Content {
        private final BikeRide bikeRide;
        private final String timeDisplay;
        private final String distance;

        public Content(BikeRide bikeRide) {
            this.bikeRide = bikeRide;

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

        public BikeRide getBikeRide() {
            return bikeRide;
        }

        public SafeHtml getShortDescription() {
            SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
            safeHtmlBuilder.appendHtmlConstant("<div class=\"rideTime\">");


            if(this.getTimeDisplay()!=null && !this.getTimeDisplay().isEmpty()) {
                safeHtmlBuilder.appendHtmlConstant("<p class=\"time\">");
                safeHtmlBuilder.appendEscaped(this.getTimeDisplay());
                safeHtmlBuilder.appendHtmlConstant("</p>");
            }
            if(this.getDistance()!=null && !this.getDistance().isEmpty()) {
                safeHtmlBuilder.appendHtmlConstant("<p class=\"distance\">");
                safeHtmlBuilder.appendEscaped(this.getDistance());
                safeHtmlBuilder.appendHtmlConstant("<span class=\"distanceUnit\">mi. away</span>");
                safeHtmlBuilder.appendHtmlConstant("</p>");
            }

            safeHtmlBuilder.appendHtmlConstant("</div>");
            safeHtmlBuilder.appendHtmlConstant("<div class=\"descriptionBlock\">");
            safeHtmlBuilder.appendHtmlConstant("<h1 class=\"rideName\">");

            if(this.bikeRide.getBikeRideName() !=null && !this.bikeRide.getBikeRideName().isEmpty()) {
                safeHtmlBuilder.appendEscaped(this.bikeRide.getBikeRideName());
            }

            safeHtmlBuilder.appendHtmlConstant("</h1>");


//            if(this.bikeRide.getDetails() != null && !this.bikeRide.getDetails().isEmpty()) {
//                safeHtmlBuilder.appendHtmlConstant("<p class=\"description\">");
//                safeHtmlBuilder.appendEscaped(this.bikeRide.getDetails());
//                safeHtmlBuilder.appendHtmlConstant("</p>");
//            }
            if(this.bikeRide.getLocation().getStreetAddress() != null && !this.bikeRide.getLocation().getStreetAddress().isEmpty()) {
                safeHtmlBuilder.appendHtmlConstant("<p class=\"address\">");
                safeHtmlBuilder.appendEscaped(this.bikeRide.getLocation().getFormattedAddress() );
                safeHtmlBuilder.appendHtmlConstant("</p>");
            }

            safeHtmlBuilder.appendHtmlConstant("</div>");
//            safeHtmlBuilder.appendEscaped(this.bikeRide.getTargetAudience());
//            safeHtmlBuilder.appendHtmlConstant(", ");
//
//
//            safeHtmlBuilder.appendHtmlConstant(", ");
//

//            safeHtmlBuilder.appendHtmlConstant(" Miles Away");

            return safeHtmlBuilder.toSafeHtml();
        }

        public String getBikeRideListItemCssClass() {
            // Current Idea
            //-------------
            // - Old Rides Are Grey
            // - isTracking Green
            // - StartTime is between 2 hours before now is Green
            // - Everything in the Future is normal

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
