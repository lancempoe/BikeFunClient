package com.googlecode.gwtphonegap.showcase.client.inappbrowser;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.inappbrowser.InAppBrowser;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;

public class InAppBrowserActivity extends NavBaseActivity implements InAppBrowserDisplay.Presenter {

  private InAppBrowserDisplay display;
  private PhoneGap phoneGap;
  private InAppBrowser inAppBrowser;

  public InAppBrowserActivity(ClientFactory clientFactory) {
    super(clientFactory);

    this.display = clientFactory.getChildBrowserDisplay();
    this.phoneGap = clientFactory.getPhoneGap();

    inAppBrowser = this.phoneGap.getInAppBrowser();

  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    display.setPresenter(this);
    panel.setWidget(display);

  }

  @Override
  public void onOpenButtonPressed() {
    inAppBrowser.open("http://www.google.de", "", "");

  }
}
