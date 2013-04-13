package com.bikefunfinder.client.bootstrap.tablet;

import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

class TabletMainAnimationMapper implements AnimationMapper {

    final AnimationMapper animationMapperDelegate;

    TabletMainAnimationMapper(AnimationMapper animationMapperDelegate) {
        this.animationMapperDelegate = animationMapperDelegate;
    }

    @Override
    public Animation getAnimation(Place oldPlace, Place newPlace) {
        return animationMapperDelegate.getAnimation(oldPlace, newPlace);
    }

}
