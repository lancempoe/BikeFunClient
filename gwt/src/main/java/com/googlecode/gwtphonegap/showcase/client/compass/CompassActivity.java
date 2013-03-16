package com.googlecode.gwtphonegap.showcase.client.compass;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.compass.CompassCallback;
import com.googlecode.gwtphonegap.client.compass.CompassError;
import com.googlecode.gwtphonegap.client.compass.CompassHeading;
import com.googlecode.gwtphonegap.client.compass.CompassOptions;
import com.googlecode.gwtphonegap.client.compass.CompassWatcher;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;
import com.googlecode.gwtphonegap.showcase.client.compass.CompassDisplay.Presenter;

public class CompassActivity extends NavBaseActivity implements Presenter {

  private final CompassDisplay display;
  private final PhoneGap phoneGap;

  private CompassWatcher watcher;

  public CompassActivity(ClientFactory clientFactory) {
    super(clientFactory);

    this.display = clientFactory.getCompassDisplay();
    this.phoneGap = clientFactory.getPhoneGap();

  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {

    display.setPresenter(this);

    display.getStartStopButton().setText("Start");
    display.getHeadingAccuracy().setText("");
    display.getMagneticHeading().setText("");
    display.getTimeStamp().setText("");
    display.getTrueHeading().setText("");

    panel.setWidget(display);

  }

  @Override
  public void onStop() {
    display.setPresenter(null);

    if (watcher != null) {

      phoneGap.getCompass().clearWatcher(watcher);
      watcher = null;
    }
  }

  @Override
  public void onStartStopButtonPressed() {
    if (watcher != null) {

      display.getStartStopButton().setText("Start");
      display.getHeadingAccuracy().setText("");
      display.getMagneticHeading().setText("");
      display.getTimeStamp().setText("");
      display.getTrueHeading().setText("");

      if (watcher != null) {

        phoneGap.getCompass().clearWatcher(watcher);
        watcher = null;
      }

    } else {
      display.getStartStopButton().setText("Stop");

      watcher = phoneGap.getCompass().watchHeading(new CompassOptions(), new CompassCallback() {

        @Override
        public void onSuccess(CompassHeading heading) {
          display.getHeadingAccuracy().setText("" + heading.getHeadingAccuracy());
          display.getMagneticHeading().setText("" + heading.getMagneticHeading());
          display.getTimeStamp().setText("" + heading.getTimeStamp());
          display.getTrueHeading().setText("" + heading.getTrueHeading());

        }

        @Override
        public void onError(CompassError error) {
          // TODO better error display
          Window.alert("error with compass");

        }
      });
    }

  }

}
