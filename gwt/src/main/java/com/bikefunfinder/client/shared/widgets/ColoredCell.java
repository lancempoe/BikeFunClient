package com.bikefunfinder.client.shared.widgets;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;
/*
 * @author: tneuwerth
 * @created 6/5/13 6:11 PM
 */

public interface ColoredCell<T> extends Cell<T> {
    public String getColorCss(T model);
}
