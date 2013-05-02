package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: lancepoehler
 * @created 4/6/13 2:22 AM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Location;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.*;

import java.util.Date;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.ui.Ui.Ui;

public class CreateScreenDisplayGwtImpl  extends Composite implements CreateScreenDisplay {
    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, CreateScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    @UiField
    HTML userName;

    @UiField(provided = true) WidgetList widgetList;
    FormListEntry formListEntry;

    MTextBox bikeRideName = new MTextBox();
    MListBox targetAudience = new MListBox();
    MTextBox locationAddress = new MTextBox();
    MTextBox locationCity = new MTextBox();
    MTextBox locationState = new MTextBox();
    MTextBox startDate = new MTextBox();
    MTextBox startTime = new MTextBox();
    MTextArea details = new MTextArea();
    MCheckBox trackingAllowed = new MCheckBox();
    MTextBox rideImage = new MTextBox();

    public CreateScreenDisplayGwtImpl() {

        //Build the form.
        widgetList = new WidgetList();

        formListEntry = new FormListEntry();
        formListEntry.setText("Bike Ride Name:");
        formListEntry.add(bikeRideName);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Target Audience:");
        for(ScreenConstants.TargetAudience enumVal: ScreenConstants.TargetAudience.values()) {
            targetAudience.addItem(enumVal.getDisplayName());
        }
        formListEntry.add(targetAudience);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Starting Address:");
        formListEntry.add(locationAddress);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Starting City:");
        formListEntry.add(locationCity);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Starting State:");
        formListEntry.add(locationState);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Starting Date:");
        startDate.getElement().setId("datepicker");
        startDate.setReadOnly(true);
        setupDatePicker();
        formListEntry.add(startDate);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Starting Time:");
        startTime.getElement().setId("timepicker");
        startTime.setReadOnly(true);
        formListEntry.add(startTime);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Ride Details:");
        details.setVisibleLines(10);
        formListEntry.add(details);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Tracking Allowed:");
        formListEntry.add(trackingAllowed);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Ride Image:");
        formListEntry.add(rideImage);
        widgetList.add(formListEntry);

        initWidget(uiBinder.createAndBindUi(this));

    }

    @Override
    protected void onLoad()
    {
        setupTimePicker();
        setupDatePicker();
    }

    @Override
    public void displayFailedToCreateRideMessage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display(BikeRide bikeRide) {
        //To change body of implemented methods use File | Settings | File Templates.

    }

    //setUserName

    @Override
    public void display(String userName) {
        this.userName.setText("Create Ride by: " + userName);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("submitRide")
    protected void onSubmitRidePressed(TapEvent event) {
        if(presenter != null) {
            BikeRide br = GWT.create(BikeRide.class);

            br.setBikeRideName(bikeRideName.getText());
            br.setBikeRideName(targetAudience.getValue(targetAudience.getSelectedIndex()));
            Location location = GWT.create(Location.class);
            location.setCity(locationCity.getText());
            location.setState(locationState.getText());
            location.setStreetAddress(locationAddress.getText());
            br.setLocation(location);
            try
            {
                DateTimeFormat dtf = DateTimeFormat.getFormat("mm/dd/yyyy h:mm a");
                Date date = null;
                date =  dtf.parse(startDate.getText() + " " + startTime.getText());
//                Window.alert("getTime() " + date.getTime() + "   parseTime: " + date.toString());
                br.setRideStartTime(date.getTime());
            }
            catch(IllegalArgumentException e)
            {
                Window.alert("Cannot read the date and time input.");
            }
            br.setDetails(details.getText());
            br.setTrackingAllowed(trackingAllowed.getValue());
            br.setImagePath(rideImage.getValue());

            presenter.onFormSelected(br);
        }
    }
    @UiHandler("backButton")
    protected void onBackButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.backButtonSelected();
        }
    }

    native void setupDatePicker() /*-{
        $wnd.setupDatePicker();
     }-*/;

    native void setupTimePicker() /*-{
       $wnd.setupTimePicker();
    }-*/;



}
