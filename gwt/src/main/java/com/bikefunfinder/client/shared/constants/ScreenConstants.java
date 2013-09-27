package com.bikefunfinder.client.shared.constants;

import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.google.gwt.i18n.client.NumberFormat;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/25/13
 * Time: 7:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScreenConstants {

    public static String TargetAudienceLabel = "- Filter By Type -";
    public static enum TargetAudience {
        FAMILY_FRIENDLY("Family Friendly", 0),
        BIKE_TALK("Bike Talk", 1),
        BOOZY("Boozy", 2),
        COSTUMES("Costumes", 3),
        EASY("Easy Riders", 4),
        EDUCATIONAL("Educational", 5),
        FAST("Fast & Furious", 6),
        FOODIE("Foodie", 7),
        MEET_UP("Meet Up", 8),
        NAKED("Naked", 9),
        OPEN_HOUSE("Open House", 10),
        ROADIES("Roadies", 11);

        private final String displayName;
        private final int orderCount;
        TargetAudience(String displayName, int orderCount) {
            this.displayName = displayName;
            this.orderCount = orderCount;
        }

        public String getDisplayName() {
            return displayName;
        }

        public int getOrderCount() {
            return orderCount;
        }
    }

    public static enum TargetIcon {
        CLIENT("eventClient.png"),
        EVENT("event.png"),
        LEADER("eventLeader.png"),
        LOGO("logo.png"),
        TRACKER("eventTracker.png");

        private final String displayName;
        TargetIcon(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private static final String DateFormat = "MM/dd/yyyy";
    private static final String TimeFormat = "hh:mm a";
    public static final String gwtDateAndTimeCombined = ScreenConstants.DateFormat + " " + ScreenConstants.TimeFormat;

    public static final String xJSDateOnlyTimeFormat = "hh:mm TT";
    public static final String xJSDateOnlyDateFormat = "MM/dd/yyyy";

    public static final String DateFormatPrintPretty = "dddd, MMMM dd, yyyy";
    public static final String DateFormatForGmapsWidget = "dddd, MMMM dd";
    public static final String TimeFormatPrintPretty = "h:mmtt";

    public static final double PORTLAND_LATITUDE = 45.52345275878906;
    public static final double PORTLAND_LONGITUDE = -122.6762084960938;
    public static final int SCREEN_REFRESH_RATE_IN_MILLISECONDS = 30000;
    public static final int TIME_TO_WARN_USER_OF_TRACKING_DISABLING_IN_MILLIS = 60*60*1000; //1 hour
    public static final int MAX_TRACKING_WITHOUT_CONFORMATION_IN_MILLISECONDS = TIME_TO_WARN_USER_OF_TRACKING_DISABLING_IN_MILLIS *2; //2 hours

    public static final NumberFormat DISTANCE_FORMAT = NumberFormat.getFormat("#.#");
    public static final String RegularExpression_City = "^((?!,).)+(?=,)";

    public static final double MinimumTimeBeforeTrackingAllowed = 1800000; //30 minutes
    public static final double MaximumTimeAfterTrackingAllowed = 86400000;  //24 hours

    public static final double TimeOffsetBeforeItsCurrent = JsDateWrapper.getHours(1);
    public static final double TimeOffsetBeforeItsLeaving = JsDateWrapper.getHours(0);
    public static final double TimeOffsetBeforeItsOld = JsDateWrapper.getMinutes(-30);

    public static final String YOU = "This is you.";
    public static final String YOU_LEADING = YOU + " Your leading the ride!";
    public static final String YOU_LEADER = YOU + " Start sharing ride location!";
    public static final String STARTING_POINT_EVENT_MOVED = "See golden bike on map for current location.";
    public static final String LIVE_TRACK = "Actively Tracking!";
}
