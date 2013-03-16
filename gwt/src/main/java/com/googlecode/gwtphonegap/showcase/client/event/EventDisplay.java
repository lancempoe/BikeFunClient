package com.googlecode.gwtphonegap.showcase.client.event;

import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.gwtphonegap.showcase.client.model.EventDemo;

import java.util.LinkedList;

public interface EventDisplay extends IsWidget {
    public void render(LinkedList<EventDemo> list);

    public void setPresenter(Presenter presenter);

    public interface Presenter {

        public void onBackButtonPressed();

    }

}
