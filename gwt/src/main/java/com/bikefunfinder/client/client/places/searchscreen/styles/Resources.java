package com.bikefunfinder.client.client.places.searchscreen.styles;
/*
 * @author: tneuwerth
 * @created 5/23/13 10:44 PM
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface Resources extends ClientBundle {
    public static final Resources INSTANCE =  GWT.create(Resources.class);

    @Source("ScreenActivit.css")
    ScreenActivityCssResource css();
}