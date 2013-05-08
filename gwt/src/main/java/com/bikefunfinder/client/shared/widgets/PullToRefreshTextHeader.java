package com.bikefunfinder.client.shared.widgets;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.googlecode.mgwt.dom.client.event.animation.TransitionEndEvent;
import com.googlecode.mgwt.dom.client.event.animation.TransitionEndHandler;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.theme.base.PullToRefreshCss;
import com.googlecode.mgwt.ui.client.widget.ProgressIndicator;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowBase;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel;

/**
 * A header for a pull panel that shows an arrow
 *
 * @author Daniel Kurka
 *
 */
public class PullToRefreshTextHeader extends Composite implements PullPanel.PullWidget {

    private FlowPanel main;

    private HTML textContainer;

    private ProgressIndicator indicator;

    private final PullToRefreshCss css;

    public PullToRefreshTextHeader() {
        this(MGWTStyle.getTheme().getMGWTClientBundle().getPullToRefreshCss());
    }

    public PullToRefreshTextHeader(PullToRefreshCss css) {

        this.css = css;
        css.ensureInjected();
        main = new FlowPanel();
        main.addStyleName(css.pullToRefresh());
        initWidget(main);

        indicator = new ProgressIndicator();
        indicator.addStyleName(css.spinner());
        indicator.getElement().getStyle().setDisplay(Style.Display.NONE);
        main.add(indicator);

        textContainer = new HTML();
        textContainer.addStyleName(css.text());
        main.add(textContainer);

        addDomHandler(new TransitionEndHandler() {

            @Override
            public void onTransitionEnd(TransitionEndEvent event) {
                event.preventDefault();
                event.stopPropagation();

            }
        }, TransitionEndEvent.getType());

    }

    @Override
    public void onScroll(int positionY) {
        //we can use this to change the widget.. arrow uses it to rotate the icon
    }

    @Override
    public int getHeight() {
        return 30;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getStateSwitchPosition() {
        return 50;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setHTML(String html) {
        String htmlToSet = html;
        if (html == null) {
            htmlToSet = "";
        }

        textContainer.setHTML(htmlToSet);
    }
}
