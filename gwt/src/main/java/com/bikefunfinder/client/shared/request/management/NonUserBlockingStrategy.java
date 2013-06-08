package com.bikefunfinder.client.shared.request.management;
/*
 * @author: tneuwerth
 * @created 6/8/13 12:41 AM
 */

public class NonUserBlockingStrategy implements ScreenBlockingStrategy {
    public static final NonUserBlockingStrategy INSTANCE = new NonUserBlockingStrategy();
    private NonUserBlockingStrategy() {
    }

    @Override
    public void afterConstruction() {
        //honey badger don't give a fuck
    }

    @Override
    public void beforeCallbackSuccessfulIsCalled() {
        //honey badger don't give a fuck
    }

    @Override
    public void whenNetworkErrorAndRquestWantsToGiveUp() {
        //honey badger don't give a fuck
    }

    @Override
    public void whenServerRespondsToCallWithClientErrorMessage() {
        //honey badger don't give a fuck
    }
}
