package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GMapDisplay extends IsWidget {
    public void setPresenter(Presenter presenter);
    public void display(BikeRide bikeRide);
    public void displayPageName(String pageName);

    public interface Presenter {
        public void onBackButtonPressed();
        public void moreRideDetilsScreenRequested(BikeRide bikeRide);
        public String provideTokenHrefFor(BikeRide bikeRide);
    }

    public void clearMapInfo();
    public void refresh();

    void setMapInfo(GeoLoc phonesGpsLoc,
                    List<BikeRide> list,
                    String cityNameText);

    void setMapInfo(GeoLoc geoLoc,
                    BikeRide bikeRide,
                    String cityNameText);

}


