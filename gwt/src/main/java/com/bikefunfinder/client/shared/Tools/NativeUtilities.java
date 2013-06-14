package com.bikefunfinder.client.shared.Tools;

import com.googlecode.mgwt.ui.client.MGWT;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 6/5/13
 * Time: 7:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class NativeUtilities {

    public static void trackPage(String pageName) {
        if( MGWT.getOsDetection().isPhone() ) {
            trackPageNative(pageName);
        }
    }

    private static native void trackPageNative(String pageName) /*-{
        $wnd.trackPage(pageName);
     }-*/;

    public static void partialWakeLock() {
        if( MGWT.getOsDetection().isPhone() ) {
            partialWakeLockNative();
        }
    }

    public static void releasePartialWakeLock() {
        if( MGWT.getOsDetection().isPhone() ) {
            releasePartialWakeLockNative();
        }
    }

    public static native void partialWakeLockNative() /*-{
        //$wnd.dim();
     }-*/;

    public static native void releasePartialWakeLockNative() /*-{
        //$wnd.release();
     }-*/;

}
