package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
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
        private final String rideName;
        private final String timeDisplay;
        private final String details;

        public Content(BikeRide bikeRide) {
            this.rideName = bikeRide.getBikeRideName();
            this.details = bikeRide.getDetails();
            JsDateWrapper bikeRideDate = bikeRide.createJsDateWrapperRideStartTime();
            this.timeDisplay = bikeRideDate.toString("h:mm tt");
        }

        public String getRideName() {
            return rideName;
        }

        public String getTimeDisplay() {
            return timeDisplay;
        }

        public String getDetails() {
            return details;
        }
    }
}
