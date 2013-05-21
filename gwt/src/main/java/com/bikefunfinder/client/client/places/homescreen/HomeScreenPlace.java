package com.bikefunfinder.client.client.places.homescreen;

import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class HomeScreenPlace extends Place {

    private final Root root;
    private final UsageEnum usage;

    public HomeScreenPlace() {
        this(null, UsageEnum.Default);
    }

    public HomeScreenPlace(Root root) {
        this(root, UsageEnum.Default);
    }

    public HomeScreenPlace(Root root, UsageEnum usage) {
        this.root = root;
        this.usage = usage;
    }

    public Root getRoot() {
        return root;
    }

    public UsageEnum getUsage() {
        return usage;
    }

    public enum UsageEnum {
        Default, ShowMyRides, FilterRides
    }

    public static class Tokenizer implements PlaceTokenizer<HomeScreenPlace> {

        @Override
        public HomeScreenPlace getPlace(String token) {
            return new HomeScreenPlace();
        }

        @Override
        public String getToken(HomeScreenPlace place) {
            return null;
        }
    }
}