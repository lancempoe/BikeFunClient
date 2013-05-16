package com.bikefunfinder.client.gin;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(SimpleGinModule.class)
public interface Injector extends Ginjector {
    public static final Injector INSTANCE = GWT.create(Injector.class);
    ClientFactory getClientFactory();
    RamObjectCache getRamObjectCache();
}

