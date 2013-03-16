package com.googlecode.gwtphonegap.showcase.bootstrap.phone;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.gwtphonegap.showcase.bootstrap.ClientFactory;
import com.googlecode.mgwt.mvp.client.AnimatableDisplay;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhoneFactory {
    public static List<IsWidget> createPhoneDisplay(ClientFactory clientFactory) {
        AnimatableDisplay display = GWT.create(AnimatableDisplay.class);

        PhoneActivityMapper appActivityMapper = new PhoneActivityMapper(clientFactory);

        PhoneAnimationMapper appAnimationMapper = new PhoneAnimationMapper();

        AnimatingActivityManager activityManager =
                new AnimatingActivityManager(appActivityMapper, appAnimationMapper, clientFactory
                        .getEventBus());

        activityManager.setDisplay(display);

        return Arrays.<IsWidget>asList(display);

    }
}
