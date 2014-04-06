package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.ServiceVersion;
import com.bikefunfinder.client.shared.request.converters.PayloadConverters;
import com.bikefunfinder.client.shared.request.management.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

import java.math.BigDecimal;

public final class VersionRequest {

    public static final class Builder {
        private WebServiceResponseConsumer<ServiceVersion> callback;

        public Builder(final WebServiceResponseConsumer<ServiceVersion> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final WebServiceResponseConsumer<ServiceVersion> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
            return this;
        }

        public VersionRequest send() {
            return new VersionRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/api/version";
    private final WebServiceResponseConsumer<ServiceVersion> callback;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private VersionRequest(final Builder builder) {
        callback = builder.callback;
        request = send();
    }

    private Request send() {
        try {
            String requestData = null;
            final RepeatableRequestBuilder requestBuilder = new RepeatableRequestBuilder(RequestBuilder.GET, URL, requestData);
            return requestBuilder.sendRequest(null, getRequestCallback(requestBuilder));
        } catch (final RequestException e) {
            e.printStackTrace();
            return null;
        }
    }

    private final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();
    private RequestCallback getRequestCallback(final RepeatableRequestBuilder requestBuilder) {

        RequestCallBackHandlerStack<ServiceVersion> cachedPewpChain = new RequestCallBackHandlerStack<ServiceVersion>(
                PayloadConverters.ServiceVersion_JSON_OBJECT_CONVERTER, requestBuilder, callback,
                new CacheStrategy<ServiceVersion>() {
                    @Override
                    public void cacheType(ServiceVersion type) {
                        ramObjectCache.setServiceVersion(type);
                    }

                    @Override
                    public ServiceVersion getCachedType() {
                        return ramObjectCache.getServiceVersion();
                    }
                }, new TryToRecallSetNumberOfFailures<ServiceVersion>(2), NonUserBlockingStrategy.INSTANCE
        );

        return new RequestCallbackSorter<ServiceVersion>(cachedPewpChain);
    }
}
