package com.bikefunfinder.client.shared.request.management;
/*
 * @author: tneuwerth
 * @created 6/8/13 12:32 AM
 */

import com.bikefunfinder.client.shared.widgets.LoadingScreen;

public interface ScreenBlockingStrategy {
    public void afterConstruction();
    public void beforeCallbackSuccessfulIsCalled();
    public void whenNetworkErrorAndRquestWantsToGiveUp();
    public void whenServerRespondsToCallWithClientErrorMessage();

}
