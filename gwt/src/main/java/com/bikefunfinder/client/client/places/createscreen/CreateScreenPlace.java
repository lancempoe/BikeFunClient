package com.bikefunfinder.client.client.places.createscreen;
/*
 * @author: tneuwerth
 * @created 4/5/13 3:59 PM
 */

import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.User;
import com.bikefunfinder.client.shared.request.management.AnnonymousUserCacheStrategy;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CreateScreenPlace extends Place {

    private final BikeRide bikeRide;
    private final AnonymousUser anonymousUser;
    private final User user;
    private final boolean isCopied;

    public CreateScreenPlace(BikeRide bikeRide, Boolean isCopied, User user, AnonymousUser anonymousUser) {
        this.user = user;
        this.anonymousUser = anonymousUser;
        this.bikeRide = bikeRide;
        this.isCopied = isCopied;
    }

    public CreateScreenPlace(BikeRide bikeRide, User user, AnonymousUser anonymousUser) {
        this.user = user;
        this.anonymousUser = anonymousUser;
        this.bikeRide = bikeRide;
        this.isCopied = false;
    }

    public AnonymousUser getAnonymousUser() {
        return anonymousUser;
    }

    public User getUser() {
        return user;
    }

    public BikeRide getBikeRide() {
        return bikeRide;
    }

    public boolean getIsCopied() {
        return isCopied;
    }

    public static class Tokenizer implements PlaceTokenizer<CreateScreenPlace> {

        @Override
        public CreateScreenPlace getPlace(String token) {
            AnonymousUser anonymousUser = AnnonymousUserCacheStrategy.INSTANCE.getCachedType();
            return new CreateScreenPlace(null, null, anonymousUser);
        }

        @Override
        public String getToken(CreateScreenPlace place) {
            return null;
        }
    }
}