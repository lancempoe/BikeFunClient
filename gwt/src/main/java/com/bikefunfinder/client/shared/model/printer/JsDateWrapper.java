package com.bikefunfinder.client.shared.model.printer;

/**
 * Created with IntelliJ IDEA.
 * User: nathanfrost
 * Date: 4/17/13
 * Time: 7:13 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bikefunfinder.client.shared.model.XDate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;

public class JsDateWrapper implements Comparable<JsDateWrapper> {

    JsDate jsDate;
    XDate xDate;

    public JsDateWrapper(double time) {
        xDate = GWT.create(XDate.class);
        xDate.setNewXDate(time);
        jsDate = JsDate.create(time);
    }

    @Override
    public int compareTo(JsDateWrapper bikeDate)  {
        if(jsDate.getTime() == bikeDate.getJsDate().getTime())
        {
            return 0;
        }
        else if(jsDate.getTime() > bikeDate.getJsDate().getTime())
        {
            return 1;
        }

        return -1;
    }

    public double getTimeZeroIfNull()
    {
        if(null != jsDate ) {
            return jsDate.getTime();
        }
        return 0;
    }


    public final JsDate getJsDate() {
        return JsDate.create(getTimeZeroIfNull());
    }

    public final boolean isSameDay(JsDateWrapper date)
    {
        JsDate date1 = getJsDate();
        JsDate date2 = date.getJsDate();
        return date1.getDate() == date2.getDate() &&
                date1.getMonth() == date2.getMonth() &&
                date1.getFullYear() == date2.getFullYear();
    }

    public String toString(String format) {
        return xDate.toString(format);
    }
}
