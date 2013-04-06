package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 4:22 PM
 */

import com.google.gwt.user.client.ui.IsWidget;

public interface CreateScreenDisplay extends IsWidget {

    public void display();
    public void setPresenter(Presenter presenter);

    public interface Presenter {
    }
}
