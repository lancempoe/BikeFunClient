package com.bikefunfinder.client.shared.Tools;

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 8/6/13
 * Time: 7:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomeHelper {

    public static String getHomeTitle(Root root) {
        String title;
        if (root.getBikeRides() != null &&
                root.getBikeRides().length() > 0 &&
                root.getClosestLocation() != null)  {
            MatchResult matcher = HomeHelper.getCityStateFromAddress(root.getClosestLocation().getFormattedAddress());
            boolean matchFound = (matcher != null); // equivalent to regExp.test(inputStr);
            if (matchFound) {
                title = matcher.getGroup(0);
            } else {
                if (root.getClosestLocation() != null &&
                        root.getClosestLocation().getFormattedAddress() != null) {
                    title = "Unknown City";
                } else {
                    title = "Search Results";
                }
            }
        }  else {
            title = "Add an Event!";
        }
        return title;
    }

    private static MatchResult getCityStateFromAddress(String formattedAddress) {
        RegExp regExp = RegExp.compile(ScreenConstants.RegularExpression_City);
        return regExp.exec(formattedAddress);
    }

}
