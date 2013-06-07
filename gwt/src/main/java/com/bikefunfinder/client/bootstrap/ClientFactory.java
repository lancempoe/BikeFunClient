package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenDisplay;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenDisplay;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.bikefunfinder.client.client.places.gmap.*;
import com.googlecode.gwtphonegap.client.event.OffLineEvent;
import com.googlecode.gwtphonegap.client.event.OnlineEvent;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public interface ClientFactory<DisplayType> {
    public PhoneGap getPhoneGap();
    public PlaceController getPlaceController();
    public EventBus getEventBus();

    public void setPhoneGap(PhoneGap phoneGap);
    public DisplayType getDisplay(MGWTAbstractActivity activity);

    public void deviceNetworkStateChanged(OnlineEvent onlineEvent);
    public void deviceNetworkStateChanged(OffLineEvent offLineEvent);
    public boolean isDeviceConnectedToNetwork();

    public void shutoffGmapTimerToBeSafe();

    public AccountDetailsProvider getAccountDetailsProvider();

    public void setPlaceHistoryMapper(AppPlaceHistoryMapper historyHandler);
    public AppPlaceHistoryMapper getPlaceHistoryMapper();

}