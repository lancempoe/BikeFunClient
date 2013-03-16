package com.googlecode.gwtphonegap.showcase.client.geolocation;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

public interface GeolocationDisplay extends IsWidget {

	public void setPresenter(Presenter presenter);

	public HasHTML getLatidute();

	public HasHTML getLongitude();

	public HasHTML getAltitude();

	public HasHTML getAccuracy();

	public HasHTML getAltitudeAccuracy();

	public HasHTML getHeading();

	public HasHTML getTimeStamp();

	public HasHTML getSpeed();

	public HasText getStartStopButton();

	public interface Presenter {

		public void onBackButtonPressed();

		public void onStartStopButtonPressed();

	}

}
