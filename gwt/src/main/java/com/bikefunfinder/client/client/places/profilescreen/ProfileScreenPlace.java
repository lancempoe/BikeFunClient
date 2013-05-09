package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: lance poehler
 * @created 5/8/13 3:59 PM
 */

import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.User;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfileScreenPlace extends Place {
    final User user;
    final AnonymousUser anonymousUser;

    public ProfileScreenPlace(User user) {
        this.user = user;
        this.anonymousUser = null;
    }
    public ProfileScreenPlace(AnonymousUser anonymousUser) {
        this.anonymousUser = anonymousUser;
        this.user = null;
    }

    public User getUser() {
        return user;
    }
    public AnonymousUser getAnonymousUser() {
        return anonymousUser;
    }


    public static class Tokenizer implements PlaceTokenizer<ProfileScreenPlace> {

        @Override
        public ProfileScreenPlace getPlace(String token) {
            return new ProfileScreenPlace((User)null);
        }

        @Override
        public String getToken(ProfileScreenPlace place) {
            return null;
        }
    }
}
