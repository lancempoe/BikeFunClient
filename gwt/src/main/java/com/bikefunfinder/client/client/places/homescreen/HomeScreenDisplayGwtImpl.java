package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.widgets.BasicCellSearchDetailImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import com.bikefunfinder.client.shared.widgets.BasicCell;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.base.ButtonBase;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.tabbar.TabBarButton;

import java.util.List;

public class HomeScreenDisplayGwtImpl extends Composite implements HomeScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, HomeScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

//    @UiField(provided = true)
//    CellList<BikeRide> cellList;
    @UiField
    VerticalPanel cellVP;

    @UiField
    HTML cityName;

    @UiField(provided = true)
    ButtonBase addButton;

    @UiField(provided = true)
    ButtonBase searchButton;

    @UiField(provided = true)
    ButtonBase loginButton;

    public HomeScreenDisplayGwtImpl() {
//        BasicCell<BikeRide> cell = new BasicCellSearchDetailImpl();
//        cellList = new CellList<BikeRide>(cell);
//        cellList.setRound(true);

        addButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarMostRecentImage());
        searchButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarSearchImage());
        loginButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarContactsImage());

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void display(List<BikeRide> list) {
//        cellList.render(list);
        for(BikeRide br: list) {
            StringBuilder html = new StringBuilder("<h1>").append(br.getBikeRideName()).append("</h1>")
                                           .append("<h2>").append(br.getRideStartTime()).append("</h2>")
                                           .append("<h2>").append(br.getDetails()).append("</h2>");
            cellVP.add(new HTML(html.toString()));
        }


    }

    @Override
    public void display(Root root) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

//    @UiHandler("cellList")
//    protected void onCellSelected(CellSelectedEvent event) {
//        if (presenter != null) {
////            presenter.onCellSelected(event.getIndex());
//            Window.alert("\\/\\/todo: show client stuff for this"+event.getIndex());
//        }
//    }

    @UiHandler("hereAndNowButton")
    protected void onHereAndNowButton(TapEvent event) {
        if (presenter != null) {
            presenter.onAboutButton();
        }
    }

    @UiHandler("timeAndDayButton")
    protected void onTimeAndDayButton(TapEvent event) {
        if (presenter != null) {
            presenter.onAboutButton();
        }
    }





}
