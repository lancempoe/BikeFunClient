package com.bikefunfinder.client.bootstrap;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:54 AM
 */

import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchsettings.SearchSettingsScreenPlace;
import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

public class AnimationMapperDelegate implements AnimationMapper {

    @Override
    public Animation getAnimation(Place oldPlace, Place newPlace) {
        if (oldPlace == null || newPlace == null ) {
            return Animation.FADE;
        }

        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof CreateScreenPlace) {
            return Animation.SLIDE;
        } else if (oldPlace instanceof CreateScreenPlace && newPlace instanceof HomeScreenPlace) {
            return Animation.SLIDE_REVERSE;
        }

        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof ProfileScreenPlace) {
            return Animation.SLIDE;
        } else if (oldPlace instanceof ProfileScreenPlace && newPlace instanceof HomeScreenPlace) {
            return Animation.SLIDE_REVERSE;
        }

        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof SearchSettingsScreenPlace) {
            return Animation.FLIP;
        } else  if (oldPlace instanceof SearchSettingsScreenPlace && newPlace instanceof HomeScreenPlace) {
            return Animation.FLIP_REVERSE;
        }


        return Animation.SLIDE;
    }
}
