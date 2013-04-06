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
import com.bikefunfinder.client.client.places.homescreen.HomeScreenActivity;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenPlace;
import com.bikefunfinder.client.client.places.loginscreen.LoginScreenActivity;
import com.bikefunfinder.client.client.places.loginscreen.LoginScreenPlace;
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

        if(place instanceof HomeScreenPlace) {
            return new HomeScreenActivity(clientFactory);
        }
        if(place instanceof LoginScreenPlace) {
            return new LoginScreenActivity(clientFactory);
        }
        if(place instanceof CreateScreenPlace) {
            return new CreateScreenActivity(clientFactory);
        }

//        // to be nuked below
//        if (place instanceof OverviewPlace) {
//            return new OverviewActivity(clientFactory);
//        }
//        if (place instanceof AccelerometerPlace) {
//            return new AccelerometerActivity(clientFactory);
//        }
//
//        if (place instanceof CameraPlace) {
//            return new CameraActivity(clientFactory);
//        }
//
//        if (place instanceof CompassPlace) {
//            return new CompassActivity(clientFactory);
//        }
//
//        if (place instanceof ConnectionPlace) {
//            return new ConnectionActivity(clientFactory);
//        }
//
//        if (place instanceof ContactPlace) {
//            return new ContactActivity(clientFactory);
//        }
//
//        if (place instanceof DevicePlace) {
//            return new DeviceActivity(clientFactory);
//        }
//
//        if (place instanceof EventPlace) {
//            return new EventActivity(clientFactory);
//        }
//
//        if (place instanceof GeolocationPlace) {
//            return new GeolocationActivity(clientFactory);
//        }
//
//        if(place instanceof GMapPlace) {
//            return new GMapActivity(clientFactory);
//        }
//
//        if (place instanceof MediaPlace) {
//            return new MediaActivity(clientFactory);
//        }
//
//        if (place instanceof NotificationPlace) {
//            return new NotificationActivity(clientFactory);
//        }
//
//        if (place instanceof InAppBrowserPlace) {
//            return new InAppBrowserActivity(clientFactory);
//        }
//
//        if (place instanceof AboutPlace) {
//            return new AboutActivity(clientFactory);
//        }
//
//        if (place instanceof FilePlace) {
//            return new FileActivity(clientFactory);
//        }

        return null;

    }
}
