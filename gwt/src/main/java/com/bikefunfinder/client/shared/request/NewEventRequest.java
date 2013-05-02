package com.bikefunfinder.client.shared.request;
/*
 * @author: lancepoehler
 * @created 4/15/13 1:33 AM
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.google.gwt.http.client.*;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

public final class NewEventRequest {
    public interface Callback {
        void onError();
        void onResponseReceived(BikeRide bikeRide);
    }

    public static final class Builder {
        private NewEventRequest.Callback callback;
        private BikeRide bikeRide;

        public Builder(final NewEventRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final NewEventRequest.Callback callback) {
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

        public NewEventRequest send() {
            return new NewEventRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/bikerides/new";

    private final NewEventRequest.Callback callback;
    private final BikeRide bikeRide;
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
                    builder.append("Unable to create new bike ride.");
                    builder.append(" Status Code: ").append(statusCode);
                    builder.append("; Status Text: ").append(response.getStatusText());
                    Dialogs.alert("Error", builder.toString(), new Dialogs.AlertCallback() {
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
