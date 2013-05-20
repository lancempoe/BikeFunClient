package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.bootstrap.db.DBKeys;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenActivity;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenActivity;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenActivity;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenActivity;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenDisplay;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenActivity;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenDisplay;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenDisplayGwtImpl;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.User;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.bikefunfinder.client.shared.request.AnonymousRequest;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.storage.client.Storage;
import com.bikefunfinder.client.client.places.gmap.*;
import java.util.Date;


public class ClientFactoryGwtImpl implements ClientFactory {

    private PhoneGap phoneGap;
    private final LocalStorageWrapper storageInterface;
    private SimpleEventBus eventBus;
    private PlaceController placeController;
    private HomeScreenDisplay homeScreenDisplay;
    private CreateScreenDisplay createScreenDisplay;
    private ProfileScreenDisplay profileScreenDisplay;
    private SearchScreenDisplay searchScreenDisplay;
    private EventScreenDisplay eventScreenDisplay;
    private GMapDisplay gMapDisplay;
    private AppPlaceHistoryMapper historyHandler;

    public ClientFactoryGwtImpl() {
        this.storageInterface = new LocalStorageWrapper();
        this.eventBus = new SimpleEventBus();
        this.placeController = new PlaceController(eventBus);
    }

    public void setPhoneGap(PhoneGap phoneGap) {
        this.phoneGap = phoneGap;
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
        }

        throw new RuntimeException("No fair! We need a real activity");
    }

    public CreateScreenDisplay getCreateScreenDisplay() {
        if (createScreenDisplay == null) {
            createScreenDisplay = new CreateScreenDisplayGwtImpl();
        }
        return createScreenDisplay;
    }


    public HomeScreenDisplay getHomeScreenDisplay() {
        if (homeScreenDisplay == null) {
            homeScreenDisplay = new HomeScreenDisplayGwtImpl();
        }
        return homeScreenDisplay;
    }


    public ProfileScreenDisplay getProfileScreenDisplay() {
        if (profileScreenDisplay == null) {
            profileScreenDisplay = new ProfileScreenDisplayGwtImpl();
        }
        return profileScreenDisplay;
    }


    public EventScreenDisplay getEventScreenDisplay() {
        if(eventScreenDisplay == null) {
            eventScreenDisplay = new EventScreenDisplayGwtImpl();
        }
        return eventScreenDisplay;
    }


    public SearchScreenDisplay getSearchScreenDisplay() {
        if (searchScreenDisplay == null) {
            searchScreenDisplay = new SearchScreenDisplayGwtImpl();
        }
        return searchScreenDisplay;
    }


    public GMapDisplay getGMapDisplay() {
        if (gMapDisplay == null) {
            gMapDisplay = new GMapViewImpl();
        }
        return gMapDisplay;
    }

    /**
     * Pass a DBKey and get back the stored value if it exists.
     * @param dbKey
     * @return
     */
    @Override
    public String getStoredValue(String dbKey) {

        String jsonText = "";
        Storage storageInterface = this.storageInterface.getStorageInterfaceMyBeNull();
        if(storageInterface==null) {
            Window.alert("Storage interface is not supported (null)");
        } else {
            jsonText = storageInterface.getItem(dbKey);
        }
        return jsonText;
    }



    @Override
    public boolean setStoredValue(String dbKey, String value) {

        Storage storageInterface = this.storageInterface.getStorageInterfaceMyBeNull();
        if(storageInterface==null) {
            Window.alert("Storage interface is not supported (null)");
            return false;
        } else {
            storageInterface.setItem(dbKey, value);
        }
        return true;
    }

    @Override
    public void refreshUserAccount() {

        Storage storageInterface = this.storageInterface.getStorageInterfaceMyBeNull();
        if(storageInterface==null) {
            Window.alert("Storage interface is not supported (null)");
        } else {
            String jsonText = storageInterface.getItem(DBKeys.USER);
            if(jsonText!=null && !jsonText.isEmpty()) {
                //TODO WE NEED TO VALIDATE THAT THE USER IS STILL LOGGED IN PRIOR TO SENDING BACK, IF NOT SEND BACK ANONYMOUSUSER
                User user = Utils.castJsonTxtToJSOObject(jsonText);
            }  else {
                jsonText = storageInterface.getItem(DBKeys.ANONYMOUS_USER);
                if(jsonText==null || jsonText.isEmpty()) {
                    final Date now = new Date();
                    createAnonymousAccount(storageInterface,
                        Long.toString(now.getTime()),
                        phoneGap.getDevice().getUuid());
                } else {
                    AnonymousUser anonymousUser = Utils.castJsonTxtToJSOObject(jsonText);
                    createAnonymousAccount(storageInterface,
                        anonymousUser.getDeviceAccount().getKey(),
                        anonymousUser.getDeviceAccount().getDeviceUUID());
                }
            }
        }
    }

    private void createAnonymousAccount(final Storage storageInterface, final String key, final String uuid) {
        AnonymousRequest.Callback callback = new AnonymousRequest.Callback() {
            @Override
            public void onError() {
                Window.alert("Oops, your BFF will be back shortly (AnonymousRequest).");
            }

            @Override
            public void onResponseReceived(AnonymousUser anonymousUser) {
                final String jsonText = JSODescriber.toJSON(anonymousUser);
                storageInterface.setItem(DBKeys.ANONYMOUS_USER, jsonText);
            }
        };
        AnonymousRequest.Builder request = new AnonymousRequest.Builder(callback);
        request.key(key).uuid(uuid).send();
    }

    private String buildUserJsonTxt() {
        User user = GWT.create(User.class);
        user.setId("id");
        user.setDeviceAccount(null);
        user.setOAuth(null);

        return JSODescriber.toJSON(user);
    }

    @Override
    public void setPlaceHistoryMapper(AppPlaceHistoryMapper historyMapper) {
        this.historyHandler = historyHandler;
    }

    @Override
    public AppPlaceHistoryMapper getPlaceHistoryMapper() {
        return historyHandler;
    }
}