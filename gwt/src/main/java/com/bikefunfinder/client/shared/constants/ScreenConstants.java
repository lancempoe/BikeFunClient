package com.bikefunfinder.client.shared.constants;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/25/13
 * Time: 7:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScreenConstants {

    public static enum TargetAudience {
        ALL_AGES("All Ages"),
        ROADIES("Roadies"),
        BOOZY("Boozy"),
        NAKED("Naked"),
        BOOZY_AND_NAKED("Boozy and Naked");

        private final String displayName;
        TargetAudience(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
