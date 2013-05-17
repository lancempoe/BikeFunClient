package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 4/25/13 6:59 PM
 */

import com.bikefunfinder.client.shared.model.OAuth;

public class OAuthJSOWrapper implements DescribeableAsString<OAuth> {
    @Override
    public String describeAsString(OAuth jsoObject) {
        final String foreignId = (jsoObject==null) ? "null" :jsoObject.getForeignId();
        final String foreignIdType = (jsoObject==null) ? "null" :jsoObject.getForeignIdType();
        final String accessToken = (jsoObject==null) ? "null" :jsoObject.getAccessToken().toString();
        return "OAuth("+
                    "foreignId: " + foreignId +
                    "foreignIdType: " + foreignIdType +
                    "accessToken: " + accessToken +
                ")";
    }
}
