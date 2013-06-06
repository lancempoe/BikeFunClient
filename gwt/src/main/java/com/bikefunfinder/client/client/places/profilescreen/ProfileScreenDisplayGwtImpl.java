package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 4:22 PM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;

public class ProfileScreenDisplayGwtImpl extends Composite implements ProfileScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, ProfileScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    @UiField
    WidgetList widgetList;

    @UiField
    ScrollPanel scrollPanel;

    Label userName = new Label();
    Label joinedTimeStamp = new Label();
    Label totalHostedBikeRideCount = new Label();

    private static Widget buildFormWidget(String title, Widget widget) {
        FormListEntry formListEntry = new FormListEntry();
        formListEntry.setText(title);
        formListEntry.add(widget);

        return formListEntry;
    }

    public ProfileScreenDisplayGwtImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        scrollPanel.setUsePos(MGWT.getOsDetection().isAndroid());

        userName.setWidth("100%");
        joinedTimeStamp.setWidth("100%");
        totalHostedBikeRideCount.setWidth("100%");

        widgetList.add(buildFormWidget("User Name:", userName));
        widgetList.add(buildFormWidget("Date Joined BFF:", joinedTimeStamp));
        widgetList.add(buildFormWidget("Ride Created:", totalHostedBikeRideCount));

    }

    @Override
    public void displayFailedToLoadProfileMessage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display(AnonymousUser anonymousUser) {
        if(anonymousUser==null) return; // failSafe but ugly;

        //setSafeText(rideImage, bikeRide.getImagePath());
        userName.setText(anonymousUser.getUserName());
        totalHostedBikeRideCount.setText(String.valueOf(anonymousUser.getTotalHostedBikeRideCount()));
        joinedTimeStamp.setText(buildJoinedDate(anonymousUser));
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
