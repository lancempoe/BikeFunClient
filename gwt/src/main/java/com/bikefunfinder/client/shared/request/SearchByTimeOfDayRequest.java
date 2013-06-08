package com.bikefunfinder.client.shared.request;
/*
 * @author: tneuwerth
 * @created 3/19/13 10:33 PM
 */

import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.request.converters.PayloadConverters;
import com.bikefunfinder.client.shared.request.management.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

import java.math.BigDecimal;

public final class SearchByTimeOfDayRequest {

    public static final class Builder {
        private WebServiceResponseConsumer<Root> callback;
        private BigDecimal longitude;
        private BigDecimal latitude;

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
        public Builder latitude(final GeoLoc geoLoc) {
            this.latitude = new BigDecimal(geoLoc.getLatitude());
            return this;
        }

        public Builder longitude(final GeoLoc geoLoc) {
            this.longitude = new BigDecimal(geoLoc.getLongitude());
            return this;
        }

        public SearchByTimeOfDayRequest send() {
            return new SearchByTimeOfDayRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/display/by_time_of_day/";

    private final WebServiceResponseConsumer<Root> callback;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private SearchByTimeOfDayRequest(final Builder builder) {
        callback = builder.callback;
        latitude = builder.latitude;
        longitude = builder.longitude;
        request = send();
    }

    private Request send() {
        try {
            final RepeatableRequestBuilder requestBuilder = new RepeatableRequestBuilder(RequestBuilder.GET, getUrlWithQuery(), null );
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

        return builder.toString();
    }

    private final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();
    private RequestCallback getRequestCallback(final RepeatableRequestBuilder requestBuilder) {

        RequestCallBackHandlerStack<Root> cachedPewpChain = new RequestCallBackHandlerStack<Root>(
            PayloadConverters.ROOT_JSON_OBJECT_CONVERTER, requestBuilder, callback, new CacheStrategy<Root>() {
            @Override
            public void cacheType(Root type) {
                ramObjectCache.setSearchByTimeOfDay(type);
            }

            @Override
            public Root getCachedType() {
                return ramObjectCache.getSearchByTimeOfDay();
            }
        }, new RepeatForeverWaitingBetweenRetries<Root>());

        return new RequestCallbackSorter<Root>(cachedPewpChain);
    }
}
