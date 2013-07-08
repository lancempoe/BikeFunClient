package com.bikefunfinder.client.shared.widgets.fullmenubar;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.impl.ImageResourcePrototype;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.base.ButtonBase;
import com.googlecode.mgwt.ui.client.widget.tabbar.TabBarButton;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: nathanfrost
 * Date: 7/7/13
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class FullMenuBarDisplayGwtImpl extends Composite implements FullMenuBarDisplay {

    @UiField
    HTML cityName;

    @UiField(provided = true)
    ButtonBase addButton;

    @UiField(provided = true)
    ButtonBase searchButton;

    @UiField(provided = true)
    ButtonBase loginButton;

    private FullMenuBarDisplay.Presenter presenter;



    private static FullMenuBarDisplayGwtImplUiBinder uiBinder = GWT.create(FullMenuBarDisplayGwtImplUiBinder.class);

    interface FullMenuBarDisplayGwtImplUiBinder extends UiBinder<Widget, FullMenuBarDisplayGwtImpl> {
    }

    public FullMenuBarDisplayGwtImpl()
    {
        Logger.getLogger("").log(Level.INFO, "Ok! we are in!");
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

        Logger.getLogger("").log(Level.INFO, "Close!");
        try{
            initWidget(uiBinder.createAndBindUi(this));
        }
        catch(Exception e) {
            Logger.getLogger("").log(Level.SEVERE, "Uck! " + e.getMessage() + ":::::::::: " + e.toString() + " :::::::: " + e.getStackTrace());
        }
        Logger.getLogger("").log(Level.INFO, "YESYES!!");

        addButton.addStyleName("menuButton");
        searchButton.addStyleName("menuButton");
        loginButton.addStyleName("menuButton");

        presenter = new FullMenuBarActivity();

        addButton.addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                presenter.onNewButton();
            }
        });

        searchButton.addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                presenter.onSearchButton();
            }
        });

        loginButton.addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                presenter.onLoginButton();
            }
        });
    }

    public void setTitle(String cityNameText) {
        cityName.setText(cityNameText);
    }
}
