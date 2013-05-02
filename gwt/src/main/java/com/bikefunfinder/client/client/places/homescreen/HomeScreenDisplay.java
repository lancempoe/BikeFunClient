package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.user.client.ui.IsWidget;

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
}
