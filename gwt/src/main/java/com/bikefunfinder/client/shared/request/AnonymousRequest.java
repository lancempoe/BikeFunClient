package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 12:57 PM
 */

import com.bikefunfinder.client.bootstrap.AccountDetailsProvider;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.request.converters.PayloadConverters;
import com.bikefunfinder.client.shared.request.management.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

public final class AnonymousRequest {

    public static final class Builder {
        private WebServiceResponseConsumer<AnonymousUser> callback;

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


        public AnonymousRequest send() {
            return new AnonymousRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/users/anonymous/";

    private final WebServiceResponseConsumer<AnonymousUser> callback;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private AnonymousRequest(final Builder builder) {
        callback = builder.callback;
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
        AccountDetailsProvider.AccountDetails accountDetails = Injector.INSTANCE.getClientFactory().getAccountDetailsProvider().getAccountDetails();

        final StringBuilder builder = new StringBuilder();
        builder.append(URL);
        builder.append(accountDetails.key);
        builder.append("/");
        builder.append(accountDetails.uuid);

        return builder.toString();
    }

    private RequestCallback getRequestCallback(final RepeatableRequestBuilder requestBuilder) {

        RequestCallBackHandlerStack<AnonymousUser> cachedPewpChain = new RequestCallBackHandlerStack<AnonymousUser>(
            PayloadConverters.AnonymousUser_JSON_OBJECT_CONVERTER, requestBuilder, callback,
                AnnonymousUserCacheStrategy.INSTANCE, new RepeatForeverWaitingBetweenRetries<AnonymousUser>()
        );

        return new RequestCallbackSorter<AnonymousUser>(cachedPewpChain);
    }
}