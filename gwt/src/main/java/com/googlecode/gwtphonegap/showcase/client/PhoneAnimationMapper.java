/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.gwtphonegap.showcase.client;

import com.google.gwt.place.shared.Place;
import com.googlecode.gwtphonegap.showcase.client.about.AboutPlace;
import com.googlecode.gwtphonegap.showcase.client.accelerometer.AccelerometerPlace;
import com.googlecode.gwtphonegap.showcase.client.camera.CameraPlace;
import com.googlecode.gwtphonegap.showcase.client.compass.CompassPlace;
import com.googlecode.gwtphonegap.showcase.client.connection.ConnectionPlace;
import com.googlecode.gwtphonegap.showcase.client.contact.ContactPlace;
import com.googlecode.gwtphonegap.showcase.client.device.DevicePlace;
import com.googlecode.gwtphonegap.showcase.client.event.EventPlace;
import com.googlecode.gwtphonegap.showcase.client.file.FilePlace;
import com.googlecode.gwtphonegap.showcase.client.geolocation.GeolocationPlace;
import com.googlecode.gwtphonegap.showcase.client.inappbrowser.InAppBrowserPlace;
import com.googlecode.gwtphonegap.showcase.client.media.MediaPlace;
import com.googlecode.gwtphonegap.showcase.client.notification.NotificationPlace;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

/**
 * @author Daniel Kurka
 * 
 */
public class PhoneAnimationMapper implements AnimationMapper {

	@Override
	public Animation getAnimation(Place oldPlace, Place newPlace) {

		if (oldPlace == null) {
			return Animation.FADE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof AccelerometerPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof AccelerometerPlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof CameraPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof CameraPlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof CompassPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof CompassPlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof ConnectionPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof ConnectionPlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof ContactPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof ContactPlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof DevicePlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof DevicePlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof EventPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof EventPlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof GeolocationPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof GeolocationPlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof MediaPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof MediaPlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof NotificationPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof NotificationPlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof InAppBrowserPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof InAppBrowserPlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof AboutPlace) {
			return Animation.SLIDE_UP;
		}

		if (oldPlace instanceof AboutPlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof OverviewPlace && newPlace instanceof FilePlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof FilePlace && newPlace instanceof OverviewPlace) {
			return Animation.SLIDE_REVERSE;
		}

		return Animation.SLIDE;

	}
}
