package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: lancepoehler
 * @created 4/6/13 2:22 AM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Location;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.window;
import static gwtquery.plugins.ui.Ui.Ui;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;

import com.google.gwt.i18n.shared.DateTimeFormat;
import java.util.Date;

public class CreateScreenDisplayGwtImpl  extends Composite implements CreateScreenDisplay {
    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, CreateScreenDisplayGwtImpl> {
    }

    @UiField
    TextBox startTime;
//    DatePicker startTime;
    @UiField
    FormPanel form;

    @UiField
    HTMLPanel formContents;

    @UiField
    FlowPanel dateTimeFP;

    public CreateScreenDisplayGwtImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        startTime.getElement().setId("datepicker");
        setupDemoElement(dateTimeFP.getElement());
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
    TextBox bikeRideName;

    @UiField
    TextBox locationAddress;

    @UiField
    TextBox locationCity;

    @UiField
    TextBox locationState;

    @UiField
    TextArea rideDetails;

    @UiHandler("submitRide")
    protected void onSubmitRidePressed(TapEvent event) {
        if(presenter != null) {
            BikeRide br = GWT.create(BikeRide.class);

            br.setRideLeaderName("Todo:LeaderName!");
            br.setBikeRideName(bikeRideName.getText());
            br.setDetails(rideDetails.getText());
            br.setRideLeaderId("abc");

            Location location = GWT.create(Location.class);
            location.setCity(locationCity.getText());
            location.setState(locationState.getText());
            location.setStreetAddress(locationAddress.getText());

            GeoLoc geoLoc = GWT.create(GeoLoc.class);
            geoLoc.setLatitude("0.00");
            geoLoc.setLongitude("0.00");
            location.setGeoLoc(geoLoc);
            br.setLocation(location);

            DateTimeFormat dtf = DateTimeFormat.getFormat("mm/dd/yyyy");
            Date date = null;
            try
            {
                date =  dtf.parse(startTime.getText());
                Window.alert("getTime() " + date.getTime() + "   parseTime: " + date.toString());
                br.setRideStartTime(date.getTime());
            }
            catch(IllegalArgumentException e)
            {
                Window.alert("Cannot read the date and time input.");
            }


            presenter.onFormSelected(br);
        }
    }
    @UiHandler("backButton")
    protected void onBackButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.backButtonSelected();
        }
    }

    public void setupDemoElement(Element demo) {
//        $("#datepicker", demo).as(Ui).datepicker();

        $("#datepicker", demo).as(Ui).datepicker();


    }



}
