package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;

public interface HomeScreenDisplay extends IsWidget {

    public void display(List<BikeRide> list);
    public void display(String cityNameText);
    public void setPresenter(Presenter presenter);

    public interface Presenter {
        public void onCellSelected(int index);

        public void onAboutButton();
    }
}
