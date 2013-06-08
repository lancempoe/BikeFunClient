package com.bikefunfinder.client.shared.request.management;
/*
 * @author: tneuwerth
 * @created 5/31/13 12:33 AM
 */

import com.bikefunfinder.client.shared.request.converters.JsonObjectConverter;
import com.bikefunfinder.client.shared.widgets.LoadingScreen;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

public class RequestCallBackHandlerStack<T> implements RequestCallbackSorter.PoopHandlerStack<T> {
    private final JsonObjectConverter<T> successfulPayloadConverter;
    private final RepeatableRequestBuilder requestBuilder;
    private final WebServiceResponseConsumer<T> callback;
    private final CacheStrategy<T> cacheStrategy;
    private final RefireStrategy<T> refireStrategy;
    private final ScreenBlockingStrategy screenBlockingStrategy;

    public RequestCallBackHandlerStack(JsonObjectConverter<T> successfulPayloadConverter,
                                       RepeatableRequestBuilder requestBuilder,
                                       WebServiceResponseConsumer<T> callback,
                                       CacheStrategy<T> cacheStrategy,
                                       RefireStrategy<T> refireStrategy) {
        this(successfulPayloadConverter, requestBuilder, callback, cacheStrategy, refireStrategy,
                new BlockUntilSuccessScreenBlockingStrategy());
    }

    public RequestCallBackHandlerStack(JsonObjectConverter<T> successfulPayloadConverter,
                                       RepeatableRequestBuilder requestBuilder,
                                       WebServiceResponseConsumer<T> callback,
                                       CacheStrategy<T> cacheStrategy,
                                       RefireStrategy<T> refireStrategy,
                                       ScreenBlockingStrategy screenBlockingStrategy) {
        this.successfulPayloadConverter = successfulPayloadConverter;
        this.requestBuilder = requestBuilder;
        this.callback = callback;
        this.cacheStrategy = cacheStrategy;
        this.refireStrategy = refireStrategy;
        this.screenBlockingStrategy = screenBlockingStrategy;

        screenBlockingStrategy.afterConstruction();
    }

    @Override
    public void goodPoop(Response response) {
        T objectPayload = successfulPayloadConverter.convert(response.getText());
        cacheStrategy.cacheType(objectPayload);

        informCallBackOfSuccess(objectPayload);
    }

    private void informCallBackOfSuccess(T objectPayload) {
        screenBlockingStrategy.beforeCallbackSuccessfulIsCalled();
        callback.onResponseReceived(objectPayload);
    }

    @Override
    public void messyPoop(Request request) {
        if(refireStrategy.shouldCloseWaitingDialog()) {
            screenBlockingStrategy.whenNetworkErrorAndRquestWantsToGiveUp();
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
        screenBlockingStrategy.whenServerRespondsToCallWithClientErrorMessage();
    }
}
