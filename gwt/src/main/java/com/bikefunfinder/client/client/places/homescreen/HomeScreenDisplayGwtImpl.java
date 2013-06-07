package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.bikefunfinder.client.shared.widgets.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.impl.ImageResourcePrototype;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.CellGroup;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.base.ButtonBase;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel;
import com.googlecode.mgwt.ui.client.widget.tabbar.TabBarButton;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HomeScreenDisplayGwtImpl extends Composite implements HomeScreenDisplay {

    interface MyStyle extends CssResource {
        String buttonTreatment();
    }
	@UiField MyStyle style;


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

    @UiField
    HeaderPanel headerPanel;



    public HomeScreenDisplayGwtImpl() {
        ImageResource tabBarAddImage = new ImageResourcePrototype("addIcon", new SafeUri() {
            @Override
            public String asString() {
                return "icons/addRideIcon.png";  //To change body of implemented methods use File | Settings | File Templates.
            }
        }, 0, 0, 45, 36, false , false);
        ImageResource tabBarSearchImage = new ImageResourcePrototype("searchIcon", new SafeUri() {
            @Override
            public String asString() {
                return "icons/searchRideIcon.png";  //To change body of implemented methods use File | Settings | File Templates.
            }
        }, 0, 0, 45, 36, false , false);
        ImageResource tabBarContactsImage = new ImageResourcePrototype("userProfileIcon", new SafeUri() {
            @Override
            public String asString() {
                return "icons/userProfileIcon.png";  //To change body of implemented methods use File | Settings | File Templates.
            }
        }, 0, 0, 45, 36, false , false);


        addButton = new TabBarButton(tabBarAddImage);
        searchButton = new TabBarButton(tabBarSearchImage);
        loginButton = new TabBarButton(tabBarContactsImage);
        addButton.addStyleName("menuButton");
        searchButton.addStyleName("menuButton");
        loginButton.addStyleName("menuButton");

        initWidget(uiBinder.createAndBindUi(this));
        
        timeAndDayButton.addStyleName(style.buttonTreatment());
        hereAndNowButton.addStyleName(style.buttonTreatment());

        MyGroupingCellList<Header, Content> groupingCellList = new MyGroupingCellList<Header, Content>(new ContentCell(), new HeaderCell());
        groupingCellList.addSelectionHandler(new SelectionHandler<Content>() {
            @Override
            public void onSelection(SelectionEvent<Content> event) {
            presenter.onRideClick(event.getSelectedItem().getBikeRide());
            }
        });


        pp = new PullGroupPanel<Header, Content>(new HeaderListWithPullPanel<Header, Content>(groupingCellList), presenter);
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


    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        //bikeEntriesHeaderList.setPresenter(presenter);
    }

    boolean wasAdjusted = false;
    @Override
    public void display(List<BikeRide> list) {

        pp.render(buildList(list));

        if(!wasAdjusted) {
            wasAdjusted = adjustPullPanelSize();
            pp.refresh();
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
        presenter.onLoginButton();
    }

    private List<CellGroup<Header, Content>> buildList(List<BikeRide> bikeRides) {
        ArrayList<CellGroup<Header, Content>> list = new ArrayList<CellGroup<Header, Content>>();

        Header header  = null;
        ArrayList<Content> contentList = new ArrayList<Content>();
        JsDateWrapper priorDate = null;
        String tableQuickLink = ""; //An empty sting will allow the quick link to function without having to see it.
        for (BikeRide bikeRide : bikeRides) {

            JsDateWrapper bikeRideDate = bikeRide.createJsDateWrapperRideStartTime();
            if (priorDate == null || !priorDate.isSameDay(bikeRideDate)) {
                if (header != null && contentList.size() > 0) {
                    //tableQuickLink is the value in the right quick link bar.  To place the header value simply place "header.getName()"
                    CellGroup<Header, Content> cellGroup = new GroupingCellList.StandardCellGroup<Header, Content>(tableQuickLink, header, contentList);
                    list.add(cellGroup);
                }
                priorDate = bikeRideDate;
                header = new Header(bikeRideDate.toString(ScreenConstants.DateFormatPrintPretty));
                contentList = new ArrayList<Content>();
            }

            //Build content of 1 ride
            if(bikeRide!= null) {
                contentList.add(new Content(bikeRide));
            }
        }

        //add the final item
        if (header != null && contentList.size() > 0) {
            //tableQuickLink is the value in the right quick link bar.  To place the header value simply place "header.getName()"
            CellGroup<Header, Content> cellGroup = new GroupingCellList.StandardCellGroup<Header, Content>(tableQuickLink, header, contentList);
            list.add(cellGroup);
        }

        return list;
    }

    private static class ContentCell implements ColoredCell<Content> {

        @Override
        public void render(SafeHtmlBuilder safeHtmlBuilder, Content model) {
            safeHtmlBuilder.append(model.getShortDescription());
        }

        @Override
        public boolean canBeSelected(Content model) {
            return true;
        }

        @Override
        public String getColorCss(Content model) {
            //todo: implement me!!

            String cssClass = model.getBikeRideListItemCssClass();
            Logger.getLogger("").log(Level.WARNING, "hi we made it! " + cssClass);
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

    private boolean adjustPullPanelSize() {
        if(headerListWidget==null || !headerListWidget.isAttached()) {
            return false;
        }

        int adjustedHeight = headerListWidget.getOffsetHeight();
        adjustedHeight -= footerFlowPanel.getOffsetHeight();
        adjustedHeight -= headerPanel.getOffsetHeight();

        if(adjustedHeight<=0) {
            return false;
        }
        headerListWidget.setHeight(adjustedHeight + "px");
        return true;
    }
}