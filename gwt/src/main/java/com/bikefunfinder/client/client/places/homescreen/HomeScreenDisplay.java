package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.CellGroup;

import java.util.List;

public interface HomeScreenDisplay extends IsWidget {

    public void display(List<BikeRide> list);
    public void display(String cityNameText);
    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public void onNewButton();
        public void onSearchButton();
        public void onLoginButton();
        public void onRideClick(BikeRide bikeRide);

        public void onTimeAndDayButton();
        public void onHereAndNowButton();

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

            if(this.getTimeDisplay()!=null && !this.getTimeDisplay().isEmpty()) {
                safeHtmlBuilder.appendEscaped(this.getTimeDisplay());
            }
            safeHtmlBuilder.appendHtmlConstant(", ");

            safeHtmlBuilder.appendEscaped(this.bikeRide.getTargetAudience());
            safeHtmlBuilder.appendHtmlConstant(", ");

            if(this.bikeRide.getBikeRideName() !=null && !this.bikeRide.getBikeRideName().isEmpty()) {
                safeHtmlBuilder.appendEscaped(this.bikeRide.getBikeRideName());
            }
            safeHtmlBuilder.appendHtmlConstant(", ");

            if(this.getDistance()!=null && !this.getDistance().isEmpty()) {
                safeHtmlBuilder.appendEscaped(this.getDistance());
            }
            safeHtmlBuilder.appendHtmlConstant(" Miles Away");

            return safeHtmlBuilder.toSafeHtml();
        }
    }
}
