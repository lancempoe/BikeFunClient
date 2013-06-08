package com.bikefunfinder.client.shared.request.management;
/*
 * @author: tneuwerth
 * @created 6/2/13 2:18 PM
 */

public interface CacheStrategy<T> {
    public void cacheType(T type);
    public T getCachedType();
}
