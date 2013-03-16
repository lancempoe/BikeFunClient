package com.googlecode.gwtphonegap.showcase.client.accelerometer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;

public class AccelerometerDisplayGwtImpl extends Composite implements AccelerometerDisplay {

	private static AccelerometerDisplayGwtImplUiBinder uiBinder = GWT.create(AccelerometerDisplayGwtImplUiBinder.class);
	private Presenter presenter;

	interface AccelerometerDisplayGwtImplUiBinder extends UiBinder<Widget, AccelerometerDisplayGwtImpl> {
	}

	@UiField
	HTML xValue;

	@UiField
	HTML yValue;

	@UiField
	HTML zValue;

	@UiField
	HTML timestamp;

	@UiField
	Button startButton;

	@UiField
	HeaderButton backButton;

	public AccelerometerDisplayGwtImpl() {

		initWidget(uiBinder.createAndBindUi(this));

		if (MGWT.getOsDetection().isTablet()) {
			backButton.setBackButton(false);
			backButton.setText("Modules");
			backButton.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getUtilCss().portraitonly());
		}
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasHTML getXValue() {
		return xValue;
	}

	@Override
	public HasHTML getYValue() {
		return yValue;
	}

	@Override
	public HasHTML getZValue() {
		return zValue;
	}

	@Override
	public HasHTML getTimeStamp() {
		return timestamp;
	}

	@Override
	public HasText getStartStopButton() {
		return startButton;
	}

	@UiHandler("startButton")
	protected void onStartButtonPressed(TapEvent event) {
		if (presenter != null) {
			presenter.onStartStopButtonPressed();
		}
	}

	@UiHandler("backButton")
	protected void oBackButtonPressed(TapEvent event) {
		if (presenter != null) {
			presenter.onBackButtonPressed();
		}
	}

}
