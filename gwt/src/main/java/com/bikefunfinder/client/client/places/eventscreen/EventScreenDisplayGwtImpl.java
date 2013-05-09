package com.bikefunfinder.client.client.places.eventscreen;

import com.bikefunfinder.client.client.places.createscreen.shared.BikeRideViewUtils;
import com.bikefunfinder.client.client.places.createscreen.shared.BikeRideViewWidgets;
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
import com.googlecode.mgwt.ui.client.widget.WidgetList;

public class EventScreenDisplayGwtImpl extends Composite implements EventScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);
    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, EventScreenDisplayGwtImpl> {}

    private Presenter presenter;

    @UiField(provided = true)
    WidgetList widgetList;

    @UiField
    HTML currentTrackings = new HTML();

    final BikeRideViewWidgets viewWidgets = new BikeRideViewWidgets(true);

    public EventScreenDisplayGwtImpl() {
        widgetList = BikeRideViewUtils.builBikeViewWidgitList(viewWidgets);
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayFailedToLoadRideMessage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display(BikeRide bikeRide) {
        viewWidgets.setWidgetsFrom(bikeRide);
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
}