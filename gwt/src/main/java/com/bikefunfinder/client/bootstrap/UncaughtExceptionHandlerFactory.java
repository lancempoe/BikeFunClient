package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class UncaughtExceptionHandlerFactory {

    public static GWT.UncaughtExceptionHandler createApplicationUncaughtExceptionHandler(final Logger log) {
        return new GWT.UncaughtExceptionHandler() {

            @Override
            public void onUncaughtException(Throwable e) {
                Window.alert("uncaught: " + e.getLocalizedMessage());
                Window.alert(e.getMessage());
                log.log(Level.SEVERE, "uncaught exception", e);
                logMessageToDeviceLog(e.getMessage());
            }
        };
    }

    public static final native void logMessageToDeviceLog(String message) /*-{
		console.log(message);
    }-*/;
}
