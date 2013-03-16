package com.googlecode.gwtphonegap.showcase.client.about;

import com.google.gwt.user.client.ui.IsWidget;

public interface AboutDisplay extends IsWidget {

	public void setPresenter(Presenter presenter);

	public interface Presenter {

		public void onBackButtonPressed();

	}

}
