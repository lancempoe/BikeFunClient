package com.googlecode.gwtphonegap.showcase.bootstrap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.showcase.client.AppPlaceHistoryMapper;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.ClientFactoryGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.OverviewPlace;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhoneGapDependentBootScrapperImpl extends PhoneGapDependentBootScrapper {

    public PhoneGapDependentBootScrapperImpl(PhoneGap phoneGapApi) {
        super(phoneGapApi);
    }

    public void phoneGapInitFailure() {
        Window.alert("can not load phonegap");

    }

    protected void phoneGapAvailable() {
        final ClientFactoryGwtImpl clientFactory = new ClientFactoryGwtImpl(phoneGapApi);
        buildDisplay(clientFactory);

        MGWTSettings settings = MgwtSettingsFactory.buildMgwtSettings();
        MGWT.applySettings(settings);

        PlaceHistoryHandler historyHandler = createHistoryMapper(clientFactory);
        historyHandler.handleCurrentHistory();
    }

    private PlaceHistoryHandler createHistoryMapper(final ClientFactoryGwtImpl clientFactory) {
        AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);

        historyHandler.register(clientFactory.getPlaceController(),
                clientFactory.getEventBus(),
                new OverviewPlace());

        return historyHandler;

    }

    private void buildDisplay(ClientFactory clientFactory) {
        List<IsWidget> elementsToAdd = RootUiFactory.getUserInterfaceRootWidgets(clientFactory);
        for (IsWidget widget : elementsToAdd) {
            RootPanel.get().add(widget);
        }
    }
}