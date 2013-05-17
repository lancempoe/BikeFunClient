package com.bikefunfinder.client.shared.widgets;
/*
 * @author: tneuwerth
 * @created 5/17/13 9:30 AM
 */

import com.google.gwt.user.client.ui.TextBox;
import com.googlecode.mgwt.ui.client.theme.base.InputCss;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class MobiInputBox extends MTextBox {
    public MobiInputBox(String idForInputElement) {
        setMobiElementID(idForInputElement);
    }

    public MobiInputBox(InputCss css, String idForInputElement) {
        super(css);
        setMobiElementID(idForInputElement);
    }

    public MobiInputBox(InputCss css, TextBox textBox, String idForInputElement) {
        super(css, textBox);
        setMobiElementID(idForInputElement);
    }

    private void setMobiElementID(String idForInputElement) {
        box.getElement().setId(idForInputElement);
    }
}
