package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.google.gwt.http.client.*;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

import java.math.BigDecimal;

public final class EventRequest {
    public interface Callback {
        void onError();
        void onResponseReceived(BikeRide bikeRide);
    }

    public static final class Builder {
        private EventRequest.Callback callback;
        private String eventId;
        private BigDecimal longitude;
        private BigDecimal latitude;
        private String clientId;

        public Builder(final EventRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final EventRequest.Callback callback) {
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

    private final EventRequest.Callback callback;
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
        Request request = null;

        final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, getUrlWithQuery());
        try {
            request = requestBuilder.sendRequest(clientId, getRequestCallback());
        } catch (final RequestException e) {
            e.printStackTrace();
        }

        return request;
    }

    private String getUrlWithQuery() {
        final StringBuilder builder = new StringBuilder();
        builder.append(URL);
        builder.append(eventId);
        builder.append("/geoloc=");
        builder.append(latitude);
        builder.append(",");
        builder.append(longitude);

        return builder.toString();
    }

    private RequestCallback getRequestCallback() {
        final RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void onError(final Request request, final Throwable exception) {
                Dialogs.alert("Error", "Unable to get event.", new Dialogs.AlertCallback() {
                    @Override
                    public void onButtonPressed() {
                        callback.onError();
                    }
                });
            }

            @Override
            public void onResponseReceived(final Request request, final Response response) {
                final int statusCode = response.getStatusCode();
                if ((statusCode < 200) || (statusCode >= 300)) {
                    final StringBuilder builder = new StringBuilder();
                    builder.append("Unable to get event. ");
                    builder.append(response.getText());
                    Dialogs.alert("Notice: ", builder.toString(), new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            callback.onError();
                        }
                    });
                } else {
                    BikeRide bikeRide = Utils.castJsonTxtToJSOObject(response.getText());
                    callback.onResponseReceived(bikeRide);
                }
            }
        };

        return requestCallback;
    }
}
