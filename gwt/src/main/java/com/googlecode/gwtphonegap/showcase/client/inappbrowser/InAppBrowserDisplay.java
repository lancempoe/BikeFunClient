package com.googlecode.gwtphonegap.showcase.client.inappbrowser;

import com.google.gwt.user.client.ui.IsWidget;

public interface InAppBrowserDisplay extends IsWidget {
	public void setPresenter(Presenter presenter);

	public interface Presenter {

		public void onBackButtonPressed();

		public void onOpenButtonPressed();

	}

}