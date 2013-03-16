package com.googlecode.gwtphonegap.showcase.client.compass;

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

public class CompassDisplayGwtImpl extends Composite implements CompassDisplay {

	private static CompassDisplayGwtImplUiBinder uiBinder = GWT.create(CompassDisplayGwtImplUiBinder.class);

	interface CompassDisplayGwtImplUiBinder extends UiBinder<Widget, CompassDisplayGwtImpl> {
	}

	@UiField
	HTML mh;

	@UiField
	HTML th;

	@UiField
	HTML ha;

	@UiField
	HTML timestamp;

	@UiField
	Button startButton;

	@UiField
	HeaderButton backButton;

	private Presenter presenter;

	public CompassDisplayGwtImpl() {

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
	public HasHTML getMagneticHeading() {
		return mh;
	}

	@Override
	public HasHTML getTrueHeading() {
		return th;
	}

	@Override
	public HasHTML getHeadingAccuracy() {
		return ha;
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
