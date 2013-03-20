package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.showcase.client.about.AboutDisplay;
import com.googlecode.gwtphonegap.showcase.client.accelerometer.AccelerometerDisplay;
import com.googlecode.gwtphonegap.showcase.client.camera.CameraDisplay;
import com.googlecode.gwtphonegap.showcase.client.compass.CompassDisplay;
import com.googlecode.gwtphonegap.showcase.client.connection.ConnectionDisplay;
import com.googlecode.gwtphonegap.showcase.client.contact.ContactDisplay;
import com.googlecode.gwtphonegap.showcase.client.device.DeviceDisplay;
import com.googlecode.gwtphonegap.showcase.client.event.EventDisplay;
import com.googlecode.gwtphonegap.showcase.client.file.FileDisplay;
import com.googlecode.gwtphonegap.showcase.client.geolocation.GeolocationDisplay;
import com.googlecode.gwtphonegap.showcase.client.gmap.GMapView;
import com.googlecode.gwtphonegap.showcase.client.inappbrowser.InAppBrowserDisplay;
import com.googlecode.gwtphonegap.showcase.client.media.MediaDisplay;
import com.googlecode.gwtphonegap.showcase.client.notification.NotificationDisplay;
import com.googlecode.gwtphonegap.showcase.client.overview.OverviewDisplay;

public interface ClientFactory {
    public PhoneGap getPhoneGap();

    public PlaceController getPlaceController();

    public EventBus getEventBus();

    public HomeScreenDisplay getHomeScreenDisplay();

    /// old views we're not going to use in BFF just for reference
    public OverviewDisplay getOverviewDisplay();

    public AccelerometerDisplay getAccelerometerDisplay();

    public CameraDisplay getCameraDisplay();

    public CompassDisplay getCompassDisplay();

    public ConnectionDisplay getConnectionDisplay();

    public ContactDisplay getContactDisplay();

    public DeviceDisplay getDeviceDisplay();

    public EventDisplay getEventDisplay();

    public GeolocationDisplay getGeolocationDisplay();

    public MediaDisplay getMediaDisplay();

    public NotificationDisplay getNotificationDisplay();

    public InAppBrowserDisplay getChildBrowserDisplay();

    public AboutDisplay getAboutDisplay();

    public FileDisplay getFileDisplay();

    public GMapView getGMapView();
}