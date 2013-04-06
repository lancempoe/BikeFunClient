package com.bikefunfinder.client.client.places.loginscreen;
/*
 * @author: tneuwerth
 * @created 4/4/13 10:56 AM
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LoginScreenDisplayGwtImpl extends Composite implements LoginScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, LoginScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    public LoginScreenDisplayGwtImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void display() {
    }


    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
