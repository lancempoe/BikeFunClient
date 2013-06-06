package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 12:57 PM
 */

import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.request.converters.PayloadConverters;
import com.bikefunfinder.client.shared.request.ratsnest.*;
import com.google.gwt.http.client.*;

public final class AnonymousRequest {

    public static final class Builder {
        private WebServiceResponseConsumer<AnonymousUser> callback;
        private String key;
        private String uuid;

        public Builder(final WebServiceResponseConsumer<AnonymousUser> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final WebServiceResponseConsumer<AnonymousUser> callback) {
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

    private final WebServiceResponseConsumer<AnonymousUser> callback;
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
        try {
            final RepeatableRequestBuilder requestBuilder = new RepeatableRequestBuilder(RequestBuilder.GET, getUrlWithQuery(), null);
            requestBuilder.setHeader("Accept", "application/json");
            requestBuilder.setHeader("content-type", "application/json");
            return requestBuilder.sendRequest(null, getRequestCallback(requestBuilder));
        } catch (final RequestException e) {
            e.printStackTrace();
            return null;
        }
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

    private RequestCallback getRequestCallback(final RepeatableRequestBuilder requestBuilder) {

        RequestCallBackHandlerStack<AnonymousUser> cachedPewpChain = new RequestCallBackHandlerStack<AnonymousUser>(
            PayloadConverters.AnonymousUser_JSON_OBJECT_CONVERTER, requestBuilder, callback,
                NoCacheStrategy.INSTANCE, new RepeatForeverWaitingBetweenRetries<AnonymousUser>()
        );

        return new RequestCallbackSorter<AnonymousUser>(cachedPewpChain);
    }
}