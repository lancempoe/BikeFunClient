package com.googlecode.gwtphonegap.showcase.client.device;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.IsWidget;

public interface DeviceDisplay extends IsWidget {

	public HasHTML getUUID();

	public HasHTML getPlatform();

	public HasHTML getPhoneGapVersion();

	public HasHTML getName();

	public HasHTML getVersion();

	public void setPresenter(Presenter presenter);

	public interface Presenter {

		public void onBackButtonPressed();

	}

}
