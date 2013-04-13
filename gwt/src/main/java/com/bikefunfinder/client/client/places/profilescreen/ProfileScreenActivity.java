package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.inappbrowser.InAppBrowser;
import com.googlecode.gwtphonegap.client.inappbrowser.InAppBrowserReference;
import com.googlecode.gwtphonegap.client.inappbrowser.LoadStopEvent;
import com.googlecode.gwtphonegap.client.inappbrowser.LoadStopHandler;
import com.googlecode.gwtphonegap.client.inappbrowser.js.InAppBrowserReferenceJsImpl;
import com.googlecode.gwtphonegap.showcase.client.inappbrowser.InAppBrowserDisplay;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class ProfileScreenActivity extends MGWTAbstractActivity implements ProfileScreenDisplay.Presenter {
    private final ClientFactory clientFactory;
    private InAppBrowserDisplay display;
    private PhoneGap phoneGap;
    private InAppBrowser inAppBrowser;

    public ProfileScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;

        this.display = null;//clientFactory.getChildBrowserDisplay();
        this.phoneGap = clientFactory.getPhoneGap();

        inAppBrowser = this.phoneGap.getInAppBrowser();
//        inAppBrowser = GWT.create(InAppBrowserReferenceJsImpl.class);
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final ProfileScreenDisplay display = clientFactory.getProfileScreenDisplay();

        display.setPresenter(this);

        panel.setWidget(display);
    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }

    @Override
    public void onLoginButtonPressed() {


        sayHelloInJava("wtf");


    }



    // A Java method using JSNI
    native void sayHelloInJava(String name) /*-{
        $wnd.sayHello(name); // $wnd is a JSNI synonym for 'window'
    }-*/;



}