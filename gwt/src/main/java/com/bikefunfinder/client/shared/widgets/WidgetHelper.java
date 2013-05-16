package com.bikefunfinder.client.shared.widgets;

import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 5/15/13
 * Time: 12:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class WidgetHelper {

    public static void setSafeText(MValueBoxBase widget, String text) {
        if(text!=null && !text.isEmpty()) {
            widget.setText(text);
        }
    }

    public static void setSafeValue(MValueBoxBase widget, String text) {
        if(text!=null && !text.isEmpty()) {
            widget.setValue(text);
        }
    }

    public static void setSafeValue(MCheckBox widget, boolean value) {
        widget.setValue(value);
    }
}
