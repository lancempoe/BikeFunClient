package com.bikefunfinder.client.bootstrap;

import com.googlecode.gwtphonegap.client.*;


/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PhoneGapDependentBootScrapper implements PhoneGapAvailableHandler, PhoneGapTimeoutHandler {

    protected final PhoneGap phoneGapApi;

    public PhoneGapDependentBootScrapper(PhoneGap phoneGapApi) {
        this.phoneGapApi = phoneGapApi;
    }

    protected abstract void phoneGapAvailable();

    protected abstract void phoneGapInitFailure();

    @Override
    public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
        phoneGapAvailable();
    }

    @Override
    public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
        phoneGapInitFailure();
    }
}