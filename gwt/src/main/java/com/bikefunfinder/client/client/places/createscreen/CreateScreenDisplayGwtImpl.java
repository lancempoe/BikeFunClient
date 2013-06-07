package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: lancepoehler
 * @created 4/6/13 2:22 AM
 */

import com.bikefunfinder.client.client.places.createscreen.widgets.BikeRideCreateUtils;
import com.bikefunfinder.client.client.places.createscreen.widgets.BikeRideCreateWidgets;
import com.bikefunfinder.client.client.places.createscreen.widgets.BikeRideCreateWidgetsImpl;
import com.bikefunfinder.client.client.places.eventscreen.widgets.BikeRideViewUtils;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;


public class CreateScreenDisplayGwtImpl  extends Composite implements CreateScreenDisplay {
    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);
    public interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, CreateScreenDisplayGwtImpl> {}

    private Presenter presenter;

    public CreateScreenDisplayGwtImpl() {
        widgetList = BikeRideViewUtils.buildBikeViewWidgitList(bikeDisplayWidgets);
        initWidget(uiBinder.createAndBindUi(this));
        scrollPanel.setUsePos(MGWT.getOsDetection().isAndroid());
    }

    @UiField
    HTML userName;

    @UiField
    ScrollPanel scrollPanel;

    @UiField
    Button createRideButton;

    @UiField
    Button updateRideButton;

    @UiField
    Button deleteRideButton;

    @UiField(provided = true)
    WidgetList widgetList;

    final BikeRideCreateWidgets bikeDisplayWidgets = new BikeRideCreateWidgetsImpl();

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
    public void populateWithExistingBikeRideDetails(BikeRide bikeRide) {
        bikeDisplayWidgets.setStateFrom(bikeRide);
    }

    @Override
    public void setUserNameOnDisplay(String userName) {
        this.userName.setText(userName);
    }

    @Override
    public void setVisibilityOfButtons(boolean existingEvent) {
        createRideButton.setVisible(!existingEvent);
        updateRideButton.setVisible(existingEvent);
        deleteRideButton.setVisible(existingEvent);
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

            Root root = GWT.create(Root.class);
            JsArray<BikeRide> bikeRides = JsArray.createArray().cast();
            bikeRides.push(bikeRide);
            root.setBikeRides(bikeRides);
            presenter.onUpdateSelected(root);
        }
    }

    @UiHandler("deleteRideButton")
    protected void onDeleteRidePressed(TapEvent event) {
        if(presenter != null) {
            BikeRide bikeRide = BikeRideCreateUtils.createBikeRideFromState(bikeDisplayWidgets);
            bikeRide.setId(bikeDisplayWidgets.getBikeRideId().getText());
            presenter.onDeleteSelected(bikeRide);
        }
    }

    @UiHandler("backButton")
    protected void onBackButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.backButtonSelected();
        }
    }
}