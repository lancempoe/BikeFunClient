package com.bikefunfinder.client.shared.request;
/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.google.gwt.http.client.*;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

public final class UpdateEventRequest {
    public interface Callback {
        void onError();
        void onResponseReceived(BikeRide bikeRide);
    }

    public static final class Builder {
        private UpdateEventRequest.Callback callback;
        private Root root;

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

        public Builder root(final Root root) {
            this.root = root;
            return this;
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/bikerides/update ";

    private final UpdateEventRequest.Callback callback;
    private final Root root;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private UpdateEventRequest(final Builder builder) {
        callback = builder.callback;
        root = builder.root;
        request = send();
    }

    private Request send() {
        Request request = null;

        final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, getUrlWithQuery());
        try {
            final String jsonText = JSODescriber.toJSON(root);
            //Window.alert(jsonText); //if yer wanting some debuggerz
            requestBuilder.setHeader("Content-Type", "application/json");
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
                    builder.append("Unable to update bike ride. ");
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
