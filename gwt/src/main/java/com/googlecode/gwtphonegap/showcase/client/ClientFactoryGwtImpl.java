package com.googlecode.gwtphonegap.showcase.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.showcase.client.about.AboutDisplay;
import com.googlecode.gwtphonegap.showcase.client.about.AboutDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.accelerometer.AccelerometerDisplay;
import com.googlecode.gwtphonegap.showcase.client.accelerometer.AccelerometerDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.camera.CameraDisplay;
import com.googlecode.gwtphonegap.showcase.client.camera.CameraDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.compass.CompassDisplay;
import com.googlecode.gwtphonegap.showcase.client.compass.CompassDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.connection.ConnectionDisplay;
import com.googlecode.gwtphonegap.showcase.client.connection.ConnectionDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.contact.ContactDisplay;
import com.googlecode.gwtphonegap.showcase.client.contact.ContactDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.device.DeviceDisplay;
import com.googlecode.gwtphonegap.showcase.client.device.DeviceDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.event.EventDisplay;
import com.googlecode.gwtphonegap.showcase.client.event.EventDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.file.FileDisplay;
import com.googlecode.gwtphonegap.showcase.client.file.FileDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.geolocation.GeolocationDisplay;
import com.googlecode.gwtphonegap.showcase.client.geolocation.GeolocationDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.inappbrowser.InAppBrowserDisplay;
import com.googlecode.gwtphonegap.showcase.client.inappbrowser.InAppBrowserDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.media.MediaDisplay;
import com.googlecode.gwtphonegap.showcase.client.media.MediaDisplayGwtImpl;
import com.googlecode.gwtphonegap.showcase.client.notification.NotificationDisplay;
import com.googlecode.gwtphonegap.showcase.client.notification.NotificationDisplayGwtImpl;

public class ClientFactoryGwtImpl implements ClientFactory {

	private PhoneGap phoneGap;
	private SimpleEventBus eventBus;
	private PlaceController placeController;
	private OverviewDisplay overviewDisplay;
	private AccelerometerDisplay accelerometerDisplay;
	private CameraDisplay cameraDisplay;
	private CompassDisplayGwtImpl compassDisplay;
	private ConnectionDisplayGwtImpl connectionDisplay;
	private ContactDisplay contactDisplay;
	private DeviceDisplay deviceDisplay;
	private EventDisplay eventDisplay;
	private GeolocationDisplay geolocationDisplay;
	private MediaDisplay mediaDisplay;
	private NotificationDisplay notificationDisplay;
	private InAppBrowserDisplay childBrowserDisplay;
	private AboutDisplay aboutDisplay;
	private FileDisplay fileDisplay;

	public ClientFactoryGwtImpl() {
		eventBus = new SimpleEventBus();

		placeController = new PlaceController(eventBus);
	}

	public void setPhoneGap(PhoneGap phoneGap) {
		this.phoneGap = phoneGap;
	}

	@Override
	public PhoneGap getPhoneGap() {
		return phoneGap;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public OverviewDisplay getOverviewDisplay() {
		if (overviewDisplay == null) {
			overviewDisplay = new OverviewDisplayGwtImpl();
		}
		return overviewDisplay;
	}

	@Override
	public AccelerometerDisplay getAccelerometerDisplay() {
		if (accelerometerDisplay == null) {
			accelerometerDisplay = new AccelerometerDisplayGwtImpl();
		}
		return accelerometerDisplay;
	}

	@Override
	public CameraDisplay getCameraDisplay() {
		if (cameraDisplay == null) {
			cameraDisplay = new CameraDisplayGwtImpl();
		}
		return cameraDisplay;
	}

	@Override
	public CompassDisplay getCompassDisplay() {
		if (compassDisplay == null) {
			compassDisplay = new CompassDisplayGwtImpl();
		}
		return compassDisplay;
	}

	@Override
	public ConnectionDisplay getConnectionDisplay() {
		if (connectionDisplay == null) {
			connectionDisplay = new ConnectionDisplayGwtImpl();
		}
		return connectionDisplay;
	}

	@Override
	public ContactDisplay getContactDisplay() {
		if (contactDisplay == null) {
			contactDisplay = new ContactDisplayGwtImpl();
		}
		return contactDisplay;
	}

	@Override
	public DeviceDisplay getDeviceDisplay() {
		if (deviceDisplay == null) {
			deviceDisplay = new DeviceDisplayGwtImpl();
		}
		return deviceDisplay;
	}

	@Override
	public EventDisplay getEventDisplay() {
		if (eventDisplay == null) {
			eventDisplay = new EventDisplayGwtImpl();
		}
		return eventDisplay;
	}

	@Override
	public GeolocationDisplay getGeolocationDisplay() {
		if (geolocationDisplay == null) {
			geolocationDisplay = new GeolocationDisplayGwtImpl();
		}
		return geolocationDisplay;
	}

	@Override
	public MediaDisplay getMediaDisplay() {
		if (mediaDisplay == null) {
			mediaDisplay = new MediaDisplayGwtImpl();
		}
		return mediaDisplay;
	}

	@Override
	public NotificationDisplay getNotificationDisplay() {
		if (notificationDisplay == null) {
			notificationDisplay = new NotificationDisplayGwtImpl();
		}
		return notificationDisplay;
	}

	@Override
	public InAppBrowserDisplay getChildBrowserDisplay() {
		if (childBrowserDisplay == null) {
			childBrowserDisplay = new InAppBrowserDisplayGwtImpl();
		}
		return childBrowserDisplay;
	}

	@Override
	public AboutDisplay getAboutDisplay() {
		if (aboutDisplay == null) {
			aboutDisplay = new AboutDisplayGwtImpl();
		}
		return aboutDisplay;
	}

	@Override
	public FileDisplay getFileDisplay() {
		if (fileDisplay == null) {
			fileDisplay = new FileDisplayGwtImpl();
		}
		return fileDisplay;
	}
}
