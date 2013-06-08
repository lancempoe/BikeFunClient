package com.bikefunfinder.client.shared.request;
/*
 * @author: lancepoehler
 * @created 4/15/13 1:33 AM
 */

import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.bikefunfinder.client.shared.request.converters.PayloadConverters;
import com.bikefunfinder.client.shared.request.management.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

import java.math.BigDecimal;

public final class NewEventRequest {

    public static final class Builder {
        private WebServiceResponseConsumer<BikeRide> callback;
        private BikeRide bikeRide;
        private BigDecimal longitude;
        private BigDecimal latitude;

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

        public Builder bikeRide(final BikeRide bikeRide) {
            this.bikeRide = bikeRide;
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

        public NewEventRequest send() {
            return new NewEventRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/bikerides/new";

    private final WebServiceResponseConsumer<BikeRide> callback;
    private final BikeRide bikeRide;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private NewEventRequest(final Builder builder) {
        callback = builder.callback;
        bikeRide = builder.bikeRide;
        latitude = builder.latitude;
        longitude = builder.longitude;
        request = send();
    }

    private Request send() {
        try {
            final String requestData = JSODescriber.toJSON(bikeRide);

            final RepeatableRequestBuilder requestBuilder = new RepeatableRequestBuilder(RequestBuilder.POST, getUrlWithQuery(), requestData);
            requestBuilder.setHeader("Content-Type", "application/json");
            return requestBuilder.sendRequest(requestData, getRequestCallback(requestBuilder));
        } catch (final RequestException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUrlWithQuery() {
        final StringBuilder builder = new StringBuilder();
        builder.append(URL);
        builder.append("/geoloc=");
        builder.append(latitude);
        builder.append(",");
        builder.append(longitude);

        return builder.toString();
    }

    private RequestCallback getRequestCallback(final RepeatableRequestBuilder requestBuilder) {

        RequestCallBackHandlerStack<BikeRide> cachedPewpChain = new RequestCallBackHandlerStack<BikeRide>(
                PayloadConverters.BikeRide_JSON_OBJECT_CONVERTER, requestBuilder, callback,
                NoCacheStrategy.INSTANCE, new RepeatForeverWaitingBetweenRetries<BikeRide>()
        );

        return new RequestCallbackSorter<BikeRide>(cachedPewpChain);
    }
}
