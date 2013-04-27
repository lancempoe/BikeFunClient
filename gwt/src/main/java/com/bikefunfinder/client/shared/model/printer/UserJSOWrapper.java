package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 4/25/13 6:53 PM
 */

import com.bikefunfinder.client.shared.model.User;

public class UserJSOWrapper implements DescribeableAsString<User>  {
    @Override
    public String describeAsString(User jsoObject) {
        return "User("+
                    "id: " + jsoObject.getId() +
                    "userName: " + jsoObject.getUserName() +
                    "email: " + jsoObject.getEmail() +
                    "oAuth: " + JSODescriber.describe(jsoObject.getOAuth()) +
                    "deviceAccount: " + JSODescriber.describe(jsoObject.getDeviceAccount()) +
                ")";
    }
}
