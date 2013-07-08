package com.bikefunfinder.client.shared.widgets.fullmenubar;

import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.bikefunfinder.client.shared.request.management.AnnonymousUserCacheStrategy;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Created with IntelliJ IDEA.
 * User: nathanfrost
 * Date: 7/7/13
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FullMenuBarDisplay extends IsWidget {
    public interface Presenter {
        public void onNewButton();
        public void onSearchButton();
        public void onLoginButton();
    }

    public void setTitle(String cityNameText);
}
