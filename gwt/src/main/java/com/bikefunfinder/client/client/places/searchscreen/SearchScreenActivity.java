package com.bikefunfinder.client.client.places.searchscreen;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:31 AM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Query;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.request.NewEventRequest;
import com.bikefunfinder.client.shared.request.SearchByParametersRequest;
import com.bikefunfinder.client.shared.request.SearchByTimeOfDayRequest;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchScreenActivity extends MGWTAbstractActivity implements SearchScreenDisplay.Presenter {
    private final ClientFactory clientFactory;

    private List<BikeRide> currentList;

    public SearchScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final SearchScreenDisplay display = clientFactory.getSearchScreenDisplay();

        display.setPresenter(this);

        panel.setWidget(display);
    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }

    @Override
    public void searchRideButtonSelected(Query query) {
        final SearchScreenDisplay display = clientFactory.getSearchScreenDisplay();

        SearchByParametersRequest.Builder request = new SearchByParametersRequest.Builder(new SearchByParametersRequest.Callback() {
            @Override
            public void onError() {
                Window.alert("Oops, your BFF will be back shortly.");
            }

            @Override
            public void onResponseReceived(Root root) {
                clientFactory.getPlaceController().goTo(new HomeScreenPlace(root));
            }
        });

        request.send(query);
    }
}
