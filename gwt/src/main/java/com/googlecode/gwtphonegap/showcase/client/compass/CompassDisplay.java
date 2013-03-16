package com.googlecode.gwtphonegap.showcase.client.compass;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

public interface CompassDisplay extends IsWidget {

	public void setPresenter(Presenter presenter);

	public HasHTML getMagneticHeading();

	public HasHTML getTrueHeading();

	public HasHTML getHeadingAccuracy();

	public HasHTML getTimeStamp();

	public HasText getStartStopButton();

	public interface Presenter {

		public void onBackButtonPressed();

		public void onStartStopButtonPressed();

	}

}
