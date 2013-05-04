package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList;
import com.googlecode.mgwt.ui.client.widget.base.ButtonBase;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowHeader;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel;
import com.googlecode.mgwt.ui.client.widget.tabbar.TabBarButton;
import com.googlecode.mgwt.ui.client.widget.HeaderList;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.CellGroup;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import java.util.ArrayList;
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

    private GroupingCellList<Header, Content> groupingCellList = new GroupingCellList<Header, Content>(new ContentCell(), new HeaderCell());

    /** BikeRide List **/
    @UiField(provided = true)
    PullPanel bikeEntries;
    @UiField(provided = true)
    HeaderList<Header, Content> bikeEntriesHeaderList;

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

    private PullArrowHeader pullArrowHeader;

    private final HomeRefreshPullHandler homeRefreshPullHandler;

    public HomeScreenDisplayGwtImpl() {
        pullArrowHeader = new PullArrowHeader();
        bikeEntries = new PullPanel();
        //Interesting!  Set header
        bikeEntries.setHeader(pullArrowHeader);
        homeRefreshPullHandler = new HomeRefreshPullHandler(pullArrowHeader, presenter);

        bikeEntries.setHeaderPullHandler(homeRefreshPullHandler);

        addButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarMostRecentImage());
        searchButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarSearchImage());
        loginButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarContactsImage());

        initWidget(uiBinder.createAndBindUi(this));
        
        timeAndDayButton.addStyleName(style.buttonTreatment());
        hereAndNowButton.addStyleName(style.buttonTreatment());

        bikeEntriesHeaderList = new HeaderList<Header, Content>(groupingCellList);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        homeRefreshPullHandler.setPresenter(presenter);
    }

    @Override
    public void display(List<BikeRide> list) {
        bikeEntries.clear();

        List<CellGroup<Header, Content>> visList = buildList(list);

        bikeEntriesHeaderList.render(buildList(list));

//        bikeEntries.add(bikeEntriesHeaderList);
/*
        JsDateWrapper firstDate = null;
        JsDateWrapper lastDay = null;

        for(BikeRide br: list) {
            JsDateWrapper date = br.createJsDateWrapperRideStartTime();

            // Date Bar Creation
            if(firstDate == null)
            {
                firstDate = date;
                lastDay = firstDate;
                bikeEntries.add(new HTML(dayBar(firstDate)));
            }
            else if(!date.isSameDay(lastDay)) {
                lastDay = date;
                bikeEntries.add(new HTML(dayBar(date)));
            }


            // Ride Item Creation
            String timeString = date.toString("h:mm tt"); // 9:30 am
                    //getShortTimeFormat().format(date);
            StringBuilder html = new StringBuilder()
                                   .append("<h2>").append(br.getBikeRideName()).append("</h2>")
                                   .append("<p>").append(timeString).append("</p>")
                                   .append("<p>").append(br.getDetails()).append("</p>");

            HTML cell = new HTML(html.toString());
            cell.addClickHandler(new BikeRideClickHandler(presenter, br, homeRefreshPullHandler));
            bikeEntries.add(cell);
        }
//*/
        bikeEntries.refresh();
    }

    public String dayBar(JsDateWrapper date)
    {
        //DateTimeFormat fmtDay = DateTimeFormat.getFormat("EEEE, MMMM dd, yyyy");

        StringBuilder dayBar = new StringBuilder("<div style=\"background-color:#dec8cb\">")
                .append("<h2>").append(date.toString("dddd, MMMM dd, yyyy")).append("</h2>")
                .append("</div>");


        return dayBar.toString();
    }

    public static boolean isSameDate(JsDateWrapper date1, JsDateWrapper date2) {
        return date1.isSameDay(date2);
    }
    public static boolean isSameDay(JsDate date1, JsDate date2) {
        return date1.getDate() == date2.getDate() &&
           date1.getMonth() == date2.getMonth() &&
           date1.getFullYear() == date2.getFullYear();
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

    private List<CellGroup<Header, Content>> buildList(List<BikeRide> bikeRides) {
        ArrayList<CellGroup<Header, Content>> list = new ArrayList<CellGroup<Header, Content>>();

        Header header  = null;
        ArrayList<Content> contentList = new ArrayList<Content>();
        JsDateWrapper priorDate = null;
        for (BikeRide bikeRide : bikeRides) {

            JsDateWrapper bikeRideDate = bikeRide.createJsDateWrapperRideStartTime();
            if (priorDate == null || !priorDate.isSameDay(bikeRideDate)) {
                if (header != null && contentList.size() > 0) {
                    CellGroup<Header, Content> cellGroup = new GroupingCellList.StandardCellGroup<Header, Content>(header.getName(), header, contentList);
                    list.add(cellGroup);
                }
                priorDate = bikeRideDate;
                header = new Header(bikeRideDate.toString("dddd, MMMM dd, yyyy"));
                contentList = new ArrayList<Content>();
            }

            //Build content of 1 ride
            String timeString = bikeRideDate.toString("h:mm tt");
            StringBuilder html = new StringBuilder()
                    .append("<h2>").append(bikeRide.getBikeRideName()).append("</h2>")
                    .append("<p>").append(timeString).append("</p>")
                    .append("<p>").append(bikeRide.getDetails()).append("</p>");
            contentList.add(new Content(html.toString()));
        }

        //add the final item
        if (header != null && contentList.size() > 0) {
            CellGroup<Header, Content> cellGroup = new GroupingCellList.StandardCellGroup<Header, Content>(header.getName(), header, contentList);
            list.add(cellGroup);
        }

        return list;
    }

    private static class ContentCell implements Cell<Content> {

        @Override
        public void render(SafeHtmlBuilder safeHtmlBuilder, Content model) {
            safeHtmlBuilder.appendEscaped(model.getName());

        }

        @Override
        public boolean canBeSelected(Content model) {
            return true;
        }

    }
    private static class HeaderCell implements Cell<Header> {

        @Override
        public void render(SafeHtmlBuilder safeHtmlBuilder, Header model) {

            safeHtmlBuilder.appendEscaped(model.getName());
        }

        @Override
        public boolean canBeSelected(Header model) {
            return false;
        }

    }

}