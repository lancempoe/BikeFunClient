package com.bikefunfinder.client.shared.request.ratsnest;
/*
 * @author: tneuwerth
 * @created 6/2/13 2:49 PM
 */

import com.google.gwt.http.client.RequestBuilder;

public class RepeatableRequestBuilder extends RequestBuilder {
    private final String payload;

    public RepeatableRequestBuilder(Method httpMethod, String url, String payload) {
        super(httpMethod, url);
        this.payload = payload;
    }

    public RepeatableRequestBuilder(String httpMethod, String url,  String payload) {
        super(httpMethod, url);
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }
}
