package com.bikefunfinder.client.client.places.eventscreen.widgets;
/*
 * @author: tneuwerth
 * @created 5/8/13 9:51 PM
 */

import com.bikefunfinder.client.client.places.createscreen.widgets.BikeRideCreateWidgets;
import com.bikefunfinder.client.shared.model.Tracking;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.WidgetList;

public class BikeRideViewUtils {

    public enum ViewWidgetLabels {
        bikeRideNames("Bike Ride Name:"),
        peopleTracking("Historical Tracking Count:"),
        someoneIsTracking("Someone is Tracking:"),
        rideLeaderName("Ride Leader Name:"),
        targetAudience("Target Audience:"),
        startingAddress1("Starting Address:"),
        distanceFromTheStart("Distance from the Event:"),
        rideDetails("Ride Details:"),
        startDateAndTime("Event Date:");

        final String labelText;
        ViewWidgetLabels(String labelText) {
            this.labelText = labelText;
        }
    }


    public enum CreateWidgetLabels {
        bikeRideNames("Bike Ride Name:"),
        targetAudience("Target Audience:"),
        trackingAllowed("Tracking:"),
        startingAddress("Starting Address:"),
        startingCity("Starting City:"),
        startingState("Starting State:"),
        startingDate("Starting Date:"),
        startTime("Start Time:"),
        rideDetails("Ride Details:");

        final String labelText;
        CreateWidgetLabels(String labelText) {
            this.labelText = labelText;
        }
    }
    public static Widget buildFormWidget(String title, Widget widget) {
        FormListEntry formListEntry = new FormListEntry();
        formListEntry.setText(title);
        formListEntry.add(widget);
        return formListEntry;
    }

    public static
        WidgetList buildWidgetListWithLabels(final BikeRideCreateWidgets u) {
        WidgetList newWidgetList = new WidgetList();

        newWidgetList.add(buildFormWidget(CreateWidgetLabels.bikeRideNames.labelText, u.getBikeRideName()));
        newWidgetList.add(buildFormWidget(CreateWidgetLabels.trackingAllowed.labelText, u.getTrackingAllowed()));
        newWidgetList.add(buildFormWidget(CreateWidgetLabels.targetAudience.labelText, u.getTargetAudience()));
        newWidgetList.add(buildFormWidget(CreateWidgetLabels.startingAddress.labelText, u.getLocationAddress()));
        newWidgetList.add(buildFormWidget(CreateWidgetLabels.startingCity.labelText, u.getLocationCity()));
        newWidgetList.add(buildFormWidget(CreateWidgetLabels.startingState.labelText, u.getLocationState()));
        newWidgetList.add(buildFormWidget(CreateWidgetLabels.startingDate.labelText, u.getStartDate()));
        newWidgetList.add(buildFormWidget(CreateWidgetLabels.startTime.labelText, u.getStartTime()));
        newWidgetList.add(buildFormWidget(CreateWidgetLabels.rideDetails.labelText, u.getDetails()));
        return newWidgetList;
    }

    public static
    WidgetList buildWidgetListWithLabels(final BikeRideViewWidgets viewWidgets) {
        WidgetList newWidgetList = new WidgetList();

        newWidgetList.add(buildFormWidget(ViewWidgetLabels.bikeRideNames.labelText, viewWidgets.getBikeRideName()));
        newWidgetList.add(buildFormWidget(ViewWidgetLabels.targetAudience.labelText, viewWidgets.getTargetAudience()));
        newWidgetList.add(buildFormWidget(ViewWidgetLabels.startingAddress1.labelText, viewWidgets.getFormattedAddress()));
        newWidgetList.add(buildFormWidget(ViewWidgetLabels.startDateAndTime.labelText, viewWidgets.getStartDateAndTime()));
        newWidgetList.add(buildFormWidget(ViewWidgetLabels.distanceFromTheStart.labelText, viewWidgets.getDistanceFromClient()));
        newWidgetList.add(buildFormWidget(ViewWidgetLabels.rideLeaderName.labelText, viewWidgets.getRideLeaderName()));
        newWidgetList.add(buildFormWidget(ViewWidgetLabels.peopleTracking.labelText, viewWidgets.getTotalPeopleTrackingCount()));
        newWidgetList.add(buildFormWidget(ViewWidgetLabels.someoneIsTracking.labelText, viewWidgets.getCurrentlyTracking()));
        newWidgetList.add(buildFormWidget(ViewWidgetLabels.rideDetails.labelText, viewWidgets.getDetails()));
        return newWidgetList;
    }


    public static WidgetList buildBikeViewWidgitList(BikeRideViewWidgets viewWidgets) {

        WidgetList newWidgetList = buildWidgetListWithLabels(viewWidgets);

        return newWidgetList;
    }

    public static WidgetList buildBikeViewWidgitList(BikeRideCreateWidgets viewWidgets) {
        WidgetList newWidgetList = buildWidgetListWithLabels(viewWidgets);
        return newWidgetList;
    }

    public static SafeHtml buildOrderListHtmlForTracking(JsArray<Tracking> trackings) {
        final SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.appendHtmlConstant("<ol>");

        if(trackings!=null && trackings.length()>0) {
            final int numTrackings = trackings.length();

            for(int index=0; index< numTrackings; index++) {
                Tracking tracking = trackings.get(index);
                if(tracking!=null && tracking.getTrackingUserName()!=null) {
                    safeHtmlBuilder.appendHtmlConstant("<li>")
                            .appendEscaped(tracking.getTrackingUserName())
                            .appendHtmlConstant("</li>");
                }
            }
        }
        safeHtmlBuilder.appendHtmlConstant("</ol>");
        return safeHtmlBuilder.toSafeHtml();
    }
}
