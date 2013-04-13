package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 4:22 PM
 */

import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileScreenDisplay extends IsWidget {

    public void display();
    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public void backButtonSelected();
        public void onLoginButtonPressed();
    }
}
