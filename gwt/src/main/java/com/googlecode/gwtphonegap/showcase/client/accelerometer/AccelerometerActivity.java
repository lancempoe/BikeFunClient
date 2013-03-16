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
package com.googlecode.gwtphonegap.showcase.client.accelerometer;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.accelerometer.Acceleration;
import com.googlecode.gwtphonegap.client.accelerometer.AccelerationCallback;
import com.googlecode.gwtphonegap.client.accelerometer.AccelerationOptions;
import com.googlecode.gwtphonegap.client.accelerometer.AccelermeterMock;
import com.googlecode.gwtphonegap.client.accelerometer.AccelerometerWatcher;
import com.googlecode.gwtphonegap.client.accelerometer.browser.AccelerationBrowserImpl;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;
import com.googlecode.gwtphonegap.showcase.client.accelerometer.AccelerometerDisplay.Presenter;

public class AccelerometerActivity extends NavBaseActivity implements Presenter {

  private final PhoneGap phoneGap;

  private AccelerometerWatcher watcher = null;

  private AccelerometerDisplay display;

  public AccelerometerActivity(ClientFactory clientFactory) {
    super(clientFactory);

    this.phoneGap = clientFactory.getPhoneGap();
    display = clientFactory.getAccelerometerDisplay();

  }

  public interface Display {
    public Widget asWidget();

    public HasClickHandlers getStartStopButton();

    public void setXValue(String string);

    public void setYValue(String string);

    public void setZValue(String string);

    public void setTimeValue(String value);
  }

  private class AccelerometerCallbackImpl implements AccelerationCallback {

    public AccelerometerCallbackImpl() {

    }

    @Override
    public void onSuccess(Acceleration acceleration) {
      display.getXValue().setText("" + acceleration.getX());
      display.getYValue().setText("" + acceleration.getY());
      display.getZValue().setText("" + acceleration.getZ());
      display.getTimeStamp().setText("" + acceleration.getTimeStamp());

    }

    @Override
    public void onFailure() {
      // TODO display error in app
      Window.alert("failure while reading accel");

    }

  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {

    display.setPresenter(this);

    display.getStartStopButton().setText("Start");

    if (!phoneGap.isPhoneGapDevice()) {
      AccelermeterMock mock = ((AccelermeterMock) phoneGap.getAccelerometer());

      ArrayList<Acceleration> list = new ArrayList<Acceleration>();
      list.add(new AccelerationBrowserImpl(1, 1, 1));
      list.add(new AccelerationBrowserImpl(1, 0, 0));
      list.add(new AccelerationBrowserImpl(0, 1, 0));
      list.add(new AccelerationBrowserImpl(0, 0, 1));
      mock.setMockValues(list);
    }

    panel.setWidget(display);

  }

  @Override
  public void onStop() {
    super.onStop();
    display.setPresenter(null);

    if (watcher != null) {
      phoneGap.getAccelerometer().clearWatch(watcher);
      watcher = null;
    }
  }

  @Override
  public void onStartStopButtonPressed() {
    if (watcher == null) {
      final AccelerationOptions options = new AccelerationOptions();
      options.setFrequency(50);
      watcher = phoneGap.getAccelerometer().watchAcceleration(options, new AccelerometerCallbackImpl());
      display.getStartStopButton().setText("Stop");
    } else {
      phoneGap.getAccelerometer().clearWatch(watcher);
      watcher = null;
      display.getStartStopButton().setText("Start");
      display.getXValue().setText("");
      display.getYValue().setText("");
      display.getZValue().setText("");
      display.getTimeStamp().setText("");
    }

  }
}
