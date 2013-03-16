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
package com.googlecode.gwtphonegap.showcase.client.notification;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.notification.AlertCallback;
import com.googlecode.gwtphonegap.client.notification.ConfirmCallback;
import com.googlecode.gwtphonegap.showcase.bootstrap.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;

public class NotificationActivity extends NavBaseActivity implements NotificationDisplay.Presenter {
    private final NotificationDisplay display;
    private final PhoneGap phoneGap;

    public NotificationActivity(ClientFactory clientFactory) {
        super(clientFactory);

        this.display = clientFactory.getNotificationDisplay();
        this.phoneGap = clientFactory.getPhoneGap();

    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        display.setPresenter(this);

        panel.setWidget(display);

    }

    @Override
    public void onAlertButtonPressed() {
        phoneGap.getNotification().alert("daniel says hi", new AlertCallback() {

            @Override
            public void onOkButtonClicked() {

            }
        }, "gwt-phonegap", "buttonText");

    }

    @Override
    public void onConfirmButtonPressed() {
        phoneGap.getNotification().confirm("question?", new ConfirmCallback() {

            @Override
            public void onConfirm(int button) {

            }
        }, "gwt-phonegap");

    }

    @Override
    public void onBeepButtonPressed() {
        phoneGap.getNotification().beep(2);

    }

    @Override
    public void onVibrateButtonPressed() {
        phoneGap.getNotification().vibrate(2500);

    }
}
