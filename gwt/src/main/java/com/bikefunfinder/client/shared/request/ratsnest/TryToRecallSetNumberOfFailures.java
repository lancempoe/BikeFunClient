package com.bikefunfinder.client.shared.request.ratsnest;
/*
 * @author: tneuwerth
 * @created 6/6/13 12:56 AM
 */

import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Timer;

public class TryToRecallSetNumberOfFailures<T> implements RefireStrategy<T> {
    public final int numberOfTimesToRetry;
    public int timesRetried;

    public TryToRecallSetNumberOfFailures(int numberOfTimesToRetry) {
        this.numberOfTimesToRetry = numberOfTimesToRetry;
    }

    @Override
    public boolean shouldCloseWaitingDialog() {
        return  timesRetried < numberOfTimesToRetry;
    }

    @Override
    public void refire(final RepeatableRequestBuilder requestBuilder, final RequestCallBackHandlerStack<T> thizz) {

        if(timesRetried < numberOfTimesToRetry) {
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
            timesRetried++;
        }
    }
}

