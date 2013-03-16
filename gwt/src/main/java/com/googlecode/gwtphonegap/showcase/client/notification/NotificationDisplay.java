package com.googlecode.gwtphonegap.showcase.client.notification;

import com.google.gwt.user.client.ui.IsWidget;

public interface NotificationDisplay extends IsWidget {
	public void setPresenter(Presenter presenter);

	public interface Presenter {

		public void onBackButtonPressed();

		public void onAlertButtonPressed();

		public void onConfirmButtonPressed();

		public void onBeepButtonPressed();

		public void onVibrateButtonPressed();

	}

}
