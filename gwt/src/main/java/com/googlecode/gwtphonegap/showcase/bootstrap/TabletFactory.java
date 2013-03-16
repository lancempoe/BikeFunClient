package com.googlecode.gwtphonegap.showcase.bootstrap;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.gwtphonegap.showcase.client.*;
import com.googlecode.mgwt.mvp.client.AnimatableDisplay;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.mvp.client.AnimationMapper;
import com.googlecode.mgwt.ui.client.dialog.TabletPortraitOverlay;
import com.googlecode.mgwt.ui.client.layout.MasterRegionHandler;
import com.googlecode.mgwt.ui.client.layout.OrientationRegionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
class TabletFactory {
    public static List<IsWidget> createTabletDisplay(ClientFactory clientFactory) {
        List<IsWidget> interfaceElements = new ArrayList<IsWidget>();

        SimplePanel navContainer = new SimplePanel();
        navContainer.getElement().setId("nav");
        navContainer.getElement().addClassName("landscapeonly");
        AnimatableDisplay navDisplay = GWT.create(AnimatableDisplay.class);

        final TabletPortraitOverlay tabletPortraitOverlay = new TabletPortraitOverlay();

        new OrientationRegionHandler(navContainer, tabletPortraitOverlay, navDisplay);
        new MasterRegionHandler(clientFactory.getEventBus(), "nav", tabletPortraitOverlay);

        ActivityMapper navActivityMapper = new TabletNavActivityMapper(clientFactory);

        AnimationMapper navAnimationMapper = new TabletNavAnimationMapper();

        AnimatingActivityManager navActivityManager =
                new AnimatingActivityManager(navActivityMapper, navAnimationMapper, clientFactory
                        .getEventBus());

        navActivityManager.setDisplay(navDisplay);

        interfaceElements.add(navContainer);

        SimplePanel mainContainer = new SimplePanel();
        mainContainer.getElement().setId("main");
        AnimatableDisplay mainDisplay = GWT.create(AnimatableDisplay.class);

        TabletMainActivityMapper tabletMainActivityMapper = new TabletMainActivityMapper(clientFactory);

        AnimationMapper tabletMainAnimationMapper = new TabletMainAnimationMapper();

        AnimatingActivityManager mainActivityManager =
                new AnimatingActivityManager(tabletMainActivityMapper, tabletMainAnimationMapper,
                        clientFactory.getEventBus());

        mainActivityManager.setDisplay(mainDisplay);
        mainContainer.setWidget(mainDisplay);
        interfaceElements.add(mainContainer);
        return interfaceElements;
    }

}
