package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.widgets.BasicCellSearchDetailImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwtphonegap.showcase.client.model.PGModule;
import com.bikefunfinder.client.shared.widgets.BasicCell;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.base.ButtonBase;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;

import java.util.List;

public class HomeScreenDisplayGwtImpl extends Composite implements HomeScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, HomeScreenDisplayGwtImpl> {
    }

    @UiField(provided = true)
    CellList<BikeRide> cellList;
    private Presenter presenter;

    @UiField
    ButtonBase aboutButton;

    public HomeScreenDisplayGwtImpl() {

        BasicCell<BikeRide> cell = new BasicCellSearchDetailImpl() {

            @Override
            public String getDisplayString(BikeRide model) {
                return model.getBikeRideName();
            }

            @Override
            public boolean canBeSelected(BikeRide model) {
                return true;
            }
        };

        cellList = new CellList<BikeRide>(cell);

        cellList.setRound(true);

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void display(List<BikeRide> list) {
        cellList.render(list);

    }

    @UiHandler("cellList")
    protected void onCellSelected(CellSelectedEvent event) {
        if (presenter != null) {
            presenter.onCellSelected(event.getIndex());
        }
    }

    @UiHandler("aboutButton")
    protected void onAboutButton(TapEvent event) {
        if (presenter != null) {
            presenter.onAboutButton();
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }

}
