package com.googlecode.gwtphonegap.showcase.client.contact;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.gwtphonegap.showcase.client.model.DemoContact;

public interface ContactDisplay extends IsWidget {
	public void setPresenter(Presenter presenter);

	public interface Presenter {

		public void onBackButtonPressed();

		public void onSearchTermEntered(String term);

	}

	public void display(List<DemoContact> contacts);

}
