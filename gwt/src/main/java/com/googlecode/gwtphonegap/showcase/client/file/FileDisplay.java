package com.googlecode.gwtphonegap.showcase.client.file;

import java.util.LinkedList;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.gwtphonegap.showcase.client.model.FileDemo;

public interface FileDisplay extends IsWidget {
	public void render(LinkedList<FileDemo> list);

	public void setPresenter(Presenter presenter);

	public interface Presenter {

		public void onBackButtonPressed();

		public void createFile(String fileName);

		public void onActionButtonPressed();

		public void overWriteFile();

		public void onEntrySelected(int index);

	}

	public HasHTML getStatus();

	public void showSelectMenu();

	public boolean confirm(String string);

	public HasText getFileContent();

	public void setSelected(int index);
}
