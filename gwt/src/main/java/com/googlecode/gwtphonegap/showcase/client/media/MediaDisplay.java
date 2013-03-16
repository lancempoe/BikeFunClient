package com.googlecode.gwtphonegap.showcase.client.media;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.IsWidget;

public interface MediaDisplay extends IsWidget {
	public void setPresenter(Presenter presenter);

	public HasHTML getPosition();

	public interface Presenter {

		public void onBackButtonPressed();

		public void onPlayButtonPressed();

		public void onPauseButtonPressed();

	}

	public void showPlayButton(boolean show);

	public void showPauseButton(boolean show);
}
