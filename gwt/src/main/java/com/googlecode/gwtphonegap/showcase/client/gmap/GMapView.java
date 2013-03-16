package com.googlecode.gwtphonegap.showcase.client.gmap;

import com.google.gwt.user.client.ui.IsWidget;


/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GMapView  extends IsWidget {
    public void setPresenter(Presenter presenter);

    public interface Presenter {

        public void onBackButtonPressed();

    }

    void refresh();

    void clearMapInfo();

    void setMapInfo(double latitude, double longitude, double accuracy);

}


