package com.bikefunfinder.client.client.places.profilescreen;
/*
 * @author: lance poehler
 * @created 5/8/13 3:59 PM
 */

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.bootstrap.db.DBKeys;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.User;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfileScreenPlace extends Place {
    final User user;
    final AnonymousUser anonymousUser;
    final ClientFactory clientFactory = Injector.INSTANCE.getClientFactory();

    public ProfileScreenPlace() {
        //Set the logged in user details
        if (clientFactory.getStoredValue(DBKeys.USER) != null) {
            User user = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.USER));
            this.user = user;
            this.anonymousUser = null;
        } else if (clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER) != null) {
            AnonymousUser anonymousUser = Utils.castJsonTxtToJSOObject(clientFactory.getStoredValue(DBKeys.ANONYMOUS_USER));
            this.anonymousUser = anonymousUser;
            this.user = null;
        } else {
            this.anonymousUser = null;
            this.user = null;
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
            return new ProfileScreenPlace();
        }

        @Override
        public String getToken(ProfileScreenPlace place) {
            return null;
        }
    }
}
