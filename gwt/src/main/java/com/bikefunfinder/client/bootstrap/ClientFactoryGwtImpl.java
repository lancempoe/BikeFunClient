package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.client.places.createscreen.CreateScreenActivity;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenActivity;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.gmap.GMapActivity;
import com.bikefunfinder.client.client.places.gmap.GMapDisplay;
import com.bikefunfinder.client.client.places.gmap.GMapDisplayImpl;
import com.bikefunfinder.client.client.places.gmaphomescreen.GMapHomeActivity;
import com.bikefunfinder.client.client.places.gmaphomescreen.GMapHomeDisplay;
import com.bikefunfinder.client.client.places.gmaphomescreen.GMapHomeDisplayGwtImpl;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenActivity;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenActivity;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenDisplay;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenActivity;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenDisplay;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenDisplayGwtImpl;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.event.OffLineEvent;
import com.googlecode.gwtphonegap.client.event.OnlineEvent;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class ClientFactoryGwtImpl implements ClientFactory {

    private PhoneGap phoneGap;
    private SimpleEventBus eventBus;
    private PlaceController placeController;
    private HomeScreenDisplay homeScreenDisplay;
    private CreateScreenDisplay createScreenDisplay;
    private ProfileScreenDisplay profileScreenDisplay;
    private SearchScreenDisplay searchScreenDisplay;
    private EventScreenDisplay eventScreenDisplay;
    private GMapDisplay gMapDisplay;
    private GMapHomeDisplay gMapHomeDisplay;
    private AppPlaceHistoryMapper historyHandler;
    private AccountDetailsProvider accountDetailsProvider;
    private boolean isDiviceOnline = true;

    public ClientFactoryGwtImpl() {
        this.eventBus = new SimpleEventBus();
        this.placeController = new PlaceController(eventBus);
    }

    @Override
    public void setPhoneGap(PhoneGap phoneGap) {
        this.phoneGap = phoneGap;
        accountDetailsProvider = new AccountDetailsProviderImpl(new LocalStorageWrapper(), phoneGap);
    }

    @Override
    public PhoneGap getPhoneGap() {
        return phoneGap;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public void deviceNetworkStateChanged(OnlineEvent onlineEvent) {
        isDiviceOnline = true;
    }

    @Override
    public void deviceNetworkStateChanged(OffLineEvent offLineEvent) {
        isDiviceOnline = false;
    }

    @Override
    public boolean isDeviceConnectedToNetwork() {
        return isDiviceOnline;
    }

    @Override
    public AccountDetailsProvider getAccountDetailsProvider() {
        return accountDetailsProvider;
    }

    @Override
    public void setPlaceHistoryMapper(AppPlaceHistoryMapper historyMapper) {
        this.historyHandler = historyMapper;
    }

    @Override
    public AppPlaceHistoryMapper getPlaceHistoryMapper() {
        return historyHandler;
    }

    @Override
    public Object getDisplay(MGWTAbstractActivity activity) {
        if(activity instanceof CreateScreenActivity) {
            return getCreateScreenDisplay();
        } else if(activity instanceof EventScreenActivity ) {
            return getEventScreenDisplay();
        }  else if(activity instanceof HomeScreenActivity) {
            return getHomeScreenDisplay();
        } else if(activity instanceof ProfileScreenActivity) {
            return getProfileScreenDisplay();
        } else if(activity instanceof SearchScreenActivity) {
            return getSearchScreenDisplay();
        } else if(activity instanceof GMapActivity) {
            return getGMapDisplay();
        } else if(activity instanceof GMapHomeActivity) {
            return getGMapHomeDisplay();
        }

        throw new RuntimeException("No fair! We need a real activity");
    }

    private CreateScreenDisplay getCreateScreenDisplay() {
        if (createScreenDisplay == null) {
            createScreenDisplay = new CreateScreenDisplayGwtImpl();
        }
        return createScreenDisplay;
    }

    private HomeScreenDisplay getHomeScreenDisplay() {
        if (homeScreenDisplay == null) {
            homeScreenDisplay = new HomeScreenDisplayGwtImpl();
        }
        return homeScreenDisplay;
    }

    private ProfileScreenDisplay getProfileScreenDisplay() {
        if (profileScreenDisplay == null) {
            profileScreenDisplay = new ProfileScreenDisplayGwtImpl();
        }
        return profileScreenDisplay;
    }

    private EventScreenDisplay getEventScreenDisplay() {
        if(eventScreenDisplay == null) {
            eventScreenDisplay = new EventScreenDisplayGwtImpl();
        }
        return eventScreenDisplay;
    }

    private SearchScreenDisplay getSearchScreenDisplay() {
        if (searchScreenDisplay == null) {
            searchScreenDisplay = new SearchScreenDisplayGwtImpl();
        }
        return searchScreenDisplay;
    }

    private GMapDisplay getGMapDisplay() {
        if (gMapDisplay == null) {
            gMapDisplay = new GMapDisplayImpl();
        }
        return gMapDisplay;
    }

    private GMapHomeDisplay getGMapHomeDisplay() {
        if (gMapHomeDisplay == null) {
            gMapHomeDisplay = new GMapHomeDisplayGwtImpl();
        }
        return gMapHomeDisplay;
    }
}