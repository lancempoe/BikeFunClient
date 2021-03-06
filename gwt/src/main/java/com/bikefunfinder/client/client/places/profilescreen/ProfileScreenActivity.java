package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NativeUtilities;
import com.bikefunfinder.client.shared.Tools.NavigationHelper;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.request.SearchByTimeOfDayForProfileRequest;
import com.bikefunfinder.client.shared.request.VersionRequest;
import com.bikefunfinder.client.shared.request.management.WebServiceResponseConsumer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class ProfileScreenActivity extends MGWTAbstractActivity implements ProfileScreenDisplay.Presenter {

    private final ClientFactory<ProfileScreenDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();
    private String userName = "";
    private String clientVersion = "1.2.1";
    private ServiceVersion serviceVersion;
    private String userId = "";

    public ProfileScreenActivity(User user) {
        ProfileScreenDisplay display = this.clientFactory.getDisplay(this);
        setUserDisplayElements(user.getId(), user.getUserName());
        display.display(user);
        display.displayServiceVersion(serviceVersion);
        display.displayClientVersion(clientVersion);
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final ProfileScreenDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        panel.setWidget(display);
    }

    public ProfileScreenActivity(AnonymousUser anonymousUser) {
        ProfileScreenDisplay display = this.clientFactory.getDisplay(this);
        setUserDisplayElements(anonymousUser.getId(), anonymousUser.getUserName());
        display.display(anonymousUser);
        display.display(userName);
        display.displayServiceVersion(serviceVersion);
        display.displayClientVersion(clientVersion);
    }

    private void setUserDisplayElements(String id, String name) {
        this.userId = id;
        this.userName = name;
        if (ramObjectCache.getServiceVersion() == null) {
            fireRequestForServiceVersion();
        }
        this.serviceVersion = ramObjectCache.getServiceVersion();
    }

    @Override
    public void backButtonSelected() {
        NavigationHelper.goToPriorScreen(clientFactory.getPlaceController());
    }

    @Override
    public void onShowMyRidesButton() {

        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                fireRequestForsearchByTimeOfDayForProfile(geoLoc);
            }
        }));
    }

    private void fireRequestForsearchByTimeOfDayForProfile(GeoLoc geoLoc) {
        WebServiceResponseConsumer<Root> callback = new WebServiceResponseConsumer<Root>() {
            @Override
            public void onResponseReceived(Root root) {
                ramObjectCache.setRoot(root);
                ramObjectCache.setMainScreenPullDownLocked(true);
                NavigationHelper.goToPriorScreen(clientFactory.getPlaceController()); //can only come from one of the home pages
            }

//            @Override
//            public void onError() {
//                Dialogs.alert("Warning", "Oops, your BFF will be back shortly.", new Dialogs.AlertCallback() {
//                    @Override
//                    public void onButtonPressed() {
//                    }
//                });
//            }
        };
        SearchByTimeOfDayForProfileRequest.Builder request = new SearchByTimeOfDayForProfileRequest.Builder(callback);
        request.latitude(geoLoc).longitude(geoLoc).rideLeaderId(userId).send();
    }

    private void fireRequestForServiceVersion() {
        WebServiceResponseConsumer<ServiceVersion> callback = new WebServiceResponseConsumer<ServiceVersion>() {
            @Override
            public void onResponseReceived(ServiceVersion type) {
                ramObjectCache.setServiceVersion(type);
            }
        };
        VersionRequest.Builder version = new VersionRequest.Builder(callback);
        version.send();
    }

    @Override
    public void onLoginButtonPressed() {
//        askGoogleForOauth();
    }

//    @Override
//    public void onCheckGooleLoginButtonPressed() {
//        Window.alert("getGoogleAuthCode"+grabGoogleAuthCode());
//    }
//
//    // A Java method using JSNI
//    native String grabGoogleAuthCode() /*-{
//        return $wnd.getGoogleAuthCode();
//    }-*/;
//
//    // A Java method using JSNI
//    native void askGoogleForOauth() /*-{
//        $wnd.doGoogleForOauth(); // $wnd is a JSNI synonym for 'window'
//    }-*/;

}