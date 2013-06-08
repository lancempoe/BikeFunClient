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
import com.bikefunfinder.client.shared.request.converters.PayloadConverters;
import com.bikefunfinder.client.shared.request.management.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

import java.math.BigDecimal;

public final class EventRequest {

    public static final class Builder {
        private WebServiceResponseConsumer<BikeRide> callback;
        private String eventId;
        private BigDecimal longitude;
        private BigDecimal latitude;
        private String clientId;

        public Builder(final WebServiceResponseConsumer<BikeRide> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final WebServiceResponseConsumer<BikeRide> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
            return this;
        }
        public Builder clientId(final String clientId) {
            this.clientId = clientId;
            return this;
        }
        public Builder id(final String eventId) {
            this.eventId = eventId;
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

        public EventRequest send() {
            return new EventRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/bikerides/";

    private final WebServiceResponseConsumer<BikeRide> callback;
    private final String eventId;
    private final String clientId;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private EventRequest(final Builder builder) {
        callback = builder.callback;
        eventId = builder.eventId;
        clientId = builder.clientId;
        latitude = builder.latitude;
        longitude = builder.longitude;
        request = send();
    }

    private Request send() {
        try {
            String requestData = null;
            final RepeatableRequestBuilder requestBuilder = new RepeatableRequestBuilder(RequestBuilder.GET, getUrlWithQuery(), requestData);
            return requestBuilder.sendRequest(null, getRequestCallback(requestBuilder));
        } catch (final RequestException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUrlWithQuery() {
        final StringBuilder builder = new StringBuilder();
        builder.append(URL);
        builder.append(eventId);
        builder.append(",");
        builder.append(clientId);
        builder.append("/geoloc=");
        builder.append(latitude);
        builder.append(",");
        builder.append(longitude);

        return builder.toString();
    }

    private final RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();
    private RequestCallback getRequestCallback(final RepeatableRequestBuilder requestBuilder) {

        RequestCallBackHandlerStack<BikeRide> cachedPewpChain = new RequestCallBackHandlerStack<BikeRide>(
                PayloadConverters.BikeRide_JSON_OBJECT_CONVERTER, requestBuilder, callback,
                new CacheStrategy<BikeRide>() {
                    @Override
                    public void cacheType(BikeRide type) {
                        ramObjectCache.setEventRequest(type);
                    }

                    @Override
                    public BikeRide getCachedType() {
                        return ramObjectCache.getEventRequest();
                    }
                }, new TryToRecallSetNumberOfFailures<BikeRide>(2), NonUserBlockingStrategy.INSTANCE
        );

        return new RequestCallbackSorter<BikeRide>(cachedPewpChain);
    }
}
