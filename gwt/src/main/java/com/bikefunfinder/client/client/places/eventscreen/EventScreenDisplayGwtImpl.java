package com.bikefunfinder.client.client.places.eventscreen;

import com.bikefunfinder.client.client.places.eventscreen.widgets.BikeRideViewUtils;
import com.bikefunfinder.client.client.places.eventscreen.widgets.BikeRideViewWidgets;
import com.bikefunfinder.client.client.places.eventscreen.widgets.BikeRideViewWidgetsImpl;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Tracking;
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

public class EventScreenDisplayGwtImpl extends Composite implements EventScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);
    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, EventScreenDisplayGwtImpl> {}

    private Presenter presenter;

    @UiField(provided = true)
    WidgetList widgetList;

    @UiField
    Button editRideButton;

    @UiField
    ScrollPanel scrollPanel;

    @UiField
    HTML currentTrackings = new HTML();

    final BikeRideViewWidgets bikeDisplayWidgets = new BikeRideViewWidgetsImpl();

    public EventScreenDisplayGwtImpl() {
        widgetList = BikeRideViewUtils.buildBikeViewWidgitList(bikeDisplayWidgets);
        initWidget(uiBinder.createAndBindUi(this));
        scrollPanel.setUsePos(MGWT.getOsDetection().isAndroid());
    }

    @Override
    public void displayFailedToLoadRideMessage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display(BikeRide bikeRide) {
        bikeDisplayWidgets.setStateFrom(bikeRide);
    }

    @Override
    public void displayEdit(boolean display) {
        editRideButton.setVisible(display);
    }

    @Override
    public void displayTrackings(JsArray<Tracking> trackings) {
        this.currentTrackings.setHTML(BikeRideViewUtils.buildOrderListHtmlForTracking(trackings));
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

    @UiHandler("eventRideMapButton")
    protected void onEventRideMapButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.eventRideMapButtonSelected();
        }
    }

    @UiHandler("editRideButton")
    protected void onEditRideButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.editRideButtonSelected();
        }
    }

    @Override
    public void resetState() {
        bikeDisplayWidgets.resetState();
    }

}