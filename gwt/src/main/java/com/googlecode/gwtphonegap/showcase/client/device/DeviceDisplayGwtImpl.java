package com.googlecode.gwtphonegap.showcase.client.device;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;

public class DeviceDisplayGwtImpl extends Composite implements DeviceDisplay {

	private static DeviceDisplayGwtImplUiBinder uiBinder = GWT.create(DeviceDisplayGwtImplUiBinder.class);

	interface DeviceDisplayGwtImplUiBinder extends UiBinder<Widget, DeviceDisplayGwtImpl> {
	}

	@UiField
	HTML name;

	@UiField
	HTML platform;

	@UiField
	HTML version;

	@UiField
	HTML pversion;

	@UiField
	HTML uuid;

	@UiField
	HeaderButton backButton;

	private Presenter presenter;

	public DeviceDisplayGwtImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		if (MGWT.getOsDetection().isTablet()) {
			backButton.setBackButton(false);
			backButton.setText("Modules");
			backButton.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getUtilCss().portraitonly());
		}
	}

	@Override
	public HasHTML getUUID() {
		return uuid;
	}

	@Override
	public HasHTML getPlatform() {
		return platform;
	}

	@Override
	public HasHTML getPhoneGapVersion() {
		return pversion;
	}

	@Override
	public HasHTML getName() {
		return name;
	}

	@Override
	public HasHTML getVersion() {
		return version;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@UiHandler("backButton")
	protected void oBackButtonPressed(TapEvent event) {
		if (presenter != null) {
			presenter.onBackButtonPressed();
		}
	}

}
