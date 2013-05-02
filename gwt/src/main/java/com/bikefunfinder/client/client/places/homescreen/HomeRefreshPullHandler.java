package com.bikefunfinder.client.client.places.homescreen;
/*
 * @author: tneuwerth
 * @created 4/28/13 3:37 PM
 */

import com.googlecode.mgwt.ui.client.widget.base.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel;

public class HomeRefreshPullHandler implements PullPanel.Pullhandler {
    private boolean failed = false;
    private boolean callRunning = false;
    private final PullArrowWidget pullArrowWidget;
    private HomeScreenDisplay.Presenter presenter;
    private HomeScreenDisplay.Presenter.NotifyTimeAndDayCallback notifyTimeAndDayCallback;
    private long lastMilliesRefreshedPullDown = 0;

    public HomeRefreshPullHandler(PullArrowWidget pullArrowWidget,
                                  HomeScreenDisplay.Presenter presenter) {
        this.pullArrowWidget = pullArrowWidget;
        this.presenter = presenter;

    }

    public void setPresenter(HomeScreenDisplay.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onPullStateChanged(PullPanel.PullWidget pullWidget, PullPanel.PullWidget.PullState state) {
      switch (state) {
            case NORMAL:
                break;

            case PULLED:
                pullWidget.setHTML("release to load");
                lastMilliesRefreshedPullDown = System.currentTimeMillis();
                break;

            default:
                break;
        }
    }

    @Override
    public void onPullAction(final PullPanel.PullWidget pullWidget) {
        if (callRunning)
            return;

        callRunning = true;
        pullWidget.setHTML("loading");

        pullArrowWidget.showLoadingIndicator();
        presenter.refreshTimeAndDayReq(askForRefresh(pullWidget));
    }

    private HomeScreenDisplay.Presenter.NotifyTimeAndDayCallback askForRefresh(final PullPanel.PullWidget pullWidget) {
        return new HomeScreenDisplay.Presenter.NotifyTimeAndDayCallback() {
            @Override
            public void onError() {
                callRunning = false;
                pullArrowWidget.showError();
                pullWidget.setHTML("Call waiting..");
                lastMilliesRefreshedPullDown = System.currentTimeMillis();
                presenter.refreshTimeAndDayReq(askForRefresh(pullWidget));
            }

            @Override
            public void onResponseReceived() {
                callRunning = false;
                lastMilliesRefreshedPullDown = System.currentTimeMillis();
            }
        };
    }

    public boolean isRefreshActionHappening() {
        final long lastPulledPlusABit = lastMilliesRefreshedPullDown + 1000;
        final long currentTimeMillis = System.currentTimeMillis();

        if( lastPulledPlusABit > currentTimeMillis) {
            return true; // if the user just pulled down.. we need to buffer a bit
        }

        return callRunning;
    }
}