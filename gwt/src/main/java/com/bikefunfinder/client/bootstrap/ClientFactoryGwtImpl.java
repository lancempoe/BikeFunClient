package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.bootstrap.db.DBKeys;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenDisplay;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenDisplayGwtImpl;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenDisplay;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenDisplayGwtImpl;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
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
import com.googlecode.mgwt.storage.client.Storage;
import com.bikefunfinder.client.client.places.gmap.*;
import java.util.Date;


public class ClientFactoryGwtImpl implements ClientFactory {

    private final PhoneGap phoneGap;
    private final LocalStorageWrapper storageInterface;
    private SimpleEventBus eventBus;
    private PlaceController placeController;
    private HomeScreenDisplay homeScreenDisplay;
    private CreateScreenDisplay createScreenDisplay;
    private ProfileScreenDisplay profileScreenDisplay;
    private SearchScreenDisplay searchScreenDisplay;
    private EventScreenDisplay eventScreenDisplay;
    private GMapDisplay hereAndNowDisplay;

    public ClientFactoryGwtImpl(PhoneGap phoneGap) {
        this.phoneGap = phoneGap;
        this.storageInterface = new LocalStorageWrapper();
        this.eventBus = new SimpleEventBus();
        this.placeController = new PlaceController(eventBus);
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
    public CreateScreenDisplay getCreateScreenDisplay() {
        if (createScreenDisplay == null) {
            createScreenDisplay = new CreateScreenDisplayGwtImpl();
        }
        return createScreenDisplay;
    }

    @Override
    public HomeScreenDisplay getHomeScreenDisplay() {
        if (homeScreenDisplay == null) {
            homeScreenDisplay = new HomeScreenDisplayGwtImpl();
        }
        return homeScreenDisplay;
    }

    @Override
    public ProfileScreenDisplay getProfileScreenDisplay() {
        if (profileScreenDisplay == null) {
            profileScreenDisplay = new ProfileScreenDisplayGwtImpl();
        }
        return profileScreenDisplay;
    }

    @Override
    public EventScreenDisplay getEventScreenDisplay() {
        if(eventScreenDisplay == null) {
            eventScreenDisplay = new EventScreenDisplayGwtImpl();
        }
        return eventScreenDisplay;
    }

    @Override
    public SearchScreenDisplay getSearchScreenDisplay() {
        if (searchScreenDisplay == null) {
            searchScreenDisplay = new SearchScreenDisplayGwtImpl();
        }
        return searchScreenDisplay;
    }

    @Override
    public GMapDisplay getHereAndNowDisplay() {
        if (hereAndNowDisplay == null) {
            hereAndNowDisplay = new GMapViewImpl();
        }
        return hereAndNowDisplay;
    }

    @Override
    public void testLocalStorage() {

        Storage storageInterface = this.storageInterface.getStorageInterfaceMyBeNull();
        if(storageInterface==null) {
//            Window.alert("Storage interface is not supported (null)");
        } else {

            String userIdJson = storageInterface.getItem(DBKeys.ANONYMOUS_USER_ID);
            if(userIdJson==null || userIdJson.isEmpty()) {
                final Date now = new Date();
                fireSomeShit(storageInterface,
                             Long.toString(now.getTime()),
                             phoneGap.getDevice().getUuid());
            } else {
                final String jsonText = storageInterface.getItem(DBKeys.ANONYMOUS_USER_ID);
                User user = Utils.castJsonTxtToJSOObject(jsonText);

//                BikeRide brTest = Utils.castJsonTxtToJSOObject(jsonText);
//                Window.alert("pulled from DB (alerady existed)");
//                Window.alert("UserId: "+user.getId());
            }
        }
    }

    private void fireSomeShit(final Storage storageInterface, final String key, final String uuid) {
        AnonymousRequest.Callback callback = new AnonymousRequest.Callback() {
            @Override
            public void onError() {
                Window.alert("Oops, your BFF will be back shortly (anonReq).");
            }

            @Override
            public void onResponseReceived(AnonymousUser anonymousUser) {
//                Window.alert("anonymous user created id:" + anonymousUser.getId() );
                final String jsonText = JSODescriber.toJSON(anonymousUser);

//                Window.alert("storing");
                storageInterface.setItem(DBKeys.ANONYMOUS_USER_ID, jsonText);
//                Window.alert("stored");

            }
        };
        AnonymousRequest.Builder request = new AnonymousRequest.Builder(callback);
        request.key(key).uuid(uuid).send();
    }

    private String buildBikeRideJsonTxt() {
        BikeRide brTest = GWT.create(BikeRide.class);
        brTest.setBikeRideName("TestingLocalStorage");
        return JSODescriber.toJSON(brTest);
    }

    private String buildUserJsonTxt() {
        User user = GWT.create(User.class);
        user.setId("id");
        user.setDeviceAccount(null);
        user.setOAuth(null);

        return JSODescriber.toJSON(user);
    }

}