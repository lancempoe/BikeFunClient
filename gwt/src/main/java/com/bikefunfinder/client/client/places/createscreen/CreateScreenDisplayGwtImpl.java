package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: lancepoehler
 * @created 4/6/13 2:22 AM
 */

import com.bikefunfinder.client.client.places.createscreen.shared.BikeRideCreateUtils;
import com.bikefunfinder.client.client.places.createscreen.shared.BikeRideCreateWidgets;
import com.bikefunfinder.client.client.places.createscreen.shared.BikeRideViewUtils;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.WidgetList;


public class CreateScreenDisplayGwtImpl  extends Composite implements CreateScreenDisplay {
    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);
    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, CreateScreenDisplayGwtImpl> {}

    private Presenter presenter;

    @UiField
    HTML userName;

    @UiField(provided = true)
    WidgetList widgetList;

    final BikeRideCreateWidgets bikeDisplayWidgets = new BikeRideCreateWidgets();

    public CreateScreenDisplayGwtImpl() {
        widgetList = BikeRideViewUtils.buildBikeViewWidgitList(bikeDisplayWidgets);

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    protected void onLoad() {
        setupTimePicker();
        setupDatePicker();
    }

    native void setupDatePicker() /*-{
        $wnd.setupDatePicker();
     }-*/;

    native void setupTimePicker() /*-{
       $wnd.setupTimePicker();
    }-*/;

    @Override
    public void displayFailedToCreateRideMessage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display(BikeRide bikeRide) {
        //To change body of implemented methods use File | Settings | File Templates.

    }

    @Override
    public void display(String userName) {
        this.userName.setText("Create Ride by: " + userName);
    }

    @Override
    public void resetState() {
        bikeDisplayWidgets.resetState();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("submitRide")
    protected void onSubmitRidePressed(TapEvent event) {
        if(presenter != null) {
            BikeRide br = BikeRideCreateUtils.createBikeRideFromState(bikeDisplayWidgets);

            presenter.onFormSelected(br);
        }
    }

    @UiHandler("backButton")
    protected void onBackButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.backButtonSelected();
        }
    }
}
