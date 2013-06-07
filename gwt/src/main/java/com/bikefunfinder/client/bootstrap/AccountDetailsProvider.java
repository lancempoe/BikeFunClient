package com.bikefunfinder.client.bootstrap;
/*
 * @author: tneuwerth
 * @created 6/6/13 3:42 PM
 */

public interface AccountDetailsProvider {
    public class AccountDetails {
        public final String uuid;
        public final String key;

        public AccountDetails(String uuid, String key) {
            this.uuid = uuid;
            this.key = key;
        }
    }

    public AccountDetails getAccountDetails();
}
