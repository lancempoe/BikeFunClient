package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.Injector;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTStyle;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhoneGapDependentBootScrapperImpl extends PhoneGapDependentBootScrapper {

    Injector injector;
    public PhoneGapDependentBootScrapperImpl(PhoneGap phoneGapApi, Injector injector) {
        super(phoneGapApi);
        this.injector = injector;
    }

    public void phoneGapInitFailure() {
        Window.alert("can not load phonegap");

    }


    ClientFactory clientFactory;

    protected void phoneGapAvailable() {
        injector.getSimpleWidget().setPhoneGap(phoneGapApi);
        clientFactory = injector.getSimpleWidget();

        buildDisplay(clientFactory);

        MGWTSettings settings = MgwtSettingsFactory.buildMgwtSettings();
        MGWT.applySettings(settings);

        //ensure theme is injected
        MGWTStyle.getTheme().getMGWTClientBundle().getMainCss().ensureInjected();

        PlaceHistoryHandler historyHandler = createHistoryMapper(clientFactory);
        historyHandler.handleCurrentHistory();
    }

    private PlaceHistoryHandler createHistoryMapper(final ClientFactory clientFactory) {
        AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);

        clientFactory.setPlaceHistoryMapper(historyMapper);
        historyHandler.register(clientFactory.getPlaceController(),
                clientFactory.getEventBus(),
                new HomeScreenPlace());

        return historyHandler;

    }

    private void buildDisplay(ClientFactory clientFactory) {
        List<IsWidget> elementsToAdd = RootUiFactory.getUserInterfaceRootWidgets(clientFactory);
        for (IsWidget widget : elementsToAdd) {
            RootPanel.get().add(widget);
        }
    }
}
