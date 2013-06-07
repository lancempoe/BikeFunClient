package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.request.AnonymousRequest;
import com.bikefunfinder.client.shared.request.ratsnest.WebServiceResponseConsumer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.event.*;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTStyle;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 1:17 PM
 */
public class PhoneGapDependentBootScrapperImpl extends PhoneGapDependentBootScrapper {

    public PhoneGapDependentBootScrapperImpl(PhoneGap phoneGapApi) {
        super(phoneGapApi);
    }

    public void phoneGapInitFailure() {
        Window.alert("can not load phonegap");
    }

    protected void phoneGapAvailable() {
        Injector.INSTANCE.getClientFactory().setPhoneGap(phoneGapApi);

        buildDisplay();

        setOfflineHandlerIfAvailable();
        setOnlineHandlerIfAvailable();

        MGWTSettings settings = MgwtSettingsFactory.buildMgwtSettings();
        MGWT.applySettings(settings);

        //ensure theme is injected
        MGWTStyle.getTheme().getMGWTClientBundle().getMainCss().ensureInjected();

        //Boot the UI after a call to anonymous user
        WebServiceResponseConsumer<AnonymousUser> callback = new WebServiceResponseConsumer<AnonymousUser>() {
            @Override
            public void onResponseReceived(AnonymousUser anonymousUser) {
                PlaceHistoryHandler historyHandler = createHistoryMapper();
                historyHandler.handleCurrentHistory();
            }
        };
        new AnonymousRequest.Builder(callback).send();
    }

    private void setOfflineHandlerIfAvailable() {
        HasOfflineHandler hasOfflineHandler = phoneGapApi.getEvent().getOffLineHandler();
        if(hasOfflineHandler!=null) {
            hasOfflineHandler.addOfflineHandler(new OffLineHandler() {
                @Override
                public void onOffLine(OffLineEvent event) {
                    Injector.INSTANCE.getClientFactory().deviceNetworkStateChanged(event);
                }
            });
        }
    }

    private void setOnlineHandlerIfAvailable() {
        HasOnlineHandler hasOnlineHandler = phoneGapApi.getEvent().getOnlineHandler();
        if(hasOnlineHandler!=null) {
            hasOnlineHandler.addOnlineHandler(new OnlineHandler() {
                @Override
                public void onOnlineEvent(OnlineEvent event) {
                    Injector.INSTANCE.getClientFactory().deviceNetworkStateChanged(event);
                }
            });
        }
    }

    private PlaceHistoryHandler createHistoryMapper() {
        AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);

        ClientFactory clientFactory = Injector.INSTANCE.getClientFactory();
        clientFactory.setPlaceHistoryMapper(historyMapper);
        historyHandler.register(clientFactory.getPlaceController(),
                clientFactory.getEventBus(),
                new HomeScreenPlace());

        return historyHandler;

    }

    private void buildDisplay() {
        List<IsWidget> elementsToAdd = RootUiFactory.getUserInterfaceRootWidgets();
        for (IsWidget widget : elementsToAdd) {
            RootPanel.get().add(widget);
        }
    }
}