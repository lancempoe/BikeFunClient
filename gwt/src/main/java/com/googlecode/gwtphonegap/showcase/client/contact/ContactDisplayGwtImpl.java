package com.googlecode.gwtphonegap.showcase.client.contact;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwtphonegap.showcase.client.BasicCell;
import com.googlecode.gwtphonegap.showcase.client.model.DemoContact;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.MSearchBox;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;

public class ContactDisplayGwtImpl extends Composite implements ContactDisplay {

	private static ContactDisplayGwtImplUiBinder uiBinder = GWT.create(ContactDisplayGwtImplUiBinder.class);

	interface ContactDisplayGwtImplUiBinder extends UiBinder<Widget, ContactDisplayGwtImpl> {
	}

	@UiField
	MSearchBox searchBox;
	private Presenter presenter;

	@UiField
	HeaderButton backButton;

	@UiField
	ScrollPanel scrollPanel;

	@UiField(provided = true)
	CellList<DemoContact> cellList;

	public ContactDisplayGwtImpl() {

		BasicCell<DemoContact> cell = new BasicCell<DemoContact>() {

			@Override
			public String getDisplayString(DemoContact model) {
				return model.getName();
			}

			@Override
			public boolean canBeSelected(DemoContact model) {
				return false;
			}
		};

		cellList = new CellList<DemoContact>(cell);

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
	public void display(List<DemoContact> contacts) {
		cellList.render(contacts);
		scrollPanel.refresh();

	}

	@UiHandler("searchBox")
	protected void onSearchBoxTyped(KeyUpEvent event) {

		if (presenter != null) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					presenter.onSearchTermEntered(searchBox.getValue());

				}
			});

		}
	}

	@UiHandler("backButton")
	protected void oBackButtonPressed(TapEvent event) {
		if (presenter != null) {
			presenter.onBackButtonPressed();
		}
	}

}
