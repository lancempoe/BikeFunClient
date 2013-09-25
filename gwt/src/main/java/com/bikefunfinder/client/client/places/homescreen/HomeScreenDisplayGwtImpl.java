package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.Tools.BikeRideHelper;
import com.bikefunfinder.client.shared.Tools.DateTools;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.bikefunfinder.client.shared.widgets.*;
import com.bikefunfinder.client.shared.widgets.base.MainMenuHeaderPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.CellGroup;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeScreenDisplayGwtImpl extends Composite implements HomeScreenDisplay {

    private static boolean hideOldRides = true; //Default to hiding old rides.
    private static final String SHOW_EXPIRED_RIDES = "Show Earlier Rides";
    private static final String HIDE_EXPIRED_RIDES = "Hide Earlier Rides";
    
    interface MyStyle extends CssResource {
        String buttonTreatment();
    }
	@UiField public static MyStyle style;


    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, HomeScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    PullGroupPanel pp;

    @UiField
    LayoutPanel headerListWidget;

    @UiField
    FlowPanel footerFlowPanel;


    @UiField
    Button mainScreenToggleButton;

    @UiField
    Button showHideToggleButton;

    @UiField
    Button refreshButton;

    @UiField
    MainMenuHeaderPanel headerPanel;

    public HomeScreenDisplayGwtImpl() {

        Logger.getLogger("").log(Level.INFO, "hsClose!");
        try{
            initWidget(uiBinder.createAndBindUi(this));
        }
        catch(Exception e) {
            Logger.getLogger("").log(Level.SEVERE, "hsUck! " + e.getMessage() + ":::::::::: " + e.toString() + " :::::::: " + e.getStackTrace());
        }
        Logger.getLogger("").log(Level.INFO, "hsYESYES!!");

        Logger.getLogger("").log(Level.INFO, "Hello we are back in homescreenimple() initWidget");

        mainScreenToggleButton.addStyleName(style.buttonTreatment());
        mainScreenToggleButton.addStyleName("icon-globe");
        mainScreenToggleButton.addStyleName("icon-large");
        showHideToggleButton.addStyleName(style.buttonTreatment());
        refreshButton.addStyleName(style.buttonTreatment());
        refreshButton.addStyleName("icon-refresh");
        refreshButton.addStyleName("icon-large");
        //TODO find way for this buttons to work with visually impaired people.
        //right now is stays "unreadable text"

        MyGroupingCellList<Header, BikeRideHelper.Content> groupingCellList = new MyGroupingCellList<Header, BikeRideHelper.Content>(new ContentCell(), new HeaderCell());
        groupingCellList.addSelectionHandler(new SelectionHandler<BikeRideHelper.Content>() {
            @Override
            public void onSelection(SelectionEvent<BikeRideHelper.Content> event) {
            presenter.onRideClick(event.getSelectedItem().getBikeRide());
            }
        });

        pp = new PullGroupPanel<Header, BikeRideHelper.Content>(new HeaderListWithPullPanel<Header, BikeRideHelper.Content>(groupingCellList), presenter);
        headerListWidget.add(pp);


        /*MGWT.addOrientationChangeHandler(new OrientationChangeHandler() {
            @Override
            public void onOrientationChanged(OrientationChangeEvent event) {
                Window.alert(event.getOrientation().toString());
                if(event.getOrientation()!=null &&
                   event.getOrientation()== OrientationChangeEvent.ORIENTATION.PORTRAIT) {
                    wasPortraitAdjusted = false;
                    adjustPullPanelSizePortrait();
                    return;
                }

                wasLandAdjusted = false;
                adjustPullPanelSizeLandscape();

            }
        });*/
        Logger.getLogger("").log(Level.INFO, "Made it to the end of the constructor.");

    }

    @Override
    public int getMainSize() {
        return headerListWidget.getOffsetHeight();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    boolean wasAdjusted = false;
    @Override
    public void display(List<BikeRide> list) {

        pp.render(buildList(list));
        if(!wasAdjusted) {
            adjustPullPanelSize();
        }
        pp.refresh();
    }

    private void setShowHideToggleButtonText() {
        if (hideOldRides) {
            this.showHideToggleButton.setText(SHOW_EXPIRED_RIDES);
        } else {
            this.showHideToggleButton.setText(HIDE_EXPIRED_RIDES);
        }
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
    public void setTitle(String cityNameText) {
        headerPanel.setTitle(cityNameText);
    }

    @UiHandler("mainScreenToggleButton")
    protected void onMainScreenToggleButton(TapEvent event) {
        if (presenter != null) {
            presenter.onMainScreenToggleButton();
        }
    }

    @UiHandler("showHideToggleButton")
    protected void onShowHideToggleButton(TapEvent event) {
        hideOldRides = !hideOldRides;
        if (presenter != null) {
            presenter.onShowHideToggleButton();
        }
    }

    @UiHandler("refreshButton")
    protected void onRefreshButton(TapEvent event) {
        if (presenter != null) {
            presenter.onRefreshButton();
        }
    }


    private List<CellGroup<Header, BikeRideHelper.Content>> buildList(List<BikeRide> bikeRides) {

        setShowHideToggleButtonText();

        ArrayList<CellGroup<Header, BikeRideHelper.Content>> list = new ArrayList<CellGroup<Header, BikeRideHelper.Content>>();

        Header header  = null;
        ArrayList<BikeRideHelper.Content> contentList = new ArrayList<BikeRideHelper.Content>();
        JsDateWrapper priorDate = null;
        String tableQuickLink = ""; //An empty sting will allow the quick link to function without having to see it.
        for (BikeRide bikeRide : bikeRides) {

            //Hide/Show expired rides that are not tracking
            if (hideOldRides && !bikeRide.isCurrentlyTracking() && DateTools.isOldRide(bikeRide)){
                continue;
            }

            JsDateWrapper bikeRideDate = bikeRide.createJsDateWrapperRideStartTime();
            if (priorDate == null || !priorDate.isSameDay(bikeRideDate)) {
                if (header != null && contentList.size() > 0) {
                    //tableQuickLink is the value in the right quick link bar.  To place the header value simply place "header.getName()"
                    CellGroup<Header, BikeRideHelper.Content> cellGroup = new GroupingCellList.StandardCellGroup<Header, BikeRideHelper.Content>(tableQuickLink, header, contentList);
                    list.add(cellGroup);
                }
                priorDate = bikeRideDate;
                header = new Header(bikeRideDate.toString(ScreenConstants.DateFormatPrintPretty));
                contentList = new ArrayList<BikeRideHelper.Content>();
            }

            //Build content of 1 ride
            if(bikeRide!= null) {
                contentList.add(new BikeRideHelper.Content(bikeRide));
            }
        }

        //add the final item
        if (header != null && contentList.size() > 0) {
            //tableQuickLink is the value in the right quick link bar.  To place the header value simply place "header.getName()"
            CellGroup<Header, BikeRideHelper.Content> cellGroup = new GroupingCellList.StandardCellGroup<Header, BikeRideHelper.Content>(tableQuickLink, header, contentList);
            list.add(cellGroup);
        }

        return list;
    }

    private static class ContentCell implements ColoredCell<BikeRideHelper.Content> {

        @Override
        public void render(SafeHtmlBuilder safeHtmlBuilder, BikeRideHelper.Content model) {
            safeHtmlBuilder.append(model.getShortDescription(false));
        }

        @Override
        public boolean canBeSelected(BikeRideHelper.Content model) {
            return true;
        }

        @Override
        public String getColorCss(BikeRideHelper.Content model) {
            //todo: We may want to create css in GWT. External uses !important
            return model.getBikeRideListItemCssClass();
        }
    }
    private static class HeaderCell implements ColoredCell<Header> {
        @Override
        public void render(SafeHtmlBuilder safeHtmlBuilder, Header model) {

            safeHtmlBuilder.appendEscaped(model.getName());
        }

        @Override
        public boolean canBeSelected(Header model) {
            return false;
        }

        @Override
        public String getColorCss(Header model) {
            //todo make this happen
            return "customHeaderStyles";
        }
    }

    @Override
    protected void onAttach() {
        super.onAttach();    //To change body of overridden methods use File | Settings | File Templates.
        pp.setupScrollPanel();

        PullPanel.PullWidget pullArrowHeader = new PullToRefreshTextHeader();
        pp.setHeader(pullArrowHeader);
        HomeRefreshPullHandler homeRefreshPullHandler = new HomeRefreshPullHandler(pullArrowHeader, presenter);
        pp.setHeaderPullHandler(homeRefreshPullHandler);
        pullArrowHeader.asWidget().setVisible(false); // is managed by the pull process .. this mess could be nicer.. but needs love
    }

    private void adjustPullPanelSize() {
        if(headerListWidget==null || !headerListWidget.isAttached()) {
            return;
        }

        int adjustedHeight = headerListWidget.getOffsetHeight();
        adjustedHeight -= footerFlowPanel.getOffsetHeight();
        adjustedHeight -= headerPanel.getOffsetHeight();

        if(adjustedHeight<=0) {
            return;
        }
        headerListWidget.setHeight(adjustedHeight + "px");
        wasAdjusted = true;
    }
}