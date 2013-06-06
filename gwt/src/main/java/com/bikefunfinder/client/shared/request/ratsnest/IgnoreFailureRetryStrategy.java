package com.bikefunfinder.client.shared.request.ratsnest;
/*
 * @author: tneuwerth
 * @created 6/6/13 12:41 AM
 */

import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Timer;

public class IgnoreFailureRetryStrategy<T> implements RefireStrategy<T> {

    @Override
    public boolean shouldCloseWaitingDialog() {
        return true;
    }

    @Override
    public void refire(final RepeatableRequestBuilder requestBuilder, final RequestCallBackHandlerStack<T> thizz) {
        //whee
    }
}
