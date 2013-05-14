package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeolocationCallback;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.request.SearchByTimeOfDayForProfileRequest;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

import java.util.ArrayList;

public class ProfileScreenActivity extends MGWTAbstractActivity implements ProfileScreenDisplay.Presenter {

    private final ClientFactory<ProfileScreenDisplay> clientFactory;
    private String userName = "";
    private String userId = "";

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final ProfileScreenDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        panel.setWidget(display);
    }

    public ProfileScreenActivity(ClientFactory clientFactory, User user) {
        this.clientFactory = clientFactory;
        ProfileScreenDisplay display = this.clientFactory.getDisplay(this);
        setUserDisplayElements(user.getId(), user.getUserName());
        display.display(user);
    }

    public ProfileScreenActivity(ClientFactory clientFactory, AnonymousUser anonymousUser) {
        this.clientFactory = clientFactory;
        ProfileScreenDisplay display = this.clientFactory.getDisplay(this);
        setUserDisplayElements(anonymousUser.getId(), anonymousUser.getUserName());
        display.display(anonymousUser);
    }

    private void setUserDisplayElements(String id, String name) {
        this.userId = id;
        this.userName = name;
    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }

    @Override
    public void onShowMyRidesButton() {
        DeviceTools.getPhoneGeoLoc(clientFactory, new NonPhoneGapGeolocationCallback() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                fireRequestForsearchByTimeOfDayForProfile(geoLoc);
            }

            @Override
            public void onFailure(GeoLoc geoLoc) {
                fireRequestForsearchByTimeOfDayForProfile(geoLoc);
            }
        });
    }

    private void fireRequestForsearchByTimeOfDayForProfile(GeoLoc geoLoc) {
        SearchByTimeOfDayForProfileRequest.Callback callback = new SearchByTimeOfDayForProfileRequest.Callback() {
            @Override
            public void onResponseReceived(Root root) {
                clientFactory.getPlaceController().goTo(new HomeScreenPlace(root));
            }

            @Override
            public void onError() {
                Window.alert("Oops, your BFF will be back shortly.");
            }
        };
        SearchByTimeOfDayForProfileRequest.Builder request = new SearchByTimeOfDayForProfileRequest.Builder(callback);
        request.latitude(geoLoc).longitude(geoLoc).rideLeaderId(userId).send();
    }

//    private InAppBrowserDisplay display;
//    private PhoneGap phoneGap;
//    private InAppBrowser inAppBrowser;
//
//    public ProfileScreenActivity(ClientFactory clientFactory) {
//            this.clientFactory = clientFactory;
//
//            this.display = null;//clientFactory.getChildBrowserDisplay();
//            this.phoneGap = clientFactory.getPhoneGap();
//
//            inAppBrowser = this.phoneGap.getInAppBrowser();
////        inAppBrowser = GWT.create(InAppBrowserReferenceJsImpl.class);
//    }
//
//    @Override
//    public void start(AcceptsOneWidget panel, EventBus eventBus) {
//        final ProfileScreenDisplay display = clientFactory.getDisplay(this);
//        display.setPresenter(this);
//        panel.setWidget(display);
//    }
//
//    @Override
//    public void backButtonSelected() {
//        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
//    }

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