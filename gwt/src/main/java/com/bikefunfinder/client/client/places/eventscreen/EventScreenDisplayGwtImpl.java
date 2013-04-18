package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;

public class EventScreenDisplayGwtImpl extends Composite implements EventScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, EventScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    @UiField Label rideName;
    @UiField Label address;
    @UiField Label startTime;
    @UiField Label details;

    public EventScreenDisplayGwtImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void display(BikeRide bikeRide) {
        rideName.setText(bikeRide.getBikeRideName());
        address.setText(bikeRide.getLocation().getCity());
        startTime.setText(bikeRide.createJsDateWrapperRideStartTime().toString("h:mm tt dddd, MMMM dd, yyyy"));
        details.setText(bikeRide.getJSON());
        Window.alert(bikeRide.getJSON());
    }


    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("backButton")
    protected void onBackButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.backButtonSelected();
        }
    }
}
