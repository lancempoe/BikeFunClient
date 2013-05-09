package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 4:22 PM
 */

import com.bikefunfinder.client.client.places.eventscreen.EventScreenDisplay;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase;

public class ProfileScreenDisplayGwtImpl extends Composite implements ProfileScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, ProfileScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    @UiField
    WidgetList widgetList;

    MTextBox userName = new MTextBox();
    MTextBox joinedTimeStamp = new MTextBox();
    MTextBox totalHostedBikeRideCount = new MTextBox();

    private static Widget buildFormWidget(String title, Widget widget) {
        FormListEntry formListEntry = new FormListEntry();
        formListEntry.setText(title);
        formListEntry.add(widget);
        return formListEntry;
    }

    public ProfileScreenDisplayGwtImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        userName.setReadOnly(true);
        joinedTimeStamp.setReadOnly(true);
        totalHostedBikeRideCount.setReadOnly(true);

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
        setSafeText(userName, anonymousUser.getUserName());
        setSafeText(totalHostedBikeRideCount, String.valueOf(anonymousUser.getTotalHostedBikeRideCount()));

        if(anonymousUser.createJsDateWrapperJoinedTimeStamp()!=null) {
            final String timeText = anonymousUser.createJsDateWrapperJoinedTimeStamp().toString(ScreenConstants.DateTimeFormatPrintPretty);
            setSafeText(joinedTimeStamp, timeText);
        } else {
            setSafeText(joinedTimeStamp, " ");
        }
    }

    private static void setSafeText(MValueBoxBase widget, String text) {
        if(text!=null && !text.isEmpty()) {
            widget.setText(text);
        }
    }

    @Override
    public void display(User user) {
       //TODO COMING IN PAID VERSION.
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

    @UiHandler("googleLoginButton")
    protected void ongoogleLoginButton(TapEvent event) {
        if (presenter != null) {
            //Window.alert("ongoogleLoginButton clicked");
            presenter.onLoginButtonPressed();
        }
    }

}
