package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: lancepoehler
 * @created 4/6/13 2:22 AM
 */

import com.bikefunfinder.client.client.places.createscreen.shared.BikeRideCreateUtils;
import com.bikefunfinder.client.client.places.createscreen.shared.BikeRideCreateWidgets;
import com.bikefunfinder.client.client.places.createscreen.shared.BikeRideViewUtils;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.WidgetList;


public class CreateScreenDisplayGwtImpl  extends Composite implements CreateScreenDisplay {
    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);
    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, CreateScreenDisplayGwtImpl> {}

    private Presenter presenter;

    @UiField
    HTML userName;

    @UiField
    Button createRideButton;

    @UiField
    Button updateRideButton;

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
        bikeDisplayWidgets.setWidgetsFrom(bikeRide);
    }

    @Override
    public void display(String userName) {
        this.userName.setText("Create Ride by: " + userName);
    }

    @Override
    public void displaySubmitOrUpdateButton(boolean displaySubmitButton) {
            createRideButton.setVisible(displaySubmitButton);
            updateRideButton.setVisible(!displaySubmitButton);
    }

    @Override
    public void resetState() {
        bikeDisplayWidgets.resetState();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("createRideButton")
     protected void onCreateRidePressed(TapEvent event) {
        if(presenter != null) {
            BikeRide bikeRide = BikeRideCreateUtils.createBikeRideFromState(bikeDisplayWidgets);
            presenter.onCreateSelected(bikeRide);
        }
    }

    @UiHandler("updateRideButton")
    protected void onUpdateRidePressed(TapEvent event) {
        if(presenter != null) {
            BikeRide bikeRide = BikeRideCreateUtils.createBikeRideFromState(bikeDisplayWidgets);
            bikeRide.setId(bikeDisplayWidgets.bikeRideId.getText());
            Root root = GWT.create(Root.class);
            JsArray<BikeRide> bikeRides = JsArray.createArray().cast();
            bikeRides.push(bikeRide);
            root.setBikeRides(bikeRides);
            presenter.onUpdateSelected(root);
        }
    }

    @UiHandler("backButton")
    protected void onBackButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.backButtonSelected();
        }
    }
}
