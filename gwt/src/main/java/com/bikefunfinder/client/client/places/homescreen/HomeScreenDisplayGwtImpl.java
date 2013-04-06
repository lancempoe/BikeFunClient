package com.bikefunfinder.client.client.places.homescreen;

import java.util.Date;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.base.ButtonBase;
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
        Date firstDate = null;
        Date lastDay = null;
        for(BikeRide br: list) {
            Date date = br.getRideStartTimeDate();
            if(firstDate == null)
            {
                firstDate = date;
                lastDay = firstDate;
                cellVP.add(new HTML(dayBar(firstDate).toString()));
            }
            else if(!isSameDay(date, lastDay))
            {
                lastDay = date;
                cellVP.add(new HTML(dayBar(date)));
            }
            String timeString = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.HOUR_MINUTE).format(date);
                    //getShortTimeFormat().format(date);
            StringBuilder html = new StringBuilder("<hr>")
                                           .append("<h2>").append(br.getBikeRideName()).append("</h2>")
                                           .append("<p>").append(timeString).append("</h2>")
                                           .append("<p>").append(br.getDetails()).append("</h2></div>");
            cellVP.add(new HTML(html.toString()));
        }
    }

    public String dayBar(Date date)
    {
        DateTimeFormat fmtDay = DateTimeFormat.getFormat("EEEE, MMMM dd, yyyy");

        StringBuilder dayBar = new StringBuilder("<div style=\"background-color:#3a413f\">")
                .append("<h2>").append(fmtDay.format(date)).append("</h2>")
                .append("</div>");
        return dayBar.toString();
    }

    public static boolean isSameDay(Date date1, Date date2)
    {
        DateTimeFormat fmtDay = DateTimeFormat.getFormat("EEEE, MMMM dd, yyyy");
        String timeString1 = fmtDay.format(date1);
        String timeString2 = fmtDay.format(date2);
        return timeString1.equals(timeString2);
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
