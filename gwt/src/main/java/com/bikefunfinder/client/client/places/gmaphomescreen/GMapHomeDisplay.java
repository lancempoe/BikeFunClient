package com.bikefunfinder.client.client.places.gmaphomescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GMapHomeDisplay extends IsWidget {

    public void display(GeoLoc geoLoc, List<BikeRide> bikeRides);
    public void setTitle(String cityNameText);
    public void setMainSize(int mainSize);
    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public String provideTokenHrefFor(BikeRide bikeRide);

        public void onMainScreenToggleButton();
        public void onShowHideToggleButton();
        public void onRefreshButton();

        public interface NotifyTimeAndDayCallback {
            void onError();
            void onResponseReceived();
        }
    }
}


