package com.bikefunfinder.client.shared.request.converters;
/*
 * @author: tneuwerth
 * @created 6/2/13 1:51 PM
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.impl.ImageResourcePrototype;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Image;
//import com.#########.ui.client.image.MyImages;

public class LoadingScreen {
    private static DecoratedPopupPanel glassPanel = null;

    private static boolean isOpen = false;

    private static ImageResource tabBarAddImage = new ImageResourcePrototype("addIcon", new SafeUri() {
        @Override
        public String asString() {
            return "icons/addRideIcon.png";  //To change body of implemented methods use File | Settings | File Templates.
        }
    }, 0, 0, 45, 36, false , false);

    public static void openLoaderPanel() {
        // Create a glass panel with `autoHide = true`

        if (glassPanel == null) {
            glassPanel = new DecoratedPopupPanel(false);
        }

        if (!isOpen) {
            glassPanel.setWidget(new Image(tabBarAddImage));
            glassPanel.setGlassEnabled(true);
            glassPanel.setPopupPosition(Window.getClientWidth() / 2 - 50,
                    Window.getClientHeight() / 2 - 45);
            glassPanel.show();
            isOpen = true;
        }
    }

    public static void closeLoader() {
        if (glassPanel != null) {
            glassPanel.hide();

            isOpen = false;
        }

    }
}

