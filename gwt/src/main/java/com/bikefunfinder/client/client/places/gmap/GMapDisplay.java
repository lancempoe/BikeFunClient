package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.shared.model.BikeRide;
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

    public interface Presenter {
        public void onBackButtonPressed();
    }

    public void clearMapInfo();
    public void refresh();

    void setMapInfo(double latitude,
                    double longitude,
                    double accuracy,
                    List<BikeRide> list,
                    String cityNameText);

}


