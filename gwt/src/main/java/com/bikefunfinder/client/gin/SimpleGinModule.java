package com.bikefunfinder.client.gin;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class SimpleGinModule extends AbstractGinModule  {

    protected void configure() {
        bind(ClientFactory.class).toProvider(ClientFactoryImplProvider.class).in(Singleton.class);
        bind(RamObjectCache.class).toProvider(RamObjectCacheImplProvider.class).in(Singleton.class);

    }
}

