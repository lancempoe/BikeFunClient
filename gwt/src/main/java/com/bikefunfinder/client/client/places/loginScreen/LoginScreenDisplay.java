package com.bikefunfinder.client.client.places.loginscreen;
/*
 * @author: tneuwerth
 * @created 4/4/13 10:55 AM
 */

import com.google.gwt.user.client.ui.IsWidget;

public interface LoginScreenDisplay extends IsWidget {

    public void display();
    public void setPresenter(Presenter presenter);

    public interface Presenter {
    }
}