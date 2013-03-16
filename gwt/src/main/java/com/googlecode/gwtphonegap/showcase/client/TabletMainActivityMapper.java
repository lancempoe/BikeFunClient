package com.googlecode.gwtphonegap.showcase.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.googlecode.gwtphonegap.showcase.client.about.AboutActivity;
import com.googlecode.gwtphonegap.showcase.client.about.AboutPlace;
import com.googlecode.gwtphonegap.showcase.client.accelerometer.AccelerometerActivity;
import com.googlecode.gwtphonegap.showcase.client.accelerometer.AccelerometerPlace;
import com.googlecode.gwtphonegap.showcase.client.camera.CameraActivity;
import com.googlecode.gwtphonegap.showcase.client.camera.CameraPlace;
import com.googlecode.gwtphonegap.showcase.client.compass.CompassActivity;
import com.googlecode.gwtphonegap.showcase.client.compass.CompassPlace;
import com.googlecode.gwtphonegap.showcase.client.connection.ConnectionActivity;
import com.googlecode.gwtphonegap.showcase.client.connection.ConnectionPlace;
import com.googlecode.gwtphonegap.showcase.client.contact.ContactActivity;
import com.googlecode.gwtphonegap.showcase.client.contact.ContactPlace;
import com.googlecode.gwtphonegap.showcase.client.device.DeviceActivity;
import com.googlecode.gwtphonegap.showcase.client.device.DevicePlace;
import com.googlecode.gwtphonegap.showcase.client.event.EventActivity;
import com.googlecode.gwtphonegap.showcase.client.event.EventPlace;
import com.googlecode.gwtphonegap.showcase.client.file.FileActivity;
import com.googlecode.gwtphonegap.showcase.client.file.FilePlace;
import com.googlecode.gwtphonegap.showcase.client.geolocation.GeolocationActivity;
import com.googlecode.gwtphonegap.showcase.client.geolocation.GeolocationPlace;
import com.googlecode.gwtphonegap.showcase.client.inappbrowser.InAppBrowserActivity;
import com.googlecode.gwtphonegap.showcase.client.inappbrowser.InAppBrowserPlace;
import com.googlecode.gwtphonegap.showcase.client.media.MediaActivity;
import com.googlecode.gwtphonegap.showcase.client.media.MediaPlace;
import com.googlecode.gwtphonegap.showcase.client.notification.NotificationActivity;
import com.googlecode.gwtphonegap.showcase.client.notification.NotificationPlace;

public class TabletMainActivityMapper implements ActivityMapper {

	private final ClientFactory clientFactory;

	private Place lastPlace;
	private Activity lastActivity;

	public TabletMainActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;

	}

	@Override
	public Activity getActivity(Place place) {
		Activity activity = getActivity(lastPlace, place);
		lastPlace = place;
		lastActivity = activity;
		return activity;

	}

	private Activity getActivity(Place lastPlace, Place newPlace) {

		if (newPlace instanceof AccelerometerPlace) {
			if (lastPlace instanceof AccelerometerPlace) {
				return lastActivity;
			}
			return new AccelerometerActivity(clientFactory);
		}

		if (newPlace instanceof CameraPlace) {
			if (lastPlace instanceof CameraPlace) {
				return lastActivity;
			}
			return new CameraActivity(clientFactory);
		}

		if (newPlace instanceof CompassPlace) {
			if (lastPlace instanceof CompassPlace) {
				return lastActivity;
			}
			return new CompassActivity(clientFactory);
		}

		if (newPlace instanceof ConnectionPlace) {
			if (lastPlace instanceof ConnectionPlace) {
				return lastActivity;
			}
			return new ConnectionActivity(clientFactory);
		}

		if (newPlace instanceof ContactPlace) {
			if (lastPlace instanceof ContactPlace) {
				return lastActivity;
			}
			return new ContactActivity(clientFactory);
		}

		if (newPlace instanceof DevicePlace) {
			if (lastPlace instanceof DevicePlace) {
				return lastActivity;
			}
			return new DeviceActivity(clientFactory);
		}

		if (newPlace instanceof EventPlace) {
			if (lastPlace instanceof EventPlace) {
				return lastActivity;
			}
			return new EventActivity(clientFactory);
		}

		if (newPlace instanceof GeolocationPlace) {
			if (lastPlace instanceof GeolocationPlace) {
				return lastActivity;
			}
			return new GeolocationActivity(clientFactory);
		}

		if (newPlace instanceof MediaPlace) {
			if (lastPlace instanceof MediaPlace) {
				return lastActivity;
			}
			return new MediaActivity(clientFactory);
		}

		if (newPlace instanceof NotificationPlace) {
			if (lastPlace instanceof NotificationPlace) {
				return lastActivity;
			}
			return new NotificationActivity(clientFactory);
		}

		if (newPlace instanceof InAppBrowserPlace) {
			if (lastPlace instanceof InAppBrowserPlace) {
				return lastActivity;
			}
			return new InAppBrowserActivity(clientFactory);
		}

		if (newPlace instanceof AboutPlace || newPlace instanceof OverviewPlace) {
			if (lastPlace instanceof AboutPlace || lastPlace instanceof OverviewPlace) {
				return lastActivity;
			}
			return new AboutActivity(clientFactory);
		}

		if (newPlace instanceof FilePlace) {
			if (lastPlace instanceof FilePlace) {
				return lastActivity;
			}
			return new FileActivity(clientFactory);
		}

		return null;
	}

}
