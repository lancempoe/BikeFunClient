package com.bikefunfinder.client.shared.widgets;
/*
 * @author: tneuwerth
 * @created 6/2/13 1:51 PM
 */

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

    private static ImageResource tabBarAddImage = new ImageResourcePrototype("ajax-loader", new SafeUri() {
        @Override
        public String asString() {
            return "icons/ajax-loader.gif";
        }
    }, 0, 0, 32, 32, false , false);

    public static void openLoaderPanel() {
        // Create a glass panel with `autoHide = true`

        if (glassPanel == null) {
            glassPanel = new DecoratedPopupPanel(false);
        }

        if (!isOpen) {
            glassPanel.setWidget(new Image(tabBarAddImage));
            glassPanel.setGlassEnabled(true);
            glassPanel.setPopupPosition((Window.getClientWidth() / 2)-16 ,
                    (Window.getClientHeight() / 2)-16 );
            glassPanel.show();
            isOpen = true;
            glassPanel.addStyleName(Resources.INSTANCE.loadingScreenCss().greyBackgroundCss());
        }
    }

    public static void closeLoader() {
        if (glassPanel != null) {
            glassPanel.hide();

            isOpen = false;
            glassPanel.removeStyleName(Resources.INSTANCE.loadingScreenCss().greyBackgroundCss());
        }

    }
}

