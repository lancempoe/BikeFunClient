package com.googlecode.gwtphonegap.showcase.client.connection;

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
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;

public class ConnectionDisplayGwtImpl extends Composite implements ConnectionDisplay {

	private static ConnectionDisplayGwtImplUiBinder uiBinder = GWT.create(ConnectionDisplayGwtImplUiBinder.class);

	interface ConnectionDisplayGwtImplUiBinder extends UiBinder<Widget, ConnectionDisplayGwtImpl> {
	}

	@UiField
	HTML connection;

	@UiField
	Button startButton;

	@UiField
	HeaderButton backButton;

	private Presenter presenter;

	public ConnectionDisplayGwtImpl() {

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

	@Override
	public HasHTML getConnection() {
		return connection;
	}

}
