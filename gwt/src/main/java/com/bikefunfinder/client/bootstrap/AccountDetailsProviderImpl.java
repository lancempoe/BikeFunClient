package com.bikefunfinder.client.bootstrap;
/*
 * @author: tneuwerth
 * @created 6/6/13 3:43 PM
 */

import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.mgwt.storage.client.Storage;

import java.util.Date;

public class AccountDetailsProviderImpl implements AccountDetailsProvider {
    private final LocalStorageWrapper localStorageWrapper;
    private final PhoneGap phoneGap;


    public AccountDetailsProviderImpl(LocalStorageWrapper localStorageWrapper, PhoneGap phoneGap) {
        this.localStorageWrapper = localStorageWrapper;
        this.phoneGap = phoneGap;
    }

    @Override
    public AccountDetails getAccountDetails() {
        AccountDetails accountDetails;
        Storage storageInterface = localStorageWrapper.getStorageInterfaceMyBeNull();

        if(storageInterface==null) {
            accountDetails = makeNewAccountSecrets();
        } else {
            String key = storageInterface.getItem("uniqueUserIdentifier");
            String uuid = storageInterface.getItem("uuid");

            if(key==null || key.isEmpty()) {
                accountDetails = makeNewAccountSecrets();
            } else if (uuid == null || uuid.isEmpty()) {
                accountDetails = makeNewAccountSecrets();
            } else {
                accountDetails = new AccountDetails(uuid, key);
            }

            storageInterface.setItem("uniqueUserIdentifier", accountDetails.key);
            storageInterface.setItem("uuid", accountDetails.uuid);
        }

        return accountDetails;
    }

    private AccountDetails makeNewAccountSecrets() {
        Date now = new Date();
        String key = Long.toString(now.getTime());
        String uuid = phoneGap.getDevice().getUuid();
        return new AccountDetails(key, uuid);
    }
}