package com.bikefunfinder.client.shared.request;
/*
 * @author: lancepoehler
 * @created 4/8/13 9:33 PM
 */

import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.Tracking;
import com.bikefunfinder.client.shared.model.printer.JSODescriber;
import com.bikefunfinder.client.shared.request.converters.PayloadConverters;
import com.bikefunfinder.client.shared.request.ratsnest.*;
import com.google.gwt.http.client.*;

public final class NewTrackRequest {

    public static final class Builder {
        private WebServiceResponseConsumer<Tracking> callback;
        private Tracking tracking;

        public Builder(final WebServiceResponseConsumer<Tracking> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final WebServiceResponseConsumer<Tracking> callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
            return this;
        }

        public Builder tracking(final Tracking tracking) {
            this.tracking = tracking;
            return this;
        }

        public NewTrackRequest send() {
            return new NewTrackRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/tracking/new ";

    private final WebServiceResponseConsumer<Tracking> callback;
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

        try {
            final String requestData = JSODescriber.toJSON(tracking);
            //Window.alert(requestData); //if yer wanting some debuggerz

            final RepeatableRequestBuilder requestBuilder = new RepeatableRequestBuilder(RequestBuilder.POST, getUrlWithQuery(), requestData);
            requestBuilder.setHeader("Content-Type", "application/json");
            request = requestBuilder.sendRequest(requestData, getRequestCallback(requestBuilder));
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

        RequestCallBackHandlerStack<Tracking> cachedPewpChain = new RequestCallBackHandlerStack<Tracking>(
                PayloadConverters.Tracking_JSON_OBJECT_CONVERTER, requestBuilder, callback , NoCacheStrategy.INSTANCE
        );

        return new RequestCallbackSorter<Tracking>(cachedPewpChain);
    }
}
