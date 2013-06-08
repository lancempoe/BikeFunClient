package com.bikefunfinder.client.shared.request.management;
/*
 * @author: tneuwerth
 * @created 6/2/13 2:23 PM
 */

public class NoCacheStrategy<T> implements CacheStrategy<T> {
    public static final NoCacheStrategy INSTANCE = new NoCacheStrategy();
    @Override
    public void cacheType(T type) {
        // we do nothing
    }

    @Override
    public T getCachedType() {
        return null;  //we store nothing!
    }
}
