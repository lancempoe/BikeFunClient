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

import com.bikefunfinder.client.client.places.createscreen.CreateScreenActivity;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.editscreen.EditScreenActivity;
import com.bikefunfinder.client.client.places.editscreen.EditScreenPlace;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenActivity;
import com.bikefunfinder.client.client.places.eventscreen.EventScreenPlace;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenActivity;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.client.places.loginscreen.LoginScreenActivity;
import com.bikefunfinder.client.client.places.loginscreen.LoginScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenActivity;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.usereventsscreen.UserEventsScreenActivity;
import com.bikefunfinder.client.client.places.usereventsscreen.UserEventsScreenPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.bikefunfinder.client.bootstrap.ClientFactory;

class PhoneActivityMapper implements ActivityMapper {

    private final ClientFactory clientFactory;

    public PhoneActivityMapper(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {

        if(place instanceof CreateScreenPlace) {
            return new CreateScreenActivity(clientFactory);
        }
        if(place instanceof EditScreenPlace) {
            return new EditScreenActivity(clientFactory);
        }
        if(place instanceof EventScreenPlace) {
            return new EventScreenActivity(clientFactory);
        }
        if(place instanceof HomeScreenPlace) {
            return new HomeScreenActivity(clientFactory);
        }
        if(place instanceof LoginScreenPlace) {
            return new LoginScreenActivity(clientFactory);
        }
        if(place instanceof ProfileScreenPlace) {
            return new ProfileScreenActivity(clientFactory);
        }
        if(place instanceof UserEventsScreenPlace) {
            return new UserEventsScreenActivity(clientFactory);
        }


        return null;

    }
}
