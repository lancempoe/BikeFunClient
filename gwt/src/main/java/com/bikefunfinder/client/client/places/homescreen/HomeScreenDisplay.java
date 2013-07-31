package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;

public interface HomeScreenDisplay extends IsWidget {

    public void display(List<BikeRide> list);
    public void setTitle(String cityNameText);
    public void setPresenter(Presenter presenter);

    public interface Presenter {
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
}
