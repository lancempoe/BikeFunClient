package com.bikefunfinder.client.shared.request.ratsnest;
/*
 * @author: tneuwerth
 * @created 6/6/13 4:21 PM
 */

import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.model.AnonymousUser;

public class AnnonymousUserCacheStrategy implements CacheStrategy<AnonymousUser> {
    public static final AnnonymousUserCacheStrategy INSTANCE = new AnnonymousUserCacheStrategy();
    private RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();

    private AnnonymousUserCacheStrategy() {}

    @Override
    public void cacheType(AnonymousUser type) {
        ramObjectCache.setAnonymousUser(type);
    }

    @Override
    public AnonymousUser getCachedType() {
        return ramObjectCache.getAnonymousUser();
    }
}