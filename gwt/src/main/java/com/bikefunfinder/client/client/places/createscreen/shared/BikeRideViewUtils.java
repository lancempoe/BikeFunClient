package com.bikefunfinder.client.client.places.createscreen.shared;
/*
 * @author: tneuwerth
 * @created 5/8/13 9:51 PM
 */

import com.bikefunfinder.client.shared.model.Tracking;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.WidgetList;

public class BikeRideViewUtils {

    public static Widget buildFormWidget(String title, Widget widget) {
        FormListEntry formListEntry = new FormListEntry();
        formListEntry.setText(title);
        formListEntry.add(widget);
        return formListEntry;
    }


    //todo: wtf man, this be a mess.. made for the sake.. of SCIENCE! haha no, I just did some bullshit.
    public static
        <U extends BikeRideCreateWidgets>

        WidgetList buildWidgetListWithLabels(final String peopleTracking,
                                             final String someoneIsTracking,
                                             final String bikeRideNames,
                                             final String rideLeaderName,
                                             final String targetAudience,
                                             final String startingAddress1,
                                             final String distanceFromTheStart,
                                             final String startTime,
                                             final String rideDetails,
                                             final U u) {
        WidgetList newWidgetList = new WidgetList();

        boolean isView = u instanceof BikeRideViewWidgets;
        if(isView) {
            newWidgetList.add(buildFormWidget(peopleTracking, ((BikeRideViewWidgets)u).totalPeopleTrackingCount));
            newWidgetList.add(buildFormWidget(someoneIsTracking, ((BikeRideViewWidgets)u).currentlyTracking));
        } else {
            newWidgetList.add(buildFormWidget("Tracking allowed", u.trackingAllowed));
        }

        newWidgetList.add(buildFormWidget(bikeRideNames, u.bikeRideName));
        newWidgetList.add(buildFormWidget(targetAudience, u.targetAudience));

        if(isView) {
            newWidgetList.add(buildFormWidget(rideLeaderName, u.rideLeaderName));
            newWidgetList.add(buildFormWidget(startingAddress1, ((BikeRideViewWidgets)u).formattedAddress));
            newWidgetList.add(buildFormWidget(distanceFromTheStart, ((BikeRideViewWidgets)u).distanceFromClient));
        } else {
            newWidgetList.add(buildFormWidget("Starting Address:", u.locationAddress));
            newWidgetList.add(buildFormWidget("Starting City:", u.locationCity));
            newWidgetList.add(buildFormWidget("Starting State:", u.locationState));
            newWidgetList.add(buildFormWidget("Starting Date:", u.startDate));
            newWidgetList.add(buildFormWidget(startTime, u.startTime));
        }
        newWidgetList.add(buildFormWidget(rideDetails, u.details));
        return newWidgetList;
    }

    public static WidgetList buildBikeViewWidgitList(BikeRideViewWidgets viewWidgets) {

        WidgetList newWidgetList = buildWidgetListWithLabels("Historical Tracking Count:",
        "Someone is Tracking:",
        "Bike Ride Name:",
        "Ride Leader Name:",
        "Target Audience:",
        "Starting Address:",
        "Distance from the Start:",
        "Start Time:",
        "Ride Details:", viewWidgets);

        return newWidgetList;
    }

    public static WidgetList buildBikeViewWidgitList(BikeRideCreateWidgets viewWidgets) {
        WidgetList newWidgetList = buildWidgetListWithLabels(null,
                null,
                "Bike Ride Name:",
                null,
                "Target Audience:",
                null,
                null,
                "Starting Time:",
                "Ride Details:",
                viewWidgets);
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
