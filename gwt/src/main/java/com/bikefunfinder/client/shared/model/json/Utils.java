package com.bikefunfinder.client.shared.model.json;
/*
 * @author: tneuwerth
 * @created 4/25/13 7:35 PM
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.Window;

public class Utils {
    private Utils() {
        throw new RuntimeException("hey, this is private no touchies!");
    }

    public static <T extends JavaScriptObject> T castJsonTxtToJSOObject(String json) {
        if(!JsonUtils.safeToEval(json)) {
            Window.alert("Woah nelly.  Unknown date.");
        }

        //Window.alert("PepPep: "+json);
        //Window.alert("jsonLenght: "+json.length());


        T castingObject = JsonUtils.safeEval(json);
        return castingObject;
    }
}
