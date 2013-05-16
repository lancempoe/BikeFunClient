package com.bikefunfinder.client.bootstrap;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:54 AM
 */

import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;
import com.bikefunfinder.client.client.places.gmap.*;

public class AnimationMapperDelegate implements AnimationMapper {

    @Override
    public Animation getAnimation(Place oldPlace, Place newPlace) {
        if (oldPlace == null || newPlace == null ) {
            return Animation.FADE;
        }

        //Create new events
        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof CreateScreenPlace) {
            return Animation.POP;
        } else if (oldPlace instanceof EventScreenPlace && newPlace instanceof CreateScreenPlace) {
            return Animation.POP;
        } else if (oldPlace instanceof CreateScreenPlace && newPlace instanceof HomeScreenPlace) {
            return Animation.POP_REVERSE;
        } else if (oldPlace instanceof CreateScreenPlace && newPlace instanceof EventScreenPlace) {
            return Animation.FADE;
        }  else if (oldPlace instanceof EventScreenPlace && newPlace instanceof GMapPlace) {
            return Animation.SLIDE;
        }

        //Search Page
        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof SearchScreenPlace) {
            return Animation.FLIP;
        } else if (oldPlace instanceof EventScreenPlace && newPlace instanceof SearchScreenPlace) {
            return Animation.FLIP;
        } else if (oldPlace instanceof SearchScreenPlace && newPlace instanceof HomeScreenPlace) {
            return Animation.FLIP_REVERSE;
        }  else if (oldPlace instanceof SearchScreenPlace && newPlace instanceof EventScreenPlace) {
            return Animation.FLIP_REVERSE;
        }

        //Profile Page.
        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof ProfileScreenPlace) {
            return Animation.FLIP;
        } else if (oldPlace instanceof EventScreenPlace && newPlace instanceof ProfileScreenPlace) {
            return Animation.FLIP;
        } else if (oldPlace instanceof ProfileScreenPlace && newPlace instanceof HomeScreenPlace) {
            return Animation.FLIP_REVERSE;
        }  else if (oldPlace instanceof ProfileScreenPlace && newPlace instanceof EventScreenPlace) {
            return Animation.FLIP_REVERSE;
        }

        //Event Page
        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof EventScreenPlace) {
            return Animation.SLIDE;
        } else if (oldPlace instanceof EventScreenPlace && newPlace instanceof HomeScreenPlace) {
            return Animation.SLIDE_REVERSE;
        }

        //Here and now page
        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof GMapPlace) {
            return Animation.SLIDE;
        } else if (oldPlace instanceof GMapPlace && newPlace instanceof HomeScreenPlace) {
            return Animation.SLIDE_REVERSE;
        } else if (oldPlace instanceof GMapPlace && newPlace instanceof EventScreenPlace) {
            return Animation.SLIDE_REVERSE;
        }

        return Animation.SLIDE;
    }
}
