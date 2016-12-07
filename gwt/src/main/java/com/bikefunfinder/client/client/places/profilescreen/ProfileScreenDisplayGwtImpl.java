package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 4:22 PM
 */

import com.bikefunfinder.client.client.places.displays.BaseComposite;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.ServiceVersion;
import com.bikefunfinder.client.shared.model.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;

public class ProfileScreenDisplayGwtImpl extends BaseComposite implements ProfileScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, ProfileScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    @UiField
    WidgetList widgetList;

    @UiField
    ScrollPanel scrollPanel;

    @UiField
    HTML profileName;

    Label userName = new Label();
    Label joinedTimeStamp = new Label();
    Label totalHostedBikeRideCount = new Label();
    Label serviceVersionLabel = new Label();
    Label clientVersionLabel = new Label();

    private static Widget buildFormWidget(String title, Widget widget) {
        FormListEntry formListEntry = new FormListEntry();
        formListEntry.setText(title);
        formListEntry.add(widget);

        return formListEntry;
    }

    public ProfileScreenDisplayGwtImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        scrollPanel.setUsePos(MGWT.getOsDetection().isAndroid());
        setScrollableWidget(scrollPanel);

        userName.setWidth("100%");
        clientVersionLabel.setWidth("100%");
        serviceVersionLabel.setWidth("100%");
        joinedTimeStamp.setWidth("100%");
        totalHostedBikeRideCount.setWidth("100%");

        widgetList.add(buildFormWidget("Client Version:", clientVersionLabel));
        widgetList.add(buildFormWidget("Service Version:", serviceVersionLabel));
        widgetList.add(buildFormWidget("Date Joined BFF:", joinedTimeStamp));
        widgetList.add(buildFormWidget("Rides Created:", totalHostedBikeRideCount));
    }

    @Override
    public void displayFailedToLoadProfileMessage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display(String profileNameText) {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.appendEscaped("Welcome " + profileNameText);
        profileName.setText(safeHtmlBuilder.toSafeHtml().asString());
    }

    @Override
    public void displayServiceVersion(ServiceVersion serviceVersion) {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.appendEscaped(serviceVersion.getVersion());
        serviceVersionLabel.setText(safeHtmlBuilder.toSafeHtml().asString());
    }

    @Override
    public void displayClientVersion(String clientVersion) {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.appendEscaped(clientVersion);
        clientVersionLabel.setText(safeHtmlBuilder.toSafeHtml().asString());
    }

    @Override
    public void display(AnonymousUser anonymousUser) {
        if(anonymousUser==null) return; // failSafe but ugly;

        //setSafeText(rideImage, bikeRide.getImagePath());
        userName.setText(anonymousUser.getUserName());

        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.appendEscaped(String.valueOf(anonymousUser.getTotalHostedBikeRideCount()));
        totalHostedBikeRideCount.setText(safeHtmlBuilder.toSafeHtml().asString());

        safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.appendEscaped(buildJoinedDate(anonymousUser));
        joinedTimeStamp.setText(safeHtmlBuilder.toSafeHtml().asString());
    }

    private String buildJoinedDate(AnonymousUser anonymousUser) {
        if(anonymousUser.createJsDateWrapperJoinedTimeStamp()!=null) {
            return anonymousUser.createJsDateWrapperJoinedTimeStamp().toString(ScreenConstants.DateFormatPrintPretty) +
                                    " at " +
                                    anonymousUser.createJsDateWrapperJoinedTimeStamp().toString(ScreenConstants.TimeFormatPrintPretty);
        }
        return "";
    }

    @Override
    public void display(User user) {
        //TODO COMING IN PAID VERSION.
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("showMyRidesButton")
    protected void onShowMyRidesButton(TapEvent event) {
        if (presenter != null) {
            presenter.onShowMyRidesButton();
        }
    }

    @UiHandler("backButton")
    protected void onBackButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.backButtonSelected();
        }
    }

//    @UiHandler("googleLoginButton")
//    protected void ongoogleLoginButton(TapEvent event) {
//        if (presenter != null) {
//            //Window.alert("ongoogleLoginButton clicked");
//            presenter.onLoginButtonPressed();
//        }
//    }

}
