package com.googlecode.gwtphonegap.showcase.client.accelerometer;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

public interface AccelerometerDisplay extends IsWidget {

	public void setPresenter(Presenter presenter);

	public HasHTML getXValue();

	public HasHTML getYValue();

	public HasHTML getZValue();

	public HasHTML getTimeStamp();

	public HasText getStartStopButton();

	public interface Presenter {

		public void onBackButtonPressed();

		public void onStartStopButtonPressed();

	}

}
