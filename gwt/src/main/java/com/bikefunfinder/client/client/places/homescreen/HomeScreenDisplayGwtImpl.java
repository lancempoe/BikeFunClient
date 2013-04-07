package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.base.ButtonBase;
import com.googlecode.mgwt.ui.client.widget.tabbar.TabBarButton;

import java.util.Date;
import java.util.List;

public class HomeScreenDisplayGwtImpl extends Composite implements HomeScreenDisplay {

    interface MyStyle extends CssResource {
        String buttonTreatment();
    }
	@UiField MyStyle style;


    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, HomeScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    @UiField
    FlowPanel bikeEntries;

    @UiField
    com.googlecode.mgwt.ui.client.widget.ScrollPanel scroller;

    @UiField
    HTML cityName;

    @UiField(provided = true)
    ButtonBase addButton;

    @UiField(provided = true)
    ButtonBase searchButton;

    @UiField(provided = true)
    ButtonBase loginButton;

    @UiField
    Button timeAndDayButton;

    @UiField
    Button hereAndNowButton;

    public HomeScreenDisplayGwtImpl() {
        addButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarMostRecentImage());
        searchButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarSearchImage());
        loginButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarContactsImage());

        initWidget(uiBinder.createAndBindUi(this));
        
        timeAndDayButton.addStyleName(style.buttonTreatment());
        hereAndNowButton.addStyleName(style.buttonTreatment());
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void display(List<BikeRide> list) {
        Date firstDate = null;
        Date lastDay = null;
        for(BikeRide br: list) {
            Date date = br.getRideStartTimeDate();
            if(firstDate == null) {
                firstDate = date;
                lastDay = firstDate;
                bikeEntries.add(new HTML(dayBar(firstDate).toString()));
            }
            else if(!isSameDay(date, lastDay)) {
                lastDay = date;
                bikeEntries.add(new HTML(dayBar(date)));
            }
            String timeString = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.HOUR_MINUTE).format(date);
                    //getShortTimeFormat().format(date);
            StringBuilder html = new StringBuilder()
                                   .append("<h2>").append(br.getBikeRideName()).append("</h2>")
                                   .append("<p>").append(timeString).append("</p>")
                                   .append("<p>").append(br.getDetails()).append("</p>");
            bikeEntries.add(new HTML(html.toString()));
        }
        scroller.refresh(); //The scroller needs to know that we've just added stuff
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
    public void display(String cityNameText) {
        cityName.setText(cityNameText);
    }

    @UiHandler("hereAndNowButton")
    protected void onHereAndNowButton(TapEvent event) {
        if (presenter != null) {
            presenter.onHereAndNowButton();
        }
    }

    @UiHandler("timeAndDayButton")
    protected void onTimeAndDayButton(TapEvent event) {
        if (presenter != null) {
            presenter.onTimeAndDayButton();
        }
    }

    @UiHandler("addButton")
    protected void onAddButtonButton(TapEvent event) {
        if (presenter != null) {
            presenter.onNewButton();
        }
    }

    @UiHandler("searchButton")
    protected void onSearchButton(TapEvent event) {
        if (presenter != null) {
            presenter.onSearchButton();
        }
    }

    @UiHandler("loginButton")
    protected void onLoginButton(TapEvent event) {
        if (presenter != null) {
            presenter.onLoginButton();
        }
    }
}