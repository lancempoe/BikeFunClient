package com.bikefunfinder.client.shared.constants;

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
        ROADIES("Roadies", 1),
        BOOZY("Boozy", 2),
        NAKED("Naked", 3),
        BOOZY_AND_NAKED("Boozy and Naked", 4);

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

    public static final String DateFormat = "mm/dd/yyyy";
    public static final String TimeFormat = "h:mm a";
    public static final String DateFormatPrintPretty = "dddd, MMMM dd, yyyy";
    public static final String TimeFormatPrintPretty = "h:mm tt";

    public static final Double PORTLAND_LATITUDE = 45.52345275878906;
    public static final Double PORTLAND_LONGITUDE = -122.6762084960938;

    public static final NumberFormat DISTANCE_FORMAT = NumberFormat.getFormat(".##");
}
