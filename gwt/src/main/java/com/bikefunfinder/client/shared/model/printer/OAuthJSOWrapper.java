package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 4/25/13 6:59 PM
 */

import com.bikefunfinder.client.shared.model.OAuth;

public class OAuthJSOWrapper implements DescribeableAsString<OAuth> {
    @Override
    public String describeAsString(OAuth jsoObject) {
        return "OAuth("+
                    "foreignId: " + jsoObject.getForeignId() +
                    "foreignIdType: " + jsoObject.getForeignIdType() +
                    "accessToken: " + jsoObject.getAccessToken() +
                ")";
    }
}
