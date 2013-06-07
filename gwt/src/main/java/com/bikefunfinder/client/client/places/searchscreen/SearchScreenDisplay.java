package com.bikefunfinder.client.client.places.searchscreen;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:31 AM
 */

import com.bikefunfinder.client.shared.model.Query;
import com.google.gwt.user.client.ui.IsWidget;

public interface SearchScreenDisplay extends IsWidget {

    /**
     * This are the items that the display can provide
     */
    public void setUserPreferences();

    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public void backButtonSelected();
        public void searchRideButtonSelected(Query query);
    }
}