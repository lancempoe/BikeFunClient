package com.bikefunfinder.client.shared.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
/**
 * Created with IntelliJ IDEA.
 * User: nathanfrost
 * Date: 6/22/13
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */
public interface FontAwesomeStyleBundle extends ClientBundle {
    @Source("font-awesome.css")
    TextResource css();

    public static final FontAwesomeStyleBundle INSTANCE = GWT.create(FontAwesomeStyleBundle.class);
}
