package com.bikefunfinder.client.shared.model;
/*
 * @author: tneuwerth
 * @created 3/21/13 11:13 AM
 */

import com.google.gwt.core.client.JavaScriptObject;

public class UserData extends JavaScriptObject {
    protected UserData() {}

    public final native String getKode() /*-{ return this.kode; }-*/;
    public final native String getNama()  /*-{ return this.nama;  }-*/;

    public final String describeAsString() {
        return getKode() + ":" + getNama();
    }
}