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
package com.googlecode.gwtphonegap.showcase.client.camera;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.camera.PictureCallback;
import com.googlecode.gwtphonegap.client.camera.PictureOptions;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;

public class CameraActivity extends NavBaseActivity implements CameraDisplay.Presenter {

  private final PhoneGap phoneGap;
  private final ClientFactory clientFactory;

  public CameraActivity(ClientFactory clientFactory) {
    super(clientFactory);

    this.clientFactory = clientFactory;
    this.phoneGap = clientFactory.getPhoneGap();

  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    CameraDisplay display = clientFactory.getCameraDisplay();

    display.setPresenter(this);

    panel.setWidget(display);
  }

  @Override
  public void onCameraButtonPressed() {
    PictureOptions options = new PictureOptions(25);
    options.setDestinationType(PictureOptions.DESTINATION_TYPE_DATA_URL);
    options.setSourceType(PictureOptions.PICTURE_SOURCE_TYPE_CAMERA);

    phoneGap.getCamera().getPicture(options, new PictureCallback() {

      @Override
      public void onSuccess(String data) {
        clientFactory.getCameraDisplay().displayFoto("data:image/jpeg;base64," + data);
      }

      @Override
      public void onFailure(String message) {
        // TODO better error display
        Window.alert("failure");

      }
    });

  }

}
