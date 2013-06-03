package com.bikefunfinder.client.shared.widgets;
/*
 * @author: tneuwerth
 * @created 5/23/13 9:19 PM
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface Resources extends ClientBundle {
    public static final Resources INSTANCE =  GWT.create(Resources.class);

    @Source("HeaderList.css")
    HeaderListCssResource css();

    @Source("loadingScreen.css")
    LoadingScreenResource loadingScreenCss();
}