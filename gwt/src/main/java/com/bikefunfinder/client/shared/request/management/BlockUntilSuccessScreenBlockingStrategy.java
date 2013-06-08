package com.bikefunfinder.client.shared.request.management;
/*
 * @author: tneuwerth
 * @created 6/8/13 12:34 AM
 */

import com.bikefunfinder.client.shared.widgets.LoadingScreen;

public class BlockUntilSuccessScreenBlockingStrategy implements ScreenBlockingStrategy {
    @Override
    public void afterConstruction() {
        LoadingScreen.openLoaderPanel();
    }

    @Override
    public void beforeCallbackSuccessfulIsCalled() {
        LoadingScreen.closeLoader();
    }

    @Override
    public void whenNetworkErrorAndRquestWantsToGiveUp() {
        LoadingScreen.closeLoader();
    }

    @Override
    public void whenServerRespondsToCallWithClientErrorMessage() {
        LoadingScreen.closeLoader();
    }
}
