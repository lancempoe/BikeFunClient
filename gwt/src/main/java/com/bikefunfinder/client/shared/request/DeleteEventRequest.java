package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.bikefunfinder.client.shared.request.converters.NoOpResponseObject;
import com.bikefunfinder.client.shared.request.converters.PayloadConverters;
import com.bikefunfinder.client.shared.request.ratsnest.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

public final class DeleteEventRequest {

    public static final class Builder {
        private WebServiceResponseConsumer<NoOpResponseObject> callback;
        private Root root;

        public Builder(final WebServiceResponseConsumer<NoOpResponseObject> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final WebServiceResponseConsumer<NoOpResponseObject> callback) {
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

    private final WebServiceResponseConsumer<NoOpResponseObject> callback;
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
        try {
            final String requestData = JSODescriber.toJSON(root);
            //Window.alert(requestData); //if yer wanting some debuggerz

            final RepeatableRequestBuilder requestBuilder = new RepeatableRequestBuilder(RequestBuilder.POST, getUrlWithQuery(), requestData);
            requestBuilder.setHeader("Content-Type", "application/json");
            return requestBuilder.sendRequest(requestData, getRequestCallback(requestBuilder));

        } catch (final RequestException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUrlWithQuery() {
        final StringBuilder builder = new StringBuilder();
        builder.append(URL);

        return builder.toString();
    }

    private RequestCallback getRequestCallback(final RepeatableRequestBuilder requestBuilder) {

        RequestCallBackHandlerStack<NoOpResponseObject> cachedPewpChain = new RequestCallBackHandlerStack<NoOpResponseObject>(
                PayloadConverters.NoOpResponse_JSON_OBJECT_CONVERTER, requestBuilder, callback,
                NoCacheStrategy.INSTANCE, new RepeatForeverWaitingBetweenRetries<NoOpResponseObject>()
        );

        return new RequestCallbackSorter<NoOpResponseObject>(cachedPewpChain);
    }
}