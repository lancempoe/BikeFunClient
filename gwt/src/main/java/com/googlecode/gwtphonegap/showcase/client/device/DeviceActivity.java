/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.gwtphonegap.showcase.client.device;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;

public class DeviceActivity extends NavBaseActivity implements DeviceDisplay.Presenter {

    private final DeviceDisplay display;
    private final PhoneGap phoneGap;

    public DeviceActivity(ClientFactory clientFactory) {
        this.display = null;//clientFactory.getDeviceDisplay();
        this.phoneGap = clientFactory.getPhoneGap();

    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {

        display.setPresenter(this);

        display.getVersion().setHTML(phoneGap.getDevice().getVersion());
        display.getName().setHTML(phoneGap.getDevice().getName());
        display.getPhoneGapVersion().setHTML(phoneGap.getDevice().getPhoneGapVersion());
        display.getPlatform().setHTML(phoneGap.getDevice().getPlatform());
        display.getUUID().setHTML(phoneGap.getDevice().getUuid());

        panel.setWidget(display);

    }

}
