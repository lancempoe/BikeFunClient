package com.bikefunfinder.client.shared.request.ratsnest;
/*
 * @author: tneuwerth
 * @created 6/6/13 12:39 AM
 */

import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Timer;

public class RepeatForeverWaitingBetweenRetries<T> implements RefireStrategy<T> {

    @Override
    public boolean shouldCloseWaitingDialog() {
        return false;
    }

    @Override
    public void refire(final RepeatableRequestBuilder requestBuilder, final RequestCallBackHandlerStack<T> thizz) {
        Timer timer = new Timer() {
            public void run() {
                try {
                    requestBuilder.setCallback(new RequestCallbackSorter(thizz));
                    requestBuilder.setRequestData(requestBuilder.getPayload());
                    requestBuilder.send();
                } catch (RequestException e) {
                    e.printStackTrace();
                }
            }
        };

        // Execute the timer to expire 2 seconds in the future
        timer.schedule(2000);
    }
}
