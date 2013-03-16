package com.googlecode.gwtphonegap.showcase.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.gwtphonegap.showcase.client.model.PGModule;

import java.util.List;

public interface OverviewDisplay extends IsWidget {

    public void display(List<PGModule> list);

    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public void onCellSelected(int index);

        public void onAboutButton();
    }

}
