package com.bikefunfinder.client.shared.Tools;

import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.gmap.GMapPlace;
import com.bikefunfinder.client.client.places.gmaphomescreen.GMapHomePlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 7/31/13
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class NavigationHelper {

    private static Stack<Place> priorPlace = new Stack<Place>(); //Used for the back button.
    private static Stack<Place> Place = new Stack<Place>(); //Used for the back button.
    private static Map<HomeTypeEnum, Place> homePlaces = new HashMap<HomeTypeEnum, Place>();
    private static enum HomeTypeEnum {
        HOME, GMAP_HOME;
    }

    public static void setHomeScreenPlace(Place place) {
        homePlaces.put(HomeTypeEnum.HOME, place);
    }
    public static void setGMapHomePlace(Place place) {
        homePlaces.put(HomeTypeEnum.GMAP_HOME, place);
    }

    public static Place getHomeScreenPlace() {
        if (!homePlaces.containsKey(HomeTypeEnum.HOME)) {
            Place homePlace = new HomeScreenPlace();
            setHomeScreenPlace(homePlace);
        }
        return homePlaces.get(HomeTypeEnum.HOME);
    }

    public static Place getGMapHomePlace() {
        if (!homePlaces.containsKey(HomeTypeEnum.GMAP_HOME)) {
            Place homePlace = new GMapHomePlace();
            setGMapHomePlace(homePlace);
        }
        return homePlaces.get(HomeTypeEnum.GMAP_HOME);
    }

    public static void goToPriorScreen(PlaceController controller) {
        goToPriorScreen(controller, true);
    }

    private static void goToPriorScreen(PlaceController controller, boolean removeCurrentPlace) {
        Place priorPlace = getPriorPlace(removeCurrentPlace);

        if (priorPlace instanceof HomeScreenPlace) {
            controller.goTo((HomeScreenPlace)priorPlace);
        } else if (priorPlace instanceof GMapHomePlace) {
            controller.goTo((GMapHomePlace)priorPlace);
        } else if (priorPlace instanceof GMapPlace) {
            controller.goTo((GMapPlace)priorPlace);
        } else if (priorPlace instanceof EventScreenPlace) {
            controller.goTo((EventScreenPlace)priorPlace);
        } else if (priorPlace instanceof CreateScreenPlace) {
            //Go back 1 more screen
            goToPriorScreen(controller, false);
        } else {
            controller.goTo(getHomeScreenPlace());
        }
    }

    private static Place getPriorPlace(boolean removeCurrentPlace) {
        //Remove the current page from the stack
        if (removeCurrentPlace && !priorPlace.empty()) {
            priorPlace.pop();
        }

        //Return the prior screen from the stack
        if (!priorPlace.empty()) {
            return priorPlace.pop();
        }
        return null;
    }

    public static Place getCurrentPlace() {
        return priorPlace.peek();
    }

    public static boolean isNavigationStackEmpty() {
        return priorPlace.empty();
    }

    public static void pushCurrentPlace(Place place) {
        priorPlace.push(place);
    }

    public static void clearNavigationStack() {
        priorPlace = new Stack<Place>();
    }

}
