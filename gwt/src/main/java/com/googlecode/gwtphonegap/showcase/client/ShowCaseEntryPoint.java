/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.gwtphonegap.showcase.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;
import com.googlecode.gwtphonegap.showcase.bootstrap.PhoneGapDependentBootScrapper;
import com.googlecode.gwtphonegap.showcase.bootstrap.PhoneGapDependentBootScrapperImpl;
import com.googlecode.gwtphonegap.showcase.bootstrap.UncaughtExceptionHandlerFactory;

import java.util.logging.Logger;

public class ShowCaseEntryPoint implements EntryPoint {
    private Logger log = Logger.getLogger(getClass().getName());

    @Override
    public void onModuleLoad() {

        GWT.UncaughtExceptionHandler uncaughtExceptionHandler = UncaughtExceptionHandlerFactory.createApplicationUncaughtExceptionHandler(log);
        GWT.setUncaughtExceptionHandler(uncaughtExceptionHandler);

        final PhoneGap phoneGapApi = GWT.create(PhoneGap.class);
        PhoneGapDependentBootScrapper applicationBootStrapper = new PhoneGapDependentBootScrapperImpl(phoneGapApi);
        phoneGapApi.addHandler((PhoneGapAvailableHandler) applicationBootStrapper);
        phoneGapApi.addHandler((PhoneGapTimeoutHandler) applicationBootStrapper);
        phoneGapApi.initializePhoneGap();
    }
}