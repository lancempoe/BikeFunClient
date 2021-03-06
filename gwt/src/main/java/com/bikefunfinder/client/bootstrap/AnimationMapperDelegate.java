package com.bikefunfinder.client.bootstrap;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:54 AM
 */

import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.gmap.GMapPlace;
import com.bikefunfinder.client.client.places.gmaphomescreen.GMapHomePlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;
import com.googlecode.mgwt.ui.client.MGWT;

public class AnimationMapperDelegate implements AnimationMapper {

    @Override
    public Animation getAnimation(Place oldPlace, Place newPlace) {
        if (oldPlace == null || newPlace == null ) {
            return Animation.FADE;
        }

        //Create new events (and edit and event)
        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof CreateScreenPlace) {
            return Animation.POP;
        } else if (oldPlace instanceof GMapHomePlace && newPlace instanceof CreateScreenPlace) {
            return Animation.POP;
        } else if (oldPlace instanceof EventScreenPlace && newPlace instanceof CreateScreenPlace) {
            return Animation.POP;
        } else if (oldPlace instanceof CreateScreenPlace && newPlace instanceof HomeScreenPlace) {
            return Animation.POP_REVERSE;
        } else if (oldPlace instanceof CreateScreenPlace && newPlace instanceof GMapHomePlace) {
            return Animation.POP_REVERSE;
        } else if (oldPlace instanceof CreateScreenPlace && newPlace instanceof EventScreenPlace) {
            return Animation.FADE;
        }

        //Search Page
        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof SearchScreenPlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SWAP;
            }
            return Animation.FLIP;
        } else if (oldPlace instanceof GMapHomePlace && newPlace instanceof SearchScreenPlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SWAP;
            }
            return Animation.FLIP;
        } else if (oldPlace instanceof EventScreenPlace && newPlace instanceof SearchScreenPlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SWAP;
            }
            return Animation.FLIP;
        } else if (oldPlace instanceof SearchScreenPlace && newPlace instanceof HomeScreenPlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SWAP_REVERSE;
            }
            return Animation.FLIP_REVERSE;
        } else if (oldPlace instanceof SearchScreenPlace && newPlace instanceof GMapHomePlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SWAP_REVERSE;
            }
            return Animation.FLIP_REVERSE;
        } else if (oldPlace instanceof SearchScreenPlace && newPlace instanceof EventScreenPlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SWAP_REVERSE;
            }
            return Animation.FLIP_REVERSE;
        }

        //Profile Page.
        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof ProfileScreenPlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SLIDE_UP;
            }
            return Animation.FLIP;
        } else if (oldPlace instanceof GMapHomePlace && newPlace instanceof ProfileScreenPlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SLIDE_UP;
            }
            return Animation.FLIP;
        } else if (oldPlace instanceof EventScreenPlace && newPlace instanceof ProfileScreenPlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SLIDE_UP;
            }
            return Animation.FLIP;
        } else if (oldPlace instanceof ProfileScreenPlace && newPlace instanceof HomeScreenPlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SLIDE_UP_REVERSE;
            }
            return Animation.FLIP_REVERSE;
        } else if (oldPlace instanceof ProfileScreenPlace && newPlace instanceof GMapHomePlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SLIDE_UP_REVERSE;
            }
            return Animation.FLIP_REVERSE;
        } else if (oldPlace instanceof ProfileScreenPlace && newPlace instanceof EventScreenPlace) {
            if(MGWT.getOsDetection().isAndroid()) {
                return Animation.SLIDE_UP_REVERSE;
            }
            return Animation.FLIP_REVERSE;
        }

        //Event Page
        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof EventScreenPlace) {
            return Animation.SLIDE;
        } else if (oldPlace instanceof GMapHomePlace && newPlace instanceof EventScreenPlace) {
            return Animation.SLIDE;
        } else if (oldPlace instanceof EventScreenPlace && newPlace instanceof GMapPlace) {
            return Animation.SLIDE;
        } else if (oldPlace instanceof GMapPlace && newPlace instanceof EventScreenPlace) {
            return Animation.SLIDE_REVERSE;
        } else if (oldPlace instanceof EventScreenPlace && newPlace instanceof HomeScreenPlace) {
            return Animation.SLIDE_REVERSE;
        } else if (oldPlace instanceof EventScreenPlace && newPlace instanceof GMapHomePlace) {
            return Animation.SLIDE_REVERSE;
        }

        //Home Pages
        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof GMapHomePlace) {
            return Animation.FADE;
        } else if (oldPlace instanceof GMapHomePlace && newPlace instanceof HomeScreenPlace) {
            return Animation.FADE;
        }

        return Animation.SLIDE;
    }
}
