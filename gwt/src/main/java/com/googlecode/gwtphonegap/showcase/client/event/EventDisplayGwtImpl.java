package com.googlecode.gwtphonegap.showcase.client.event;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwtphonegap.showcase.client.BasicCell;
import com.googlecode.gwtphonegap.showcase.client.model.EventDemo;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;

public class EventDisplayGwtImpl extends Composite implements EventDisplay {

	private static EventDisplayGwtImplUiBinder uiBinder = GWT.create(EventDisplayGwtImplUiBinder.class);

	interface EventDisplayGwtImplUiBinder extends UiBinder<Widget, EventDisplayGwtImpl> {
	}

	@UiField
	HeaderButton backButton;

	@UiField
	ScrollPanel scrollPanel;

	@UiField(provided = true)
	CellList<EventDemo> cellList;

	private Presenter presenter;

	public EventDisplayGwtImpl() {

		BasicCell<EventDemo> cell = new BasicCell<EventDemo>() {

			@Override
			public String getDisplayString(EventDemo model) {
				return model.getText();
			}

		};

		cellList = new CellList<EventDemo>(cell);

		initWidget(uiBinder.createAndBindUi(this));

		if (MGWT.getOsDetection().isTablet()) {
			backButton.setBackButton(false);
			backButton.setText("Modules");
			backButton.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getUtilCss().portraitonly());
		}
	}

	@UiHandler("backButton")
	protected void oBackButtonPressed(TapEvent event) {
		if (presenter != null) {
			presenter.onBackButtonPressed();
		}
	}

	@Override
	public void render(LinkedList<EventDemo> list) {
		cellList.render(list);
		scrollPanel.refresh();

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

}
