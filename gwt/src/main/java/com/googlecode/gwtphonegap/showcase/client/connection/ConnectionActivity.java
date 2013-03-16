package com.googlecode.gwtphonegap.showcase.client.connection;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;

public class ConnectionActivity extends NavBaseActivity implements ConnectionDisplay.Presenter {

  private ConnectionDisplay display;
  private PhoneGap phoneGap;

  public ConnectionActivity(ClientFactory clientFactory) {
    super(clientFactory);

    this.display = clientFactory.getConnectionDisplay();
    this.phoneGap = clientFactory.getPhoneGap();

  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    display.setPresenter(this);

    panel.setWidget(display);

  }

  @Override
  public void onStartStopButtonPressed() {
    display.getConnection().setHTML(phoneGap.getConnection().getType());

  }
}
