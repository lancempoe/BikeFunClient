package com.bikefunfinder.client.shared.widgets;

import com.bikefunfinder.client.shared.model.BikeRide;

/**
 * Created with IntelliJ IDEA.
 * User: nathanfrost
 * Date: 3/16/13
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicCellSearchDetailImpl extends BasicCell<BikeRide> {

    @Override
    public String getDisplayString(BikeRide model) {
        String html = "<h1>" + model.bikeRideName + "</h1><h2>" + model.rideStartTime + "</h2><h2>" + model.details + "</h2>";
        return html;
    }

    @Override
    public boolean canBeSelected(BikeRide model) {
        return true;
    }
}
