package com.googlecode.gwtphonegap.showcase.client.file;

import java.util.ArrayList;
import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwtphonegap.showcase.client.BasicCell;
import com.googlecode.gwtphonegap.showcase.client.model.FileDemo;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.ButtonType;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.OptionCallback;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.OptionsDialogEntry;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.MTextArea;
import com.googlecode.mgwt.ui.client.widget.base.ButtonBase;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;

public class FileDisplayGwtImpl extends Composite implements FileDisplay {

	private static FileDisplayGwtImplUiBinder uiBinder = GWT.create(FileDisplayGwtImplUiBinder.class);

	interface FileDisplayGwtImplUiBinder extends UiBinder<Widget, FileDisplayGwtImpl> {
	}

	@UiField(provided = true)
	CellList<FileDemo> cellList;
	private Presenter presenter;

	@UiField
	HeaderButton backButton;

	@UiField
	ButtonBase plusButton;

	@UiField
	ButtonBase actionButton;

	@UiField
	HTML status;

	@UiField
	MTextArea content;

	public FileDisplayGwtImpl() {

		BasicCell<FileDemo> cell = new BasicCell<FileDemo>() {

			@Override
			public String getDisplayString(FileDemo model) {
				return model.getName();
			}

			@Override
			public boolean canBeSelected(FileDemo model) {
				return true;
			}
		};

		cellList = new CellList<FileDemo>(cell);

		initWidget(uiBinder.createAndBindUi(this));

		if (MGWT.getOsDetection().isTablet()) {
			backButton.setBackButton(false);
			backButton.setText("Modules");
			backButton.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getUtilCss().portraitonly());
		}

	}

	@Override
	public void render(LinkedList<FileDemo> list) {
		cellList.render(list);

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public HasHTML getStatus() {
		return status;
	}

	@UiHandler("backButton")
	protected void oBackButtonPressed(TapEvent event) {
		if (presenter != null) {
			presenter.onBackButtonPressed();
		}
	}

	@UiHandler("plusButton")
	protected void onPlusButtonTapped(TapEvent event) {
		if (presenter != null) {
			String fileName = Window.prompt("filename", "");

			presenter.createFile(fileName);
		}
	}

	@UiHandler("actionButton")
	protected void onActionButtonPressed(TapEvent event) {
		if (presenter != null) {
			presenter.onActionButtonPressed();
		}
	}

	@UiHandler("cellList")
	protected void onCellSelected(CellSelectedEvent event) {
		if (presenter != null) {
			presenter.onEntrySelected(event.getIndex());
		}
	}

	@Override
	public void showSelectMenu() {
		ArrayList<OptionsDialogEntry> list = new ArrayList<OptionsDialogEntry>();

		list.add(new OptionsDialogEntry("Overwrite", ButtonType.IMPORTANT));
		list.add(new OptionsDialogEntry("Cancel", ButtonType.NORMAL));

		Dialogs.options(list, new OptionCallback() {

			@Override
			public void onOptionSelected(int index) {
				if (presenter != null) {
					if (index == 0) {
						presenter.overWriteFile();
					}
				}

			}
		});

	}

	@Override
	public boolean confirm(String string) {
		return Window.confirm(string);
	}

	@Override
	public HasText getFileContent() {
		return content;
	}

	@Override
	public void setSelected(int index) {
		cellList.setSelectedIndex(index, true);

	}
}
