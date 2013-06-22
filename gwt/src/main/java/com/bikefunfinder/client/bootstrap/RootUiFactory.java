package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.bootstrap.phone.PhoneFactory;
import com.bikefunfinder.client.bootstrap.tablet.TabletFactory;
import com.bikefunfinder.client.shared.css.AppBundle;
import com.bikefunfinder.client.shared.css.FontAwesomeBundle;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.mgwt.ui.client.MGWT;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 1:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class RootUiFactory {
    public static List<IsWidget> getUserInterfaceRootWidgets() {
        StyleInjector.inject(AppBundle.INSTANCE.css().getText());
        StyleInjector.inject(FontAwesomeBundle.INSTANCE.css().getText());

        List<IsWidget> elementsToAdd;
        if (MGWT.getOsDetection().isTablet()) {

            // very nasty workaround because GWT does not correctly support
            // @media
            StyleInjector.inject(AppBundle.INSTANCE.css().getText());
            StyleInjector.inject(FontAwesomeBundle.INSTANCE.css().getText());

            elementsToAdd = TabletFactory.createTabletDisplay();
        } else {
            elementsToAdd = PhoneFactory.createPhoneDisplay();
        }
        return elementsToAdd;
    }
}