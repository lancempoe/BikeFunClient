package com.bikefunfinder.client.shared.request;
/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bikefunfinder.client.client.places.homescreen.HomeScreenActivity;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.http.client.*;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

public final class UpdateEventRequest {
    public interface Callback {
        void onError();
        void onResponseReceived(BikeRide bikeRide);
    }

    public static final class Builder {
        private UpdateEventRequest.Callback callback;
        private BikeRide bikeRide;
        private String userId;
        private String key;
        private String uuid;

        public Builder(final UpdateEventRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final UpdateEventRequest.Callback callback) {
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

        public Builder userId(final String userId) {
            this.userId = userId;
            return this;
        }
        public Builder key(final String key) {
            this.key = key;
            return this;
        }
        public Builder uuid(final String uuid) {
            this.uuid = uuid;
            return this;
        }
        public UpdateEventRequest send() {
            return new UpdateEventRequest(this);
        }
    }

    private static final String URL = "http://appworks.timneuwerth.com/FunService/rest/bikerides/update ";

    private final UpdateEventRequest.Callback callback;
    private final BikeRide bikeRide;
    private final String userId;
    private final String key;
    private final String uuid;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private UpdateEventRequest(final Builder builder) {
        callback = builder.callback;
        bikeRide = builder.bikeRide;
        userId = builder.userId;
        key = builder.key;
        uuid = builder.uuid;
        request = send();
    }

    private Request send() {
        Request request = null;

        final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, getUrlWithQuery());
        try {
            request = requestBuilder.sendRequest(bikeRide.toSource(), getRequestCallback());
        } catch (final RequestException e) {
            e.printStackTrace();
        }

        return request;
    }

    private String getUrlWithQuery() {
        final StringBuilder builder = new StringBuilder();
        builder.append(URL);
        builder.append(userId);
        builder.append("/");
        builder.append(key);
        builder.append("/");
        builder.append(uuid);

        return builder.toString();
    }

    private RequestCallback getRequestCallback() {
        final RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void onError(final Request request, final Throwable exception) {
                Dialogs.alert("Error", "Unable to update bike ride.",
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
                    builder.append("Unable to update bike ride.");
                    builder.append(" Status Code: ").append(statusCode);
                    builder.append("; Status Text: ").append(response.getStatusText());
                    Dialogs.alert("Error", builder.toString(), new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            callback.onError();
                        }
                    });
                } else {
                    BikeRide bikeRide = HomeScreenActivity.testObjectParse(response.getText());
                    callback.onResponseReceived(bikeRide);
                }
            }
        };

        return requestCallback;
    }
}
