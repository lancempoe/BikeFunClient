package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NativeUtilities;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.request.SearchByTimeOfDayForProfileRequest;
import com.bikefunfinder.client.shared.request.ratsnest.CacheStrategy;
import com.bikefunfinder.client.shared.request.ratsnest.GeoLocCacheStrategy;
import com.bikefunfinder.client.shared.request.ratsnest.WebServiceResponseConsumer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.MGWT;

public class ProfileScreenActivity extends MGWTAbstractActivity implements ProfileScreenDisplay.Presenter {

    private final ClientFactory<ProfileScreenDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private String userName = "";
    private String userId = "";

    public ProfileScreenActivity(User user) {
        ProfileScreenDisplay display = this.clientFactory.getDisplay(this);
        setUserDisplayElements(user.getId(), user.getUserName());
        display.display(user);

        NativeUtilities.trackPage("Profile Screen (User)");
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

        NativeUtilities.trackPage("Profile Screen (Anonymous)");
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

        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                fireRequestForsearchByTimeOfDayForProfile(geoLoc);
            }
        }, GeoLocCacheStrategy.INSTANCE));
    }

    private void fireRequestForsearchByTimeOfDayForProfile(GeoLoc geoLoc) {
        WebServiceResponseConsumer<Root> callback = new WebServiceResponseConsumer<Root>() {
            @Override
            public void onResponseReceived(Root root) {
                clientFactory.getPlaceController().goTo(new HomeScreenPlace(root, HomeScreenPlace.UsageEnum.ShowMyRides));
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