package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: lance poehler
 * @created 5/8/13 3:59 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.bootstrap.db.DBKeys;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.User;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfileScreenPlace extends Place {
    final User user;
    final AnonymousUser anonymousUser;

    final ClientFactory clientFactory = Injector.INSTANCE.getClientFactory();

    public ProfileScreenPlace() {
        clientFactory.refreshUserAccount(); //Changes will not be visible until next view.

        //Set the logged in user details
        if (clientFactory.getStoredValue(DBKeys.USER) != null) {
            this.user = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.USER));
        } else {
            this.user = null;
        }

        if (clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER) != null) {
            this.anonymousUser = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER));
        } else {
            this.anonymousUser = null;
        }

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
            ProfileScreenPlace profileScreenPlace = new ProfileScreenPlace();
            if(profileScreenPlace.getAnonymousUser()!=null ||
               profileScreenPlace.getUser()!=null) {
                return profileScreenPlace;
            }

            return null;
        }

        @Override
        public String getToken(ProfileScreenPlace place) {
            return null;
        }
    }
}
