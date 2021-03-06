package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Query;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.bikefunfinder.client.shared.request.converters.PayloadConverters;
import com.bikefunfinder.client.shared.request.management.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

import java.math.BigDecimal;

public final class SearchByParametersRequest {

    public static final class Builder {
        private WebServiceResponseConsumer<Root> callback;
        private Query query;
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

        public Builder query(final Query query) {
            this.query = query;
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

        public SearchByParametersRequest send() {
            return new SearchByParametersRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/display/by_search/";

    private final WebServiceResponseConsumer<Root> callback;
    private final Query query;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private SearchByParametersRequest(final Builder builder) {
        callback = builder.callback;
        query = builder.query;
        latitude = builder.latitude;
        longitude = builder.longitude;
        request = send();
    }

    private Request send() {
        try {
            final String requestPayload = JSODescriber.toJSON(query);
            final RepeatableRequestBuilder requestBuilder = new RepeatableRequestBuilder(RequestBuilder.POST, getUrlWithQuery(), requestPayload);
            requestBuilder.setHeader("Content-Type", "application/json");
            return requestBuilder.sendRequest(requestPayload, getRequestCallback(requestBuilder));
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
                PayloadConverters.ROOT_JSON_OBJECT_CONVERTER, requestBuilder, callback,  new CacheStrategy<Root>() {
            @Override
            public void cacheType(Root root) {
                ramObjectCache.setRoot(root); //This is used on the main page.
            }

            @Override
            public Root getCachedType() {
                return null; //todo deceid if this is the best response.
            }
        }, new RepeatForeverWaitingBetweenRetries<Root>());

        return new RequestCallbackSorter<Root>(cachedPewpChain);
    }
}
