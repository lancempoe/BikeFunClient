package com.bikefunfinder.client;

/*
 * @author: tneuwerth
 * @created 3/19/13 6:47 PM
 */

import com.bikefunfinder.client.bootstrap.PhoneGapDependentBootScrapper;
import com.bikefunfinder.client.bootstrap.PhoneGapDependentBootScrapperImpl;
import com.bikefunfinder.client.bootstrap.UncaughtExceptionHandlerFactory;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;
import com.googlecode.mgwt.ui.client.MGWTStyle;

import java.util.logging.Logger;

public class BFFEntrypoint implements EntryPoint {
    private Logger log = Logger.getLogger(getClass().getName());


    @Override
    public void onModuleLoad() {
        GWT.UncaughtExceptionHandler uncaughtExceptionHandler = UncaughtExceptionHandlerFactory.createApplicationUncaughtExceptionHandler(log);
        GWT.setUncaughtExceptionHandler(uncaughtExceptionHandler);

        PhoneGap phoneGapApi = GWT.create(PhoneGap.class);
        PhoneGapDependentBootScrapper applicationBootStrapper = new PhoneGapDependentBootScrapperImpl(phoneGapApi);
        phoneGapApi.addHandler((PhoneGapAvailableHandler) applicationBootStrapper);
        phoneGapApi.addHandler((PhoneGapTimeoutHandler) applicationBootStrapper);
        phoneGapApi.initializePhoneGap();


        //this will create a link element at the end of head
        MGWTStyle.getTheme().getMGWTClientBundle().getMainCss().ensureInjected();
        //append your own css as last thing in the head

    }
}