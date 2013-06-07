package com.bikefunfinder.client.bootstrap;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
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