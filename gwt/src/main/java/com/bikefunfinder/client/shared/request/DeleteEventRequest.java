package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gwt.http.client.*;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

public final class DeleteEventRequest {
    public interface Callback {
        void onError();
        void onResponseReceived();
    }

    public static final class Builder {
        private DeleteEventRequest.Callback callback;
        private String userId;
        private String key;
        private String uuid;

        public Builder(final DeleteEventRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final DeleteEventRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
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

        public DeleteEventRequest send() {
            return new DeleteEventRequest(this);
        }
    }

    private static final String URL = "http://appworks.timneuwerth.com/FunService/rest/bikerides/delete/";

    private final DeleteEventRequest.Callback callback;
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

    private DeleteEventRequest(final Builder builder) {
        callback = builder.callback;
        userId = builder.userId;
        key = builder.key;
        uuid = builder.uuid;
        request = send();
    }

    private Request send() {
        Request request = null;

        final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, getUrlWithQuery());
        try {
            request = requestBuilder.sendRequest(null, getRequestCallback());
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
                Dialogs.alert("Error", "Unable to delete event.", new Dialogs.AlertCallback() {
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
                    builder.append("Unable to delete event.");
                    builder.append(" Status Code: ").append(statusCode);
                    builder.append("; Status Text: ").append(response.getStatusText());
                    Dialogs.alert("Error", builder.toString(), new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            callback.onError();
                        }
                    });
                } else {
                    callback.onResponseReceived();
                }
            }
        };

        return requestCallback;
    }
}