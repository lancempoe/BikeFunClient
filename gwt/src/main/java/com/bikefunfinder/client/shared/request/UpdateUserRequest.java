package com.bikefunfinder.client.shared.request;


import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.User;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.bikefunfinder.client.shared.request.converters.PayloadConverters;
import com.bikefunfinder.client.shared.request.ratsnest.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/14/13
 * Time: 8:04 PM
 * To change this template use File | Settings | File Templates.
 */
public final class UpdateUserRequest {

    public static final class Builder {
        private WebServiceResponseConsumer<User> callback;
        private User user;

        public Builder(final WebServiceResponseConsumer<User> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final WebServiceResponseConsumer<User> callback) {
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

        public UpdateUserRequest send() {
            return new UpdateUserRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/users/update ";

    private final WebServiceResponseConsumer<User> callback;
    private final User user;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private UpdateUserRequest(final Builder builder) {
        callback = builder.callback;
        user = builder.user;
        request = send();
    }

    private Request send() {
        Request request = null;

        try {
            final String requestPayload = JSODescriber.toJSON(user);
            //Window.alert(jsonText); //if yer wanting some debuggerz
            final RepeatableRequestBuilder requestBuilder = new RepeatableRequestBuilder(RequestBuilder.POST, getUrlWithQuery(), requestPayload);
            requestBuilder.setHeader("Content-Type", "application/json");
            request = requestBuilder.sendRequest(requestPayload, getRequestCallback(requestBuilder));
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

    private RequestCallback getRequestCallback(final RepeatableRequestBuilder requestBuilder) {

        RequestCallBackHandlerStack<User> cachedPewpChain = new RequestCallBackHandlerStack<User>(
                PayloadConverters.User_JSON_OBJECT_CONVERTER, requestBuilder, callback,
                NoCacheStrategy.INSTANCE, new RepeatForeverWaitingBetweenRetries<User>()
        );

        return new RequestCallbackSorter<User>(cachedPewpChain);
    }
}
