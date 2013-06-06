package com.bikefunfinder.client.shared.Tools;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 6/5/13
 * Time: 7:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class NativeUtilities {

    public static native void trackPage(String pageName) /*-{
        $wnd.trackPage(pageName);
     }-*/;

}
