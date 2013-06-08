package com.bikefunfinder.client.client.places.searchscreen;
/*
 * @author: lancepoehler
 * @created 4/27/13 11:31 AM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.shared.Tools.DeviceTools;
import com.bikefunfinder.client.shared.Tools.NativeUtilities;
import com.bikefunfinder.client.shared.Tools.NonPhoneGapGeoLocCallback;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Query;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.request.SearchByParametersRequest;
import com.bikefunfinder.client.shared.request.management.GeoLocCacheStrategy;
import com.bikefunfinder.client.shared.request.management.WebServiceResponseConsumer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

import java.util.List;

public class SearchScreenActivity extends MGWTAbstractActivity implements SearchScreenDisplay.Presenter {
    private final ClientFactory<SearchScreenDisplay> clientFactory = Injector.INSTANCE.getClientFactory();
    private final Query query;

    private List<BikeRide> currentList;

    public SearchScreenActivity(Query query) {
        this.query = query;
        setupDisplay();

    }

    private void setupDisplay() {
        //TODO PUT IN CODE TO POPULATE THE QUERY SCREEN FROM PRIOR QUERY SAVED IN LOCAL DB.
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final SearchScreenDisplay display = clientFactory.getDisplay(this);
        display.setPresenter(this);
        panel.setWidget(display);

        NativeUtilities.trackPage("Search Screen");
    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }

    @Override
    public void searchRideButtonSelected(Query query) {

        final SearchByParametersRequest.Builder request = new SearchByParametersRequest.Builder(new WebServiceResponseConsumer<Root>() {
//            @Override
//            public void onError() {
//                //At this point the message has already been displayed to the user.
//            }

            @Override
            public void onResponseReceived(Root root) {
                clientFactory.getPlaceController().goTo(new HomeScreenPlace(root, HomeScreenPlace.UsageEnum.FilterRides));
            }
        });

        //Clear out defaults
        if (ScreenConstants.TargetAudienceLabel.equals(query.getTargetAudience())) {
            query.setTargetAudience("");
        }
        request.query(query);

        DeviceTools.requestPhoneGeoLoc(new NonPhoneGapGeoLocCallback(new NonPhoneGapGeoLocCallback.GeolocationHandler() {
            @Override
            public void onSuccess(GeoLoc geoLoc) {
                request.latitude(geoLoc).longitude(geoLoc).send();
            }
        }));
    }
}
