package com.bikefunfinder;
/*
 * @author: tneuwerth
 * @created 3/21/13 4:41 PM
 */

import com.bikefunfinder.client.shared.model.UserData;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.Window;
import org.junit.Test;

public class RootJsonMashallTester {
    @Test
    public void testRootJsonToObjectMarshall() {
        String wrappedJson = "[{\"kode\":\"002\",\"nama\":\"bambang gentolet\"},{\"kode\":\"012\",\"nama\":\"Algiz\"}]";
        //final Root composed = Root.overlay(json);
        System.out.println("is safe to eval..? " + JsonUtils.safeToEval(wrappedJson));

//        final Root composed = JsonUtils.safeEval(wrappedJson);
//        Window.alert("composed = " + composed);


        JsArray<UserData> uda = JsonUtils.safeEval(wrappedJson);
        Window.alert("uda size: "+uda.length());
        int size = uda.length();
        for(int index=0; index<size; index++) {
            UserData userData = uda.get(index);
            Window.alert("full :"+userData.getFullData());
        }
    }
}
