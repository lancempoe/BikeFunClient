package com.bikefunfinder.client.shared.request;

import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.User;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.google.gwt.http.client.*;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/14/13
 * Time: 7:36 PM
 */
public final class NewUserRequest {
    public interface Callback {
        void onError();
        void onResponseReceived(User user);
    }

    public static final class Builder {
        private NewUserRequest.Callback callback;
        private User user;

        public Builder(final NewUserRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final NewUserRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
            return this;
        }

        public Builder user(final User user) {
            this.user = user;
            return this;
        }

        public NewUserRequest send() {
            return new NewUserRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/users";

    private final NewUserRequest.Callback callback;
    private final User user;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private NewUserRequest(final Builder builder) {
        callback = builder.callback;
        user = builder.user;
        request = send();
    }

    private Request send() {
        Request request = null;

        final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, getUrlWithQuery());
        try {
            final String jsonText = JSODescriber.toJSON(user);
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
                Dialogs.alert("Error", "Unable to create new user.",
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
                    builder.append("Unable to create new user.");
                    builder.append(" Status Code: ").append(statusCode);
                    builder.append("; Status Text: ").append(response.getStatusText());
                    Dialogs.alert("Error", builder.toString(), new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            callback.onError();
                        }
                    });
                } else {
                    User user = Utils.castJsonTxtToJSOObject(response.getText());
                    callback.onResponseReceived(user);
                }
            }
        };

        return requestCallback;
    }
}

