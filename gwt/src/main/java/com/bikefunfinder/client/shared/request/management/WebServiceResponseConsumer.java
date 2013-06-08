package com.bikefunfinder.client.shared.request.management;
/*
 * @author: tneuwerth
 * @created 5/27/13 2:53 PM
 */

public interface WebServiceResponseConsumer<T> {
    void onResponseReceived(T type);
}