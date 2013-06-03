package com.bikefunfinder.client.shared.request.ratsnest;
/*
 * @author: tneuwerth
 * @created 5/31/13 12:33 AM
 */

import com.bikefunfinder.client.shared.request.converters.JsonObjectConverter;
import com.bikefunfinder.client.shared.request.converters.LoadingScreen;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;

public class RequestCallBackHandlerStack<T> implements RequestCallbackSorter.PoopHandlerStack<T> {
    private final JsonObjectConverter<T> successfulPayloadConverter;
    private final RepeatableRequestBuilder requestBuilder;
    private final WebServiceResponseConsumer<T> callback;
    private final CacheStrategy<T> cacheStrategy;

    public RequestCallBackHandlerStack(JsonObjectConverter<T> successfulPayloadConverter,
                                       RepeatableRequestBuilder requestBuilder,
                                       WebServiceResponseConsumer<T> callback,
                                       CacheStrategy<T> cacheStrategy) {
        this.successfulPayloadConverter = successfulPayloadConverter;
        this.requestBuilder = requestBuilder;
        this.callback = callback;
        this.cacheStrategy = cacheStrategy;

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

        T cachedEntry = cacheStrategy.getCachedType();
        if(cachedEntry!=null) {
            informCallBackOfSuccess(cachedEntry);
        } else {
            refireRequestInAfterSomeTime(this);
        }
    }

    private void refireRequestInAfterSomeTime(final RequestCallBackHandlerStack<T> thizz) {
        Timer timer = new Timer() {
            public void run() {
                try {
                    requestBuilder.setCallback(new RequestCallbackSorter(thizz));
                    requestBuilder.setRequestData(requestBuilder.getPayload());
                    requestBuilder.send();
                } catch (RequestException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        };

        // Execute the timer to expire 2 seconds in the future
        timer.schedule(2000);
    }
}
