package com.bikefunfinder.client.bootstrap;

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.googlecode.mgwt.ui.client.MGWTSettings;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class MgwtSettingsFactory {

    public static MGWTSettings buildMgwtSettings() {
        MGWTSettings.ViewPort viewPort = buildMgwtViewPort();
        return buildMgwtSettings(viewPort);
    }

    private static MGWTSettings buildMgwtSettings(MGWTSettings.ViewPort viewPort) {
        MGWTSettings settings = new MGWTSettings();
        settings.setViewPort(viewPort);
        settings.setIconUrl(ScreenConstants.TargetIcon.LOGO.getDisplayName());
        settings.setAddGlosToIcon(true);
        settings.setFullscreen(true);
        settings.setPreventScrolling(true);
        return settings;
    }

    private static MGWTSettings.ViewPort buildMgwtViewPort() {
        MGWTSettings.ViewPort viewPort = new MGWTSettings.ViewPort();
        viewPort.setTargetDensity(MGWTSettings.ViewPort.DENSITY.MEDIUM);
        viewPort.setUserScaleAble(false).setMinimumScale(1.0).setMinimumScale(1.0).setMaximumScale(1.0);
        return viewPort;
    }
}
