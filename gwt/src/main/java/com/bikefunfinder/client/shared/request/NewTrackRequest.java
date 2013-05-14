package com.bikefunfinder.client.shared.request;
/*
 * @author: lancepoehler
 * @created 4/8/13 9:33 PM
 */

import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.Tracking;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.google.gwt.http.client.*;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

public final class NewTrackRequest {
    public interface Callback {
        void onError();
        void onResponseReceived(Tracking tracking);
    }

    public static final class Builder {
        private NewTrackRequest.Callback callback;
        private Tracking tracking;

        public Builder(final NewTrackRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final NewTrackRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
            return this;
        }

        public Builder bikeRide(final Tracking tracking) {
            this.tracking = tracking;
            return this;
        }

        public NewTrackRequest send() {
            return new NewTrackRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/tracking/new ";

    private final NewTrackRequest.Callback callback;
    private final Tracking tracking;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private NewTrackRequest(final Builder builder) {
        callback = builder.callback;
        tracking = builder.tracking;
        request = send();
    }

    private Request send() {
        Request request = null;

        final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, getUrlWithQuery());
        try {
            final String jsonText = JSODescriber.toJSON(tracking);
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
                Dialogs.alert("Error", "Unable to create new tracking on bike ride.",
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
                    builder.append("Unable to create new tracking on bike ride. ");
                    builder.append(response.getText());
                    Dialogs.alert("Notice: ", builder.toString(), new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            callback.onError();
                        }
                    });
                } else {
                    Tracking tracking = Utils.castJsonTxtToJSOObject(response.getText());
                    callback.onResponseReceived(tracking);
                }
            }
        };
        return requestCallback;
    }
}
