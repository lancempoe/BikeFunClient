package com.googlecode.gwtphonegap.showcase.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwtphonegap.showcase.client.model.PGModule;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.base.ButtonBase;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;

public class OverviewDisplayGwtImpl extends Composite implements OverviewDisplay {

	private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

	interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, OverviewDisplayGwtImpl> {
	}

	@UiField(provided = true)
	CellList<PGModule> cellList;
	private Presenter presenter;

	@UiField
	ButtonBase aboutButton;

	public OverviewDisplayGwtImpl() {

		BasicCell<PGModule> cell = new BasicCell<PGModule>() {

			@Override
			public String getDisplayString(PGModule model) {
				return model.getName();
			}

			@Override
			public boolean canBeSelected(PGModule model) {
				return true;
			}
		};

		cellList = new CellList<PGModule>(cell);

		cellList.setRound(true);

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void display(List<PGModule> list) {
		cellList.render(list);

	}

	@UiHandler("cellList")
	protected void onCellSelected(CellSelectedEvent event) {
		if (presenter != null) {
			presenter.onCellSelected(event.getIndex());
		}
	}

	@UiHandler("aboutButton")
	protected void onAboutButton(TapEvent event) {
		if (presenter != null) {
			presenter.onAboutButton();
		}
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

}
