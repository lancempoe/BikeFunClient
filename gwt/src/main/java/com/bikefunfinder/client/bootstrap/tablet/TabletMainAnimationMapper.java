package com.bikefunfinder.client.bootstrap.tablet;

import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

class TabletMainAnimationMapper implements AnimationMapper {

    @Override
    public Animation getAnimation(Place oldPlace, Place newPlace) {
        if (oldPlace == null || newPlace == null ) {
            return Animation.FADE;
        }

        if (oldPlace instanceof HomeScreenPlace && newPlace instanceof CreateScreenPlace) {
            return Animation.SLIDE;
        }
        if (oldPlace instanceof CreateScreenPlace && newPlace instanceof HomeScreenPlace) {
            return Animation.SLIDE_REVERSE;
        }
        return Animation.SLIDE;
    }

}
