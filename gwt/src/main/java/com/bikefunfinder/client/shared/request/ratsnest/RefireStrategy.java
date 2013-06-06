package com.bikefunfinder.client.shared.request.ratsnest;
/*
 * @author: tneuwerth
 * @created 6/6/13 12:35 AM
 */

public interface RefireStrategy<T> {
    public boolean shouldCloseWaitingDialog();
    public void refire(RepeatableRequestBuilder requestBuilder, RequestCallBackHandlerStack<T> thizz);
}
