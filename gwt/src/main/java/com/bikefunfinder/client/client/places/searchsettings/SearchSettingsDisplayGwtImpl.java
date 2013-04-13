package com.bikefunfinder.client.client.places.searchsettings;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:31 AM
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;

public class SearchSettingsDisplayGwtImpl extends Composite implements SearchSettingsScreenDisplay {
    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, SearchSettingsDisplayGwtImpl> {
    }

    @Override
    public void display() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public SearchSettingsDisplayGwtImpl() {
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
}
