package com.bikefunfinder.client.bootstrap.phone;

import com.bikefunfinder.client.bootstrap.ActivityMapperDelegate;
import com.bikefunfinder.client.bootstrap.AnimationMapperDelegate;
import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.gin.Injector;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
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
    public static List<IsWidget> createPhoneDisplay() {
        AnimatableDisplay display = GWT.create(AnimatableDisplay.class);

        PhoneActivityMapper appActivityMapper = new PhoneActivityMapper(new ActivityMapperDelegate());

        PhoneAnimationMapper appAnimationMapper = new PhoneAnimationMapper(new AnimationMapperDelegate());

        AnimatingActivityManager activityManager =
            new AnimatingActivityManager(appActivityMapper,
                                         appAnimationMapper,
                    Injector.INSTANCE.getClientFactory().getEventBus());

        activityManager.setDisplay(display);

        return Arrays.<IsWidget>asList(display);

    }
}
