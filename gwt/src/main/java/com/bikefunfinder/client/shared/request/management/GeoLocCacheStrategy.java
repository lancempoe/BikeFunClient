package com.bikefunfinder.client.shared.request.management;
/*
 * @author: tneuwerth
 * @created 6/6/13 12:12 AM
 */

import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.gin.RamObjectCache;
import com.bikefunfinder.client.shared.model.GeoLoc;

public class GeoLocCacheStrategy implements CacheStrategy<GeoLoc> {
    public static final GeoLocCacheStrategy INSTANCE = new GeoLocCacheStrategy();
    private RamObjectCache ramObjectCache = Injector.INSTANCE.getRamObjectCache();

    private GeoLocCacheStrategy() {}

    @Override
    public void cacheType(GeoLoc type) {
        ramObjectCache.setCurrentPhoneGeoLoc(type);
    }

    @Override
    public GeoLoc getCachedType() {
        return ramObjectCache.getCurrentPhoneGeoLoc();
    }
}
