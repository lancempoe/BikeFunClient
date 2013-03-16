package com.googlecode.gwtphonegap.showcase.client.camera;

import com.google.gwt.user.client.ui.IsWidget;

public interface CameraDisplay extends IsWidget {
	public void setPresenter(Presenter presenter);

	public void displayFoto(String data);

	public interface Presenter {

		void onCameraButtonPressed();

		void onBackButtonPressed();
	}
}
