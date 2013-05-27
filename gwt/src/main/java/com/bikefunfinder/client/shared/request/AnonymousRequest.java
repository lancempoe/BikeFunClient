package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 12:57 PM
 */

import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.bikefunfinder.client.shared.constants.Settings;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

public final class AnonymousRequest {

    public static final class Builder {
        private ServiceCallback<AnonymousUser> callback;
        private String key;
        private String uuid;

        public Builder(final ServiceCallback<AnonymousUser> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final ServiceCallback<AnonymousUser> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
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

        public AnonymousRequest send() {
            return new AnonymousRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/users/anonymous/";

    private final ServiceCallback<AnonymousUser> callback;
    private final String key;
    private final String uuid;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private AnonymousRequest(final Builder builder) {
        callback = builder.callback;
        key = builder.key;
        uuid = builder.uuid;
        request = send();
    }

    private Request send() {
        Request request = null;

        final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, getUrlWithQuery());
        try {
            request = requestBuilder.sendRequest(null, getRequestCallback());
            requestBuilder.setHeader("Accept", "application/json");
            requestBuilder.setHeader("content-type", "application/json");
        } catch (final RequestException e) {
            e.printStackTrace();
        }

        return request;
    }

    private String getUrlWithQuery() {
        final StringBuilder builder = new StringBuilder();
        builder.append(URL);
        builder.append(key);
        builder.append("/");
        builder.append(uuid);

//        Window.alert(builder.toString());
        return builder.toString();
    }

    private RequestCallback getRequestCallback() {
        final RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void onError(final Request request, final Throwable exception) {
                Dialogs.alert("Error", "Unable to get anonymous user.", new Dialogs.AlertCallback() {
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
                    builder.append("Unable to get anonymous user. ");
                    builder.append(response.getText());
                    Dialogs.alert("Notice: ", builder.toString(), new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            callback.onError();
                        }
                    });
                } else {
                    AnonymousUser anonymousUser = Utils.castJsonTxtToJSOObject(response.getText());
                    callback.onResponseReceived(anonymousUser);

                }
            }
        };

        return requestCallback;
    }
}