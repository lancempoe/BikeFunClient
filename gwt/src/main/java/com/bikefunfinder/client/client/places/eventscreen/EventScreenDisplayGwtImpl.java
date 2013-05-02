package com.bikefunfinder.client.client.places.eventscreen;
/*
 * @author: lancePoehler
 * @created 4/5/13 11:11 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Tracking;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.*;

import java.util.ArrayList;
import java.util.List;

public class EventScreenDisplayGwtImpl extends Composite implements EventScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, EventScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    @UiField(provided = true) WidgetList widgetList;
    FormListEntry formListEntry;

    MIntegerBox totalPeopleTrackingCount = new MIntegerBox();
    MCheckBox currentlyTracking = new MCheckBox();
    MTextBox rideImage = new MTextBox();
    MTextBox bikeRideName = new MTextBox();
    MTextBox rideLeaderName = new MTextBox();
    MTextBox targetAudience = new MTextBox();
    MTextBox formattedAddress = new MTextBox();
    MTextBox distanceFromClient = new MTextBox();
    MTextBox rideStartTime = new MTextBox();
    MTextArea details = new MTextArea();

    @UiField
    HTML currentTrackings;

    public EventScreenDisplayGwtImpl() {

        //Build the form.
        widgetList = new WidgetList();

        totalPeopleTrackingCount.setReadOnly(true);
        currentlyTracking.setReadOnly(true);
        rideImage.setReadOnly(true);
        bikeRideName.setReadOnly(true);
        rideLeaderName.setReadOnly(true);
        targetAudience.setReadOnly(true);
        formattedAddress.setReadOnly(true);
        distanceFromClient.setReadOnly(true);
        rideStartTime.setReadOnly(true);
        details.setReadOnly(true);

        formListEntry = new FormListEntry();
        formListEntry.setText("Total People Tracking:");
        formListEntry.add(totalPeopleTrackingCount);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Someone is tracking:");
        formListEntry.add(currentlyTracking);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Ride Image:");
        formListEntry.add(rideImage);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Bike Ride Name:");
        formListEntry.add(bikeRideName);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Ride Leader Name:");
        formListEntry.add(rideLeaderName);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Target Audience:");
        formListEntry.add(targetAudience);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Starting Address:");
        formListEntry.add(formattedAddress);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Starting Address:");
        formListEntry.add(formattedAddress);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Distance from the Start:");
        formListEntry.add(distanceFromClient);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Start Time:");
        formListEntry.add(rideStartTime);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Ride Details:");
        details.setVisibleLines(10);
        formListEntry.add(details);
        widgetList.add(formListEntry);

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayFailedToLoadRideMessage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display(BikeRide bikeRide) {
        totalPeopleTrackingCount.setText(bikeRide.getTotalPeopleTrackingCount());
        currentlyTracking.setValue(Boolean.parseBoolean(bikeRide.isCurrentlyTracking()));
        rideImage.setText(bikeRide.getImagePath());
        bikeRideName.setText(bikeRide.getBikeRideName());
        rideLeaderName.setText(bikeRide.getRideLeaderName());
        targetAudience.setText(bikeRide.getTargetAudience());
        formattedAddress.setText(bikeRide.getLocation().getFormattedAddress());
        distanceFromClient.setText(bikeRide.getDistanceFromClient() + " Miles");
        rideStartTime.setText(bikeRide.createJsDateWrapperRideStartTime().toString("h:mm tt dddd, MMMM dd, yyyy"));
        details.setText(bikeRide.getDetails());
    }

    @Override
    public void display(JsArray<Tracking> trackings) {
        StringBuffer currentTrackings = new StringBuffer();

        final int numTrackings = trackings.length();
        for(int index=0; index< numTrackings; index++) {
            Tracking tracking = trackings.get(index);
            currentTrackings.append("<li>").append(tracking.getTrackingUserName()).append("</li>");
        }
        this.currentTrackings.setText("<ol>" + currentTrackings.toString() + "</ol>");
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
