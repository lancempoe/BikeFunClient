package com.bikefunfinder.client.client.places.homescreen;
/*
 * @author: tneuwerth
 * @created 4/28/13 3:37 PM
 */

import com.googlecode.mgwt.ui.client.widget.base.PullPanel;

public class HomeRefreshPullHandler implements PullPanel.Pullhandler {
    private boolean failed = false;
    private boolean callRunning = false;
    private final PullPanel.PullWidget pullWidget;
    private HomeScreenDisplay.Presenter presenter;
    private HomeScreenDisplay.Presenter.NotifyTimeAndDayCallback notifyTimeAndDayCallback;
    private long lastMilliesRefreshedPullDown = 0;

    public HomeRefreshPullHandler(PullPanel.PullWidget pullWidget,
                                  HomeScreenDisplay.Presenter presenter) {
        this.pullWidget = pullWidget;
        this.presenter = presenter;

    }

    public void setPresenter(HomeScreenDisplay.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onPullStateChanged(PullPanel.PullWidget pullWidget, PullPanel.PullWidget.PullState state) {
      switch (state) {
            case NORMAL:
                this.pullWidget.asWidget().setVisible(false);
                break;

            case PULLED:
                this.pullWidget.asWidget().setVisible(true);
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

//        this.pullWidget.showLoadingIndicator();
        presenter.refreshTimeAndDayReq(askForRefresh(pullWidget));
    }

    private HomeScreenDisplay.Presenter.NotifyTimeAndDayCallback askForRefresh(final PullPanel.PullWidget pullWidget) {
        return new HomeScreenDisplay.Presenter.NotifyTimeAndDayCallback() {
            @Override
            public void onError() {
                callRunning = false;
//                HomeRefreshPullHandler.this.pullWidget.showError();
                pullWidget.setHTML("Call waiting..");
                lastMilliesRefreshedPullDown = System.currentTimeMillis();
                presenter.refreshTimeAndDayReq(askForRefresh(pullWidget));
            }

            @Override
            public void onResponseReceived() {
                callRunning = false;
                lastMilliesRefreshedPullDown = System.currentTimeMillis();
                HomeRefreshPullHandler.this.pullWidget.asWidget().setVisible(false);
//                HomeRefreshPullHandler.this.pullWidget.showArrow();
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