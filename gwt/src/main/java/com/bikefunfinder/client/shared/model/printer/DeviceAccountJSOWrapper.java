package com.bikefunfinder.client.shared.model.printer;
/*
 * @author: tneuwerth
 * @created 4/25/13 7:18 PM
 */

import com.bikefunfinder.client.shared.model.DeviceAccount;

public class DeviceAccountJSOWrapper implements DescribeableAsString<DeviceAccount> {
    @Override
    public final String describeAsString(DeviceAccount jsoObject) {
        final String deviceUUID = (jsoObject==null) ? "null" : jsoObject.getDeviceUUID();
        final String key = (jsoObject==null) ? "null" : jsoObject.getKey();

        return "DeviceAccount(" +
                    "deviceUUID:" + deviceUUID + "," +
                    "key:" + key + "," +
                ")";
    }
}
