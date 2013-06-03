package com.bikefunfinder.client.shared.request.converters;
/*
 * @author: tneuwerth
 * @created 5/27/13 6:19 PM
 */

public interface JsonObjectConverter<T> {
    public T convert(String jsonTxt);
}
