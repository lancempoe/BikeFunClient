package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 12:57 PM
 */

import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.request.converters.PayloadConverters;
import com.bikefunfinder.client.shared.request.ratsnest.*;
import com.google.gwt.http.client.*;

import java.math.BigDecimal;

public final class SearchByTimeOfDayForProfileRequest {

    public static final class Builder {
        private WebServiceResponseConsumer<Root> callback;
        private BigDecimal longitude;
        private BigDecimal latitude;
        private String rideLeaderId;

        public Builder(final WebServiceResponseConsumer<Root> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final WebServiceResponseConsumer<Root> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
            return this;
        }

        public Builder rideLeaderId(final String rideLeaderId) {
            this.rideLeaderId = rideLeaderId;
            return this;
        }
        public Builder latitude(final GeoLoc geoLoc) {
            this.latitude = new BigDecimal(geoLoc.getLatitude());
            return this;
        }

        public Builder longitude(final GeoLoc geoLoc) {
            this.longitude = new BigDecimal(geoLoc.getLongitude());
            return this;
        }

        public SearchByTimeOfDayForProfileRequest send() {
            return new SearchByTimeOfDayForProfileRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/display/by_time_of_day/";

    private final WebServiceResponseConsumer<Root> callback;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final String rideLeaderId;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private SearchByTimeOfDayForProfileRequest(final Builder builder) {
        callback = builder.callback;
        latitude = builder.latitude;
        longitude = builder.longitude;
        rideLeaderId = builder.rideLeaderId;
        request = send();
    }

    private Request send() {
        try {
            final RepeatableRequestBuilder requestBuilder = new RepeatableRequestBuilder(RequestBuilder.GET, getUrlWithQuery(), null);
            return requestBuilder.sendRequest(null, getRequestCallback(requestBuilder));
        } catch (final RequestException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUrlWithQuery() {
        final StringBuilder builder = new StringBuilder();
        builder.append(URL);
        builder.append("geoloc=");
        builder.append(latitude);
        builder.append(",");
        builder.append(longitude);
        builder.append("/rideLeaderId=");
        builder.append(rideLeaderId);

        return builder.toString();
    }

    private final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();
    private RequestCallback getRequestCallback(final RepeatableRequestBuilder requestBuilder) {

        RequestCallBackHandlerStack<Root> cachedPewpChain = new RequestCallBackHandlerStack<Root>(
                PayloadConverters.ROOT_JSON_OBJECT_CONVERTER, requestBuilder, callback, new CacheStrategy<Root>() {
            @Override
            public void cacheType(Root type) {
                ramObjectCache.setSearchByTimeOfDayForProfile(type);
            }

            @Override
            public Root getCachedType() {
                return ramObjectCache.getSearchByTimeOfDayForProfile();
            }
        }, new RepeatForeverWaitingBetweenRetries<Root>()
        );

        return new RequestCallbackSorter<Root>(cachedPewpChain);
    }
}
