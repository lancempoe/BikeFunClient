package com.bikefunfinder.client.shared.request.management;
/*
 * @author: tneuwerth
 * @created 6/6/13 12:41 AM
 */

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
