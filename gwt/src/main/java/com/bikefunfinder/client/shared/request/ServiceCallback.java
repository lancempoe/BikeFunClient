package com.bikefunfinder.client.shared.request;
/*
 * @author: tneuwerth
 * @created 5/27/13 2:53 PM
 */

public interface ServiceCallback<T> {
    void onError();
    void onResponseReceived(T type);

    public enum NetworkStatus {
        Successful, UnSuccessful;
    }
}