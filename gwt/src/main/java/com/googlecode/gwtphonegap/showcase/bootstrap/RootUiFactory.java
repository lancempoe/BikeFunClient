package com.googlecode.gwtphonegap.showcase.bootstrap;

import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.css.AppBundle;
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
    public static List<IsWidget> getUserInterfaceRootWidgets(ClientFactory clientFactory) {
        List<IsWidget> elementsToAdd;
        if (MGWT.getOsDetection().isTablet()) {

            // very nasty workaround because GWT does not corretly support
            // @media
            StyleInjector.inject(AppBundle.INSTANCE.css().getText());

            elementsToAdd = TabletFactory.createTabletDisplay(clientFactory);
        } else {
            elementsToAdd = PhoneFactory.createPhoneDisplay(clientFactory);
        }
        return elementsToAdd;
    }
}