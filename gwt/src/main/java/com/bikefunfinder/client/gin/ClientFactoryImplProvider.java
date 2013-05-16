package com.bikefunfinder.client.gin;

import com.bikefunfinder.client.bootstrap.ClientFactoryGwtImpl;
import com.google.inject.Provider;

public class ClientFactoryImplProvider implements Provider<ClientFactoryGwtImpl> {
    @Override
    public ClientFactoryGwtImpl get() {
        return new ClientFactoryGwtImpl();
    }
}