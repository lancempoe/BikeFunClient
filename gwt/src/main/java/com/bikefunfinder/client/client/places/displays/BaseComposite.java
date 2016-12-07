package com.bikefunfinder.client.client.places.displays;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;

public class BaseComposite extends Composite{

    private ScrollPanel scrollableWidget;


    public BaseComposite() {
        sinkEvents(Event.ONMOUSEWHEEL);
    }

    protected void setScrollableWidget(ScrollPanel scrollableWidget) {
        this.scrollableWidget = scrollableWidget;
    }

    @Override
    public void onBrowserEvent(Event event) {
        //custom events
        switch (event.getTypeInt()) {
            case Event.ONMOUSEWHEEL:
                if (scrollableWidget != null) {
                    scrollableWidget.scrollTo(scrollableWidget.getX(), scrollableWidget.getY() - event.getMouseWheelVelocityY());
                }
            default:
                super.onBrowserEvent(event);

        }
    }
}
