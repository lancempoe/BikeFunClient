package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.bikefunfinder.client.shared.constants.Settings;
import com.google.gwt.http.client.*;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog.*;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

public final class DeleteEventRequest {
    public interface Callback {
        void onError();
        void onResponseReceived();
    }

    public static final class Builder {
        private DeleteEventRequest.Callback callback;
        private Root root;

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

        public Builder root(final Root root) {
            this.root = root;
            return this;
        }

        public DeleteEventRequest send() {
            return new DeleteEventRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/bikerides/delete/";

    private final DeleteEventRequest.Callback callback;
    private final Root root;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private DeleteEventRequest(final Builder builder) {
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
                    builder.append("Unable to delete event. ");
                    builder.append(response.getText());
                    Dialogs.alert("Notice: ", builder.toString(), new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            callback.onError();
                        }
                    });
                } else {
                    Dialogs.alert("Notice: ", "Successfully Deleted Event!", new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            //Nothing needs to happen... simply notify the user.
                        }
                    });
                    callback.onResponseReceived();
                }
            }
        };

        return requestCallback;
    }
}