package com.bikefunfinder.client.client.places.editscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 4:22 PM
 */

import com.bikefunfinder.client.client.places.createscreen.CreateScreenDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EditScreenDisplayGwtImpl extends Composite implements EditScreenDisplay {
    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, EditScreenDisplayGwtImpl> {
    }


    private Presenter presenter;

    @Override
    public void display() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
