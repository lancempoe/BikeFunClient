package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 3/21/13 6:24 PM
 */

import com.google.gwt.core.client.JavaScriptObject;

interface DescribeableAsString<T extends JavaScriptObject>  {
    public String describeAsString(T jsoObject);
}