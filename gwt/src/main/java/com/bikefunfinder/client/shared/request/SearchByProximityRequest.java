package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
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
import com.google.gwt.user.client.Window;

import java.math.BigDecimal;

public final class SearchByProximityRequest {

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

        public SearchByProximityRequest send() {
            return new SearchByProximityRequest(this);
        }

        public SearchByProximityRequest sendAndDebug() {
            return new SearchByProximityRequest(this, false);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/display/by_proximity/";

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

    private SearchByProximityRequest(final Builder builder) {
        this(builder, false);
    }

    private SearchByProximityRequest(final Builder builder, boolean debug) {
        callback = builder.callback;
        latitude = builder.latitude;
        longitude = builder.longitude;
        request = send(debug);
    }

    private Request send(Boolean debug) {
        try {
            final RepeatableRequestBuilder requestBuilder = new RepeatableRequestBuilder(RequestBuilder.GET, getUrlWithQuery(debug), null);
            return requestBuilder.sendRequest(null, getRequestCallback(requestBuilder));
        } catch (final RequestException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUrlWithQuery(boolean debug) {
        final StringBuilder builder = new StringBuilder();
        builder.append(URL);
        builder.append("geoloc=");
        builder.append(latitude);
        builder.append(",");
        builder.append(longitude);

        if(debug) {
            Window.alert(builder.toString());
        }

        return builder.toString();
    }


    private final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();
    private RequestCallback getRequestCallback(final RepeatableRequestBuilder requestBuilder) {

        RequestCallBackHandlerStack<Root> cachedPewpChain = new RequestCallBackHandlerStack<Root>(
                PayloadConverters.ROOT_JSON_OBJECT_CONVERTER, requestBuilder, callback,  new CacheStrategy<Root>() {
            @Override
            public void cacheType(Root root) {
                ramObjectCache.setSearchByProximity(root);
            }

            @Override
            public Root getCachedType() {
                return ramObjectCache.getSearchByProximity();
            }
        }, new RepeatForeverWaitingBetweenRetries<Root>());

        return new RequestCallbackSorter<Root>(cachedPewpChain);
    }
}
