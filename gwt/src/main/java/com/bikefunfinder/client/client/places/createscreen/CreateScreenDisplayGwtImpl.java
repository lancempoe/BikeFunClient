package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: lancepoehler
 * @created 4/6/13 2:22 AM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;

public class CreateScreenDisplayGwtImpl  extends Composite implements CreateScreenDisplay {
    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, CreateScreenDisplayGwtImpl> {
    }

    @UiField
    DatePicker startTime;

    public CreateScreenDisplayGwtImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayFailedToCreateRideMessage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void displayResponse(BikeRide bikeRide) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private Presenter presenter;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiField
    FormPanel form;

    @UiHandler("submitRide")
    protected void onSubmitRidePressed(TapEvent event) {
        if(presenter != null) {
            //presenter.onFormSelected(new BikeRide());
        }
    }
    @UiHandler("backButton")
    protected void onBackButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.backButtonSelected();
        }
    }

}
