package com.bikefunfinder.client.client.places.searchsettings;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:31 AM
 */

import com.google.gwt.user.client.ui.IsWidget;

public interface SearchSettingsScreenDisplay extends IsWidget {

    public void display();
    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public void backButtonSelected();
    }
}