package com.googlecode.gwtphonegap.showcase.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

import com.googlecode.gwtphonegap.showcase.client.OverviewDisplay.Presenter;
import com.googlecode.gwtphonegap.showcase.client.about.AboutPlace;
import com.googlecode.gwtphonegap.showcase.client.accelerometer.AccelerometerPlace;
import com.googlecode.gwtphonegap.showcase.client.camera.CameraPlace;
import com.googlecode.gwtphonegap.showcase.client.compass.CompassPlace;
import com.googlecode.gwtphonegap.showcase.client.connection.ConnectionPlace;
import com.googlecode.gwtphonegap.showcase.client.contact.ContactPlace;
import com.googlecode.gwtphonegap.showcase.client.device.DevicePlace;
import com.googlecode.gwtphonegap.showcase.client.event.EventPlace;
import com.googlecode.gwtphonegap.showcase.client.file.FilePlace;
import com.googlecode.gwtphonegap.showcase.client.geolocation.GeolocationPlace;
import com.googlecode.gwtphonegap.showcase.client.inappbrowser.InAppBrowserPlace;
import com.googlecode.gwtphonegap.showcase.client.media.MediaPlace;
import com.googlecode.gwtphonegap.showcase.client.model.PGModule;
import com.googlecode.gwtphonegap.showcase.client.notification.NotificationPlace;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends MGWTAbstractActivity implements Presenter {

  private final ClientFactory clientFactory;
  private List<PGModule> currentList;

  public OverviewActivity(ClientFactory clientFactory) {
    this.clientFactory = clientFactory;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    OverviewDisplay display = clientFactory.getOverviewDisplay();

    currentList = getModuleList();

    display.display(currentList);

    display.setPresenter(this);

    panel.setWidget(display);
  }

  private List<PGModule> getModuleList() {
    ArrayList<PGModule> list = new ArrayList<PGModule>();

    list.add(new PGModule("Accelerometer"));
    list.add(new PGModule("Camera"));
    list.add(new PGModule("Compass"));
    list.add(new PGModule("Connection"));
    list.add(new PGModule("Contacts"));
    list.add(new PGModule("Device"));
    list.add(new PGModule("Event"));
    list.add(new PGModule("File"));
    list.add(new PGModule("Geolocation"));
    list.add(new PGModule("Media"));
    list.add(new PGModule("Notification"));

    list.add(new PGModule("InAppBrowser"));
    return list;
  }

  @Override
  public void onCellSelected(int index) {
    switch (index) {
      case 0:
        clientFactory.getPlaceController().goTo(new AccelerometerPlace());
        break;

      case 1:
        clientFactory.getPlaceController().goTo(new CameraPlace());
        break;

      case 2:
        clientFactory.getPlaceController().goTo(new CompassPlace());
        break;

      case 3:
        clientFactory.getPlaceController().goTo(new ConnectionPlace());
        break;

      case 4:
        clientFactory.getPlaceController().goTo(new ContactPlace());
        break;

      case 5:
        clientFactory.getPlaceController().goTo(new DevicePlace());
        break;

      case 6:
        clientFactory.getPlaceController().goTo(new EventPlace());
        break;
      case 7:
        clientFactory.getPlaceController().goTo(new FilePlace());
        break;
      case 8:
        clientFactory.getPlaceController().goTo(new GeolocationPlace());
        break;
      case 9:
        clientFactory.getPlaceController().goTo(new MediaPlace());
        break;
      case 10:
        clientFactory.getPlaceController().goTo(new NotificationPlace());
        break;

      case 11:
        clientFactory.getPlaceController().goTo(new InAppBrowserPlace());
        break;
    }

  }

  @Override
  public void onAboutButton() {
    clientFactory.getPlaceController().goTo(new AboutPlace());

  }

}
