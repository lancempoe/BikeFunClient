package com.bikefunfinder.client.bootstrap;
/*
 * @author: tneuwerth
 * @created 4/17/13 9:16 PM
 */

import com.googlecode.mgwt.storage.client.LocalStorageGwtImpl;
import com.googlecode.mgwt.storage.client.Storage;

public class LocalStorageWrapper {
    final Storage storageInterface;

    public LocalStorageWrapper() {
        if(com.google.gwt.storage.client.Storage.isLocalStorageSupported()) {
            this.storageInterface = new LocalStorageGwtImpl();
        } else {
            this.storageInterface = null;
        }
    }

    public Storage getStorageInterfaceMyBeNull() {
        return storageInterface;
    }

    public boolean isStorageAvailable() {
        if(null==storageInterface) {
            return false;
        }

        return true;
    }
}
