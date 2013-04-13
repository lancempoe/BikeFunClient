package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 4:22 PM
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;

public class ProfileScreenDisplayGwtImpl extends Composite implements ProfileScreenDisplay {
    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, ProfileScreenDisplayGwtImpl> {
    }

    @Override
    public void display() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public ProfileScreenDisplayGwtImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    private Presenter presenter;


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
            presenter.onLoginButtonPressed();
        }
    }
}
