/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.bikefunfinder.client.bootstrap.phone;

import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

/**
 * @author Daniel Kurka
 */
class PhoneAnimationMapper implements AnimationMapper {

    final AnimationMapper animationMapperDelegate;

    PhoneAnimationMapper(AnimationMapper animationMapperDelegate) {
        this.animationMapperDelegate = animationMapperDelegate;
    }

    @Override
    public Animation getAnimation(Place oldPlace, Place newPlace) {
        return animationMapperDelegate.getAnimation(oldPlace, newPlace);
    }
}
