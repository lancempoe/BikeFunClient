package com.bikefunfinder.client.gin;
/*
 * @author: tneuwerth
 * @created 5/15/13 6:33 PM
 */

import com.google.inject.Provider;

public class RamObjectCacheImplProvider implements Provider<RamObjectCacheImpl> {
    @Override
    public RamObjectCacheImpl get() {
        return new RamObjectCacheImpl();
    }
}
