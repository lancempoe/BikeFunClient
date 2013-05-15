package com.bikefunfinder.client;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.bootstrap.ClientFactoryGwtImpl;
import com.bikefunfinder.client.bootstrap.PhoneGapDependentBootScrapper;
import com.bikefunfinder.client.bootstrap.PhoneGapDependentBootScrapperImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;

public class SimpleGinModule extends AbstractGinModule implements Provider<ClientFactoryGwtImpl> {

    protected void configure() {
        bind(ClientFactory.class).toProvider(SimpleGinModule.class).in(Singleton.class);
    }

    @Override
    public ClientFactoryGwtImpl get() {
        return new ClientFactoryGwtImpl();
    }
}

