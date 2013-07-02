package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 4:22 PM
 */

import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.User;
import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileScreenDisplay extends IsWidget {

    /**
     * This are the items that the display can provide
     */
    public void displayFailedToLoadProfileMessage();
    public void display(User user);
    public void display(AnonymousUser anonymousUser);
    public void display(String profileNameText);

    /**
     * This is the contract for what the activity can provide
     */
    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public void backButtonSelected();
        public void onLoginButtonPressed();
        public void onShowMyRidesButton();
    }
}
