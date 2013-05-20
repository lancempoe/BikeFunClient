package com.bikefunfinder.client.bootstrap.db;
/*
 * @author: tneuwerth
 * @created 4/25/13 6:44 PM
 */

public class DBKeys {
    private DBKeys() {
        throw new RuntimeException("hands off sucker this is private!");
    }

    public static final String ANONYMOUS_USER = "anonymous_user";
    public static final String USER = "user";
    public static final String PRIOR_SEARCH = "prior_search";
}
