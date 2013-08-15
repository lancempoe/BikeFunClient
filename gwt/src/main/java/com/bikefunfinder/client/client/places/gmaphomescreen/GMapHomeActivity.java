package com.bikefunfinder.client.client.places.gmaphomescreen;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.Tools.*;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.helper.Extractor;
import com.bikefunfinder.client.shared.request.SearchByTimeOfDayRequest;
import com.bikefunfinder.client.shared.request.management.WebServiceResponseConsumer;
import com.bikefunfinder.client.shared.widgets.NavBaseActivity;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMapHomeActivity extends NavBaseActivity implements GMapHomeDisplay.Presenter {
    private final ClientFactory<GMapHomeDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private final GMapHomeDisplay display = clientFactory.getDisplay(this);
    private final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();

    final NotifyTimeAndDayCallback noOpNotifyTimeAndDayCallback = new NotifyTimeAndDayCallback() {
        @Override public void onError() { } // noOp
        @Override public void onResponseReceived() { } // noOp
    };

    public GMapHomeActivity() {
        Root root = ramObjectCache.getRoot();
        GeoLoc geoLoc = ramObjectCache.getCurrentPhoneGeoLoc();
        if (geoLoc == null) {
            onRefreshButton();
        } else if (root == null) {
            fireRequest(display, geoLoc, noOpNotifyTimeAndDayCallback);
        } else {
            setupDisplay(geoLoc, Extractor.getBikeRidesFrom(root), HomeHelper.getHomeTitle(root));
        }
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        display.setPresenter(this);
        panel.setWidget(display);
    }

    @Override
    public void onMainScreenToggleButton() {
        clientFactory.getPlaceController().goTo(NavigationHelper.getHomeScreenPlace());
    }

    @Override
    public void onShowHideToggleButton() {
        Root root = ramObjectCache.getRoot();
        GeoLoc geoLoc = ramObjectCache.getCurrentPhoneGeoLoc();
        display.display(geoLoc, Extractor.getBikeRidesFrom(root));
    }

    @Override
    public void onRefreshButton() {
        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                ramObjectCache.setCurrentPhoneGeoLoc(geoLoc);
                fireRequest(display, geoLoc, noOpNotifyTimeAndDayCallback); //Here & now Map
                noOpNotifyTimeAndDayCallback.onResponseReceived();
            }
        }));
    }

    @Override
    public String provideTokenHrefFor(BikeRide bikeRide) {
        if(bikeRide==null) {
            return "";
        }

        String hashToken = clientFactory.getPlaceHistoryMapper().getToken(new EventScreenPlace(bikeRide));
        UrlBuilder urlBuilder = Window.Location.createUrlBuilder().setHash(hashToken);
        return urlBuilder.buildString();
    }

    private void fireRequest(final GMapHomeDisplay display,
                             final GeoLoc geoLoc,
                             final NotifyTimeAndDayCallback notifyTimeAndDayCallback) {

        WebServiceResponseConsumer<Root> callback = new WebServiceResponseConsumer<Root>() {
            @Override
            public void onResponseReceived(Root root) {
                ramObjectCache.setRoot(root);
                setupDisplay(geoLoc, Extractor.getBikeRidesFrom(root), HomeHelper.getHomeTitle(root));
                notifyTimeAndDayCallback.onResponseReceived();
            }
        };
        SearchByTimeOfDayRequest.Builder request = new SearchByTimeOfDayRequest.Builder(callback);
        request.latitude(geoLoc).longitude(geoLoc).send();
    }

    /**
     * @param geoLoc
     * @param bikeRides
     * @param title
     */
    private void setupDisplay(GeoLoc geoLoc, List<BikeRide> bikeRides, String title) {
        display.display(geoLoc, bikeRides);
        display.setTitle(title);
        display.setMainSize(ramObjectCache.getMainScreenSize());
    }
}
