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
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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
import com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase;

import java.util.ArrayList;
import java.util.List;

public class EventScreenDisplayGwtImpl extends Composite implements EventScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, EventScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    @UiField
    WidgetList widgetList;

    MIntegerBox totalPeopleTrackingCount = new MIntegerBox();
    MCheckBox currentlyTracking = new MCheckBox();
    //MTextBox rideImage = new MTextBox();
    MTextBox bikeRideName = new MTextBox();
    MTextBox rideLeaderName = new MTextBox();
    MTextBox targetAudience = new MTextBox();
    MTextBox formattedAddress = new MTextBox();
    MTextBox distanceFromClient = new MTextBox();
    MTextBox rideStartTime = new MTextBox();
    MTextArea details = new MTextArea();

    @UiField
    HTML currentTrackings = new HTML();


    private static Widget buildFormWidget(String title, Widget widget) {
        FormListEntry formListEntry = new FormListEntry();
        formListEntry.setText(title);
        formListEntry.add(widget);
        return formListEntry;
    }
    public EventScreenDisplayGwtImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        totalPeopleTrackingCount.setReadOnly(true);
        currentlyTracking.setReadOnly(true);
        //rideImage.setReadOnly(true);
        bikeRideName.setReadOnly(true);
        rideLeaderName.setReadOnly(true);
        targetAudience.setReadOnly(true);
        formattedAddress.setReadOnly(true);
        distanceFromClient.setReadOnly(true);
        rideStartTime.setReadOnly(true);
        details.setReadOnly(true);
        details.setVisibleLines(10);

        widgetList.add(buildFormWidget("Total People Tracking:", totalPeopleTrackingCount));
        widgetList.add(buildFormWidget("Someone is tracking:", currentlyTracking));
        //widgetList.add(buildFormWidget("Ride Image:", rideImage));
        widgetList.add(buildFormWidget("Bike Ride Name:", bikeRideName));
        widgetList.add(buildFormWidget("Ride Leader Name:", rideLeaderName));
        widgetList.add(buildFormWidget("Target Audience:", targetAudience));
        widgetList.add(buildFormWidget("Starting Address:", formattedAddress));
        widgetList.add(buildFormWidget("Starting Address:", formattedAddress));
        widgetList.add(buildFormWidget("Distance from the Start:", distanceFromClient));
        widgetList.add(buildFormWidget("Start Time:", rideStartTime));
        widgetList.add(buildFormWidget("Ride Details:", details));
    }

    @Override
    public void displayFailedToLoadRideMessage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display(BikeRide bikeRide) {
        if(bikeRide==null) return; // failSafe but ugly;

        setSafeText(totalPeopleTrackingCount, bikeRide.getTotalPeopleTrackingCount());

        if(bikeRide.isCurrentlyTracking()!=null && !bikeRide.isCurrentlyTracking().isEmpty()) {
            currentlyTracking.setValue(Boolean.parseBoolean(bikeRide.isCurrentlyTracking()));
        }

        //setSafeText(rideImage, bikeRide.getImagePath());
        setSafeText(bikeRideName, bikeRide.getBikeRideName());
        setSafeText(rideLeaderName, bikeRide.getRideLeaderName());
        setSafeText(targetAudience, bikeRide.getTargetAudience());

        if(bikeRide.getLocation()!=null) {
            setSafeText(formattedAddress, bikeRide.getLocation().getFormattedAddress());
        }

        final String distanceFromClientString = ScreenConstants.DISTANCE_FORMAT.format(bikeRide.getDistanceFromClient());
        if(distanceFromClient!=null) {
            setSafeText(distanceFromClient, distanceFromClientString + " Miles");
        }


        if(bikeRide.createJsDateWrapperRideStartTime()!=null) {
            final String timeText = bikeRide.createJsDateWrapperRideStartTime().toString(ScreenConstants.DateTimeFormatPrintPretty);
            setSafeText(rideStartTime, timeText);
        } else {
            setSafeText(rideStartTime, " ");
        }

        setSafeText(details, bikeRide.getDetails());
    }

    private static void setSafeText(MValueBoxBase widget, String text) {
        if(text!=null && !text.isEmpty()) {
            widget.setText(text);
        }
    }
    @Override
    public void displayTrackings(JsArray<Tracking> trackings) {

        final SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.appendHtmlConstant("<ol>");

        if(trackings!=null && trackings.length()>0) {
            final int numTrackings = trackings.length();

            for(int index=0; index< numTrackings; index++) {
                Tracking tracking = trackings.get(index);
                if(tracking!=null && tracking.getTrackingUserName()!=null) {
                    safeHtmlBuilder.appendHtmlConstant("<li>")
                                   .appendEscaped(tracking.getTrackingUserName())
                                   .appendHtmlConstant("</li>");
                }
            }
        }
        safeHtmlBuilder.appendHtmlConstant("</ol>");

        this.currentTrackings.setHTML(safeHtmlBuilder.toSafeHtml());
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
