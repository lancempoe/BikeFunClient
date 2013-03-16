package com.googlecode.gwtphonegap.showcase.client.media;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.media.Media;
import com.googlecode.gwtphonegap.client.media.MediaCallback;
import com.googlecode.gwtphonegap.client.media.MediaError;
import com.googlecode.gwtphonegap.client.media.MediaPositionCallback;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;
import com.googlecode.mgwt.ui.client.MGWT;

public class MediaActivity extends NavBaseActivity implements MediaDisplay.Presenter {
  private final MediaDisplay display;
  private final PhoneGap phoneGap;

  public MediaActivity(ClientFactory clientFactory) {
    super(clientFactory);

    this.display = clientFactory.getMediaDisplay();
    this.phoneGap = clientFactory.getPhoneGap();

  }

  private Media media;
  private Timer timer;

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {

    display.setPresenter(this);

    display.showPlayButton(true);
    display.showPauseButton(false);

    panel.setWidget(display);

    String url = "";
    if (MGWT.getOsDetection().isIOs()) {
      url = "sound.mp3";
    } else {
      if (MGWT.getOsDetection().isAndroid()) {
        url = "http://audio.ibeat.org/content/p1rj1s/p1rj1s_-_rockGuitar.mp3";
      } else {
        if (MGWT.getOsDetection().isDesktop()) {

        } else {
          // what are your running on
          Window.alert("currently media only on android and ios");
        }
      }
    }

    media = phoneGap.getMedia().create(url, new MediaCallback() {

      @Override
      public void onSuccess() {

      }

      @Override
      public void onStatusChange() {

      }

      @Override
      public void onError(MediaError error) {
        Window.alert("error: " + error.getErrorCode() + " " + error.getErrorMessage());
      }
    });

    timer = new Timer() {

      @Override
      public void run() {
        media.getCurrentPosition(new MediaPositionCallback() {

          @Override
          public void onSuccess(long position) {
            if (position == -1) {
              display.getPosition().setHTML("");
            } else {
              display.getPosition().setHTML(position + " / " + media.getDuration());
            }

          }

          @Override
          public void onFailure(MediaError error) {

          }
        });

      }

    };

    timer.scheduleRepeating(200);

  }

  @Override
  public void onStop() {
    super.onStop();
    media.release();
    timer.cancel();
  }

  @Override
  public void onPlayButtonPressed() {

    display.showPlayButton(false);
    display.showPauseButton(true);

    media.play();
  }

  @Override
  public void onPauseButtonPressed() {

    display.showPlayButton(true);
    display.showPauseButton(false);

    media.pause();

  }
}
