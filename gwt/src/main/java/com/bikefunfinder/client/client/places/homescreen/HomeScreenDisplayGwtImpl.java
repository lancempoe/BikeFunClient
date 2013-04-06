package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.widgets.BasicCellSearchDetailImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import com.bikefunfinder.client.shared.widgets.BasicCell;
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

    @UiField(provided = true)
    CellList<BikeRide> cellList;

    @UiField
    HTML cityName;

    @UiField(provided = true)
    ButtonBase addButton;

    @UiField(provided = true)
    ButtonBase searchButton;

    @UiField(provided = true)
    ButtonBase loginButton;

//    @UiField
//    ButtonBase aboutButton;

//    @UiField
//    Button hereAndNowBtn;
//
//    @UiField
//    Button timeAndDayBtn;
//    @UiField
//    TabPanel tabPanel;

//    @UiField
//    HTML content;

//    @UiField
//    BookmarkTabBarButton bookmarkTabButton;

//    @UiField
//    FlowPanel fp;

    @UiField
    Button hereAndNowButton;

    @UiField
    Button timeAndDayButton;

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


        addButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarMostRecentImage());
        searchButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarSearchImage());
        loginButton = new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarContactsImage());

//        tabPanel.add(new TabBarButton(MGWTStyle.getTheme().getMGWTClientBundle().tabBarBookMarkImage()), new HTML("stuff"));


//        HTMLPanel panel = new HTMLPanel("stuff");
//        tabPanel.add(new DownloadsTabBarButton(), panel);
//        hereAndNowBtn.setSmall(true);
//        timeAndDayBtn.setSmall(true);
        initWidget(uiBinder.createAndBindUi(this));

//        Window.alert(MGWTStyle.getTheme().getMGWTClientBundle().tabBarBookMarkImage().getSafeUri().asString());
//        Image img = GWT.create(Image.class);
//        img.setUrl(MGWTStyle.getTheme().getMGWTClientBundle().tabBarBookMarkImage().getSafeUri());
        //hp.add(img);



//        VerticalPanel firstButton = new VerticalPanel();
//        firstButton.add(img);
//        final HTML w = new HTML("Here and Now");
//        firstButton.add(w);
//        fp.add(firstButton);
//
//        VerticalPanel secondButton = new VerticalPanel();
//        secondButton.add(img);
//        final HTML w2 = new HTML("Time and day");
//        secondButton.add(w2);
//        fp.add(secondButton);


//        LayoutPanel optionButtons = new LayoutPanel();
//        optionButtons.setHorizontal(true);
//        Button hereAndNowButton = new Button("Here and now");
//        hereAndNowButton.setSmall(true);
//        Button timeAndDay = new Button("Time and day");
//        timeAndDay.setSmall(true);
//        optionButtons.add(hereAndNowButton);
//        optionButtons.add(timeAndDay);
//        scopeButtons.add(optionButtons);
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

//    @UiHandler("aboutButton")
//    protected void onAboutButton(TapEvent event) {
//        if (presenter != null) {
//            presenter.onAboutButton();
//        }
//    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }

}
