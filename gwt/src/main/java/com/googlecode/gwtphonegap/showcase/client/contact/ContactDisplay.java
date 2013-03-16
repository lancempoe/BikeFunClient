package com.googlecode.gwtphonegap.showcase.client.contact;

import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.gwtphonegap.showcase.client.model.DemoContact;

import java.util.List;

public interface ContactDisplay extends IsWidget {
    public void setPresenter(Presenter presenter);

    public interface Presenter {

        public void onBackButtonPressed();

        public void onSearchTermEntered(String term);

    }

    public void display(List<DemoContact> contacts);

}
