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
package com.googlecode.gwtphonegap.showcase.client.geolocation;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationCallback;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationOptions;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationWatcher;
import com.googlecode.gwtphonegap.client.geolocation.Position;
import com.googlecode.gwtphonegap.client.geolocation.PositionError;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;
import com.googlecode.gwtphonegap.showcase.client.geolocation.GeolocationDisplay.Presenter;

public class GeolocationActivity extends NavBaseActivity implements Presenter {

  private final PhoneGap phoneGap;
  private final GeolocationDisplay display;

  private GeolocationWatcher watcher;

  public GeolocationActivity(ClientFactory clientFactory) {
    super(clientFactory);

    this.display = clientFactory.getGeolocationDisplay();
    this.phoneGap = clientFactory.getPhoneGap();

  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {

    display.setPresenter(this);

    display.getStartStopButton().setText("Start");

    display.getAccuracy().setText("");
    display.getAltitude().setText("");
    display.getAltitudeAccuracy().setText("");
    display.getHeading().setText("");
    display.getLatidute().setText("");
    display.getLongitude().setText("");
    display.getTimeStamp().setText("");

    panel.setWidget(display);

  }

  @Override
  public void onStop() {
    display.setPresenter(null);

    if (watcher != null) {

      phoneGap.getGeolocation().clearWatch(watcher);
      watcher = null;
    }
  }

  @Override
  public void onStartStopButtonPressed() {

    if (watcher == null) {
      GeolocationOptions options = new GeolocationOptions();
      options.setFrequency(1000);
      watcher = phoneGap.getGeolocation().watchPosition(options, new GeoLocationCallbackImpl());
      display.getStartStopButton().setText("Stop");

    } else {
      phoneGap.getGeolocation().clearWatch(watcher);
      watcher = null;
      display.getStartStopButton().setText("Start");

      display.getAccuracy().setText("");
      display.getAltitude().setText("");
      display.getAltitudeAccuracy().setText("");
      display.getHeading().setText("");
      display.getLatidute().setText("");
      display.getLongitude().setText("");
      display.getTimeStamp().setText("");
      display.getSpeed().setText("");

    }

  }

  private class GeoLocationCallbackImpl implements GeolocationCallback {

    @Override
    public void onSuccess(Position position) {

      display.getAccuracy().setText("" + position.getCoordinates().getAccuracy());
      display.getAltitude().setText("" + position.getCoordinates().getAltitude());
      display.getAltitudeAccuracy().setText("" + position.getCoordinates().getAltitudeAccuracy());
      display.getHeading().setText("" + position.getCoordinates().getHeading());
      display.getLatidute().setText("" + position.getCoordinates().getLatitude());
      display.getLongitude().setText("" + position.getCoordinates().getLongitude());
      display.getSpeed().setText("" + position.getCoordinates().getSpeed());
      display.getTimeStamp().setText("" + position.getTimeStamp());

    }

    @Override
    public void onFailure(PositionError error) {
      switch (error.getCode()) {
        case PositionError.PERMISSION_DENIED:
          Window.alert("no permission - stoping watcher");

          break;
        case PositionError.POSITION_UNAVAILABLE:
          Window.alert("unavaible");
          break;
        case PositionError.TIMEOUT:
          Window.alert("timeout");
          break;
        default:
          break;
      }

      if (watcher != null) {
        phoneGap.getGeolocation().clearWatch(watcher);
        watcher = null;
        display.getStartStopButton().setText("Start");

      }

    }

  }

}
