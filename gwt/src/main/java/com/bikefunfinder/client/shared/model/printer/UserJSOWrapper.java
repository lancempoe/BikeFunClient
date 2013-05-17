package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 4/25/13 6:53 PM
 */

import com.bikefunfinder.client.shared.model.User;

public class UserJSOWrapper implements DescribeableAsString<User>  {
    @Override
    public String describeAsString(User jsoObject) {
        final String id = (jsoObject==null) ? "null" :jsoObject.getId();
        final String userName = (jsoObject==null) ? "null" :jsoObject.getUserName();
        final String email = (jsoObject==null) ? "null" :jsoObject.getEmail();
        final String describe = (jsoObject==null) ? "null" :JSODescriber.describe(jsoObject.getOAuth());
        final String describe1 = (jsoObject==null) ? "null" :JSODescriber.describe(jsoObject.getDeviceAccount());
        return "User("+
                    "id: " + id +
                    "userName: " + userName +
                    "email: " + email +
                    "oAuth: " + describe +
                    "deviceAccount: " + describe1 +
                ")";
    }
}
