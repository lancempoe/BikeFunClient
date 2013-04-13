package com.bikefunfinder.client.client.places.searchsettings;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:31 AM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class SearchSettingsScreenActivity extends MGWTAbstractActivity implements SearchSettingsScreenDisplay.Presenter {
    private final ClientFactory clientFactory;

    public SearchSettingsScreenActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        final SearchSettingsScreenDisplay display = clientFactory.getSearchSettingsScreenDisplay();

        display.setPresenter(this);

        panel.setWidget(display);
    }

    @Override
    public void backButtonSelected() {
        clientFactory.getPlaceController().goTo(new HomeScreenPlace());
    }
}
