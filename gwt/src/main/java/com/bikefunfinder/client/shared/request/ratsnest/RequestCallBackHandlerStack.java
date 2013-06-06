package com.bikefunfinder.client.shared.request.ratsnest;
/*
 * @author: tneuwerth
 * @created 5/31/13 12:33 AM
 */

import com.bikefunfinder.client.shared.request.converters.JsonObjectConverter;
import com.bikefunfinder.client.shared.widgets.LoadingScreen;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;

public class RequestCallBackHandlerStack<T> implements RequestCallbackSorter.PoopHandlerStack<T> {
    private final JsonObjectConverter<T> successfulPayloadConverter;
    private final RepeatableRequestBuilder requestBuilder;
    private final WebServiceResponseConsumer<T> callback;
    private final CacheStrategy<T> cacheStrategy;
    private final RefireStrategy<T> refireStrategy;

    public RequestCallBackHandlerStack(JsonObjectConverter<T> successfulPayloadConverter,
                                       RepeatableRequestBuilder requestBuilder,
                                       WebServiceResponseConsumer<T> callback,
                                       CacheStrategy<T> cacheStrategy,
                                       RefireStrategy<T> refireStrategy) {
        this.successfulPayloadConverter = successfulPayloadConverter;
        this.requestBuilder = requestBuilder;
        this.callback = callback;
        this.cacheStrategy = cacheStrategy;
        this.refireStrategy = refireStrategy;

        LoadingScreen.openLoaderPanel();
    }

    @Override
    public void goodPoop(Response response) {
        T objectPayload = successfulPayloadConverter.convert(response.getText());
        cacheStrategy.cacheType(objectPayload);

        informCallBackOfSuccess(objectPayload);
    }

    private void informCallBackOfSuccess(T objectPayload) {
        LoadingScreen.closeLoader();
        callback.onResponseReceived(objectPayload);
    }

    @Override
    public void messyPoop(Request request) {
        if(refireStrategy.shouldCloseWaitingDialog()) {
            LoadingScreen.closeLoader();
        }

        T cachedEntry = cacheStrategy.getCachedType();
        if(cachedEntry!=null) {
            informCallBackOfSuccess(cachedEntry);
        } else {
            refireStrategy.refire(requestBuilder, this);
        }
    }

    @Override
    public void errorPoop() {
        LoadingScreen.closeLoader();
    }
}
