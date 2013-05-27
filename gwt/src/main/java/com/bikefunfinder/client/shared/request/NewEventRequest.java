package com.bikefunfinder.client.shared.request;
/*
 * @author: lancepoehler
 * @created 4/15/13 1:33 AM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.google.gwt.http.client.*;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

import java.math.BigDecimal;

public final class NewEventRequest {

    public static final class Builder {
        private ServiceCallback<BikeRide> callback;
        private BikeRide bikeRide;
        private BigDecimal longitude;
        private BigDecimal latitude;

        public Builder(final ServiceCallback<BikeRide> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final ServiceCallback<BikeRide> callback) {
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

    private final ServiceCallback<BikeRide> callback;
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
        Request request = null;

        final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, getUrlWithQuery());
        try {
            requestBuilder.setHeader("Content-Type", "application/json");
            final String jsonText = JSODescriber.toJSON(bikeRide);
            request = requestBuilder.sendRequest(jsonText, getRequestCallback());
        } catch (final RequestException e) {
            e.printStackTrace();
        }

        return request;
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

    private RequestCallback getRequestCallback() {
        final RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void onError(final Request request, final Throwable exception) {
                Dialogs.alert("Error", "Unable to create new bike ride.",
                        new Dialogs.AlertCallback() {
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
                    builder.append(response.getText());
                    Dialogs.alert("Notice: ", builder.toString(), new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            callback.onError();
                        }
                    });
                } else {
                    Dialogs.alert("Notice: ", "Successfully Created Event!", new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            //Nothing needs to happen... simply notify the user.
                        }
                    });
                    BikeRide bikeRide = Utils.castJsonTxtToJSOObject(response.getText());
                    callback.onResponseReceived(bikeRide);
                }
            }
        };

        return requestCallback;
    }
}
