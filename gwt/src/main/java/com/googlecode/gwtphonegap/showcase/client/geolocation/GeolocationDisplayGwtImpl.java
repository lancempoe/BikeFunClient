package com.googlecode.gwtphonegap.showcase.client.geolocation;

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

public class GeolocationDisplayGwtImpl extends Composite implements GeolocationDisplay {

	private static GeolocationDisplayGwtImplUiBinder uiBinder = GWT.create(GeolocationDisplayGwtImplUiBinder.class);

	interface GeolocationDisplayGwtImplUiBinder extends UiBinder<Widget, GeolocationDisplayGwtImpl> {
	}

	@UiField
	HTML latitude;

	@UiField
	HTML longitude;

	@UiField
	HTML altitude;

	@UiField
	HTML accuracy;

	@UiField
	HTML aa;

	@UiField
	HTML heading;

	@UiField
	HTML speed;

	@UiField
	HTML timestamp;

	@UiField
	Button startButton;

	@UiField
	HeaderButton backButton;

	private Presenter presenter;

	public GeolocationDisplayGwtImpl() {
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
	public HasHTML getLatidute() {
		return latitude;
	}

	@Override
	public HasHTML getLongitude() {
		return longitude;
	}

	@Override
	public HasHTML getAltitude() {
		return altitude;
	}

	@Override
	public HasHTML getAccuracy() {
		return accuracy;
	}

	@Override
	public HasHTML getAltitudeAccuracy() {
		return aa;
	}

	@Override
	public HasHTML getHeading() {
		return heading;
	}

	@Override
	public HasHTML getTimeStamp() {
		return timestamp;
	}

	@Override
	public HasHTML getSpeed() {
		return speed;
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
