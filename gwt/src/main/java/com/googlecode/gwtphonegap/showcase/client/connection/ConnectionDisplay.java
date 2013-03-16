package com.googlecode.gwtphonegap.showcase.client.connection;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.IsWidget;

public interface ConnectionDisplay extends IsWidget {

	public void setPresenter(Presenter presenter);

	public HasHTML getConnection();

	public interface Presenter {

		public void onBackButtonPressed();

		public void onStartStopButtonPressed();

	}

}
