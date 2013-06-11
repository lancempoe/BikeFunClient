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
    public void setUserId(String userId);
    public void setIsTracking(boolean isTrackings);
    public void displayPageName(String pageName);
    public void resetForHereAndNow(GeoLoc centerGeoLoc);
    public void resetForEvent(GeoLoc centerGeoLoc);
    public void resetPolyLine();
    public void truffleShuffle();

    public interface Presenter {
        public void backButtonSelected();
        public void trackingRideButtonSelected(boolean tracking);
        public String provideTokenHrefFor(BikeRide bikeRide);
    }

    void setupMapDisplayForHereAndNow(GeoLoc phoneGpsLoc, List<BikeRide> list);
    void setupMapToDisplayBikeRide(GeoLoc phoneGpsLoc, BikeRide bikeRide, boolean reCenterReZoom);

}


