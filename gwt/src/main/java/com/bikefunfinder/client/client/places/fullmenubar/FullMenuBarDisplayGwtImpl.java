package com.bikefunfinder.client.client.places.fullmenubar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.impl.ImageResourcePrototype;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.base.ButtonBase;
import com.googlecode.mgwt.ui.client.widget.tabbar.TabBarButton;

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


    private static FullMenuScreenDisplayGwtImplUiBinder uiBinder = GWT.create(FullMenuScreenDisplayGwtImplUiBinder.class);

    interface FullMenuScreenDisplayGwtImplUiBinder extends UiBinder<Widget, FullMenuBarDisplayGwtImpl> {
    }

    public FullMenuBarDisplayGwtImpl()
    {
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

        initWidget(uiBinder.createAndBindUi(this));

        addButton.addStyleName("menuButton");
        searchButton.addStyleName("menuButton");
        loginButton.addStyleName("menuButton");
    }

    public void setTitle(String cityNameText) {
        cityName.setText(cityNameText);
    }
}
