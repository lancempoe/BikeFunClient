package com.googlecode.gwtphonegap.showcase.client.event;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.event.*;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;
import com.googlecode.gwtphonegap.showcase.client.model.EventDemo;

import java.util.Date;
import java.util.LinkedList;

public class EventActivity extends NavBaseActivity implements EventDisplay.Presenter {

    private final PhoneGap phoneGap;
    private final EventDisplay display;

    public EventActivity(ClientFactory clientFactory) {
        super(clientFactory);

        this.display = clientFactory.getEventDisplay();
        this.phoneGap = clientFactory.getPhoneGap();

        phoneGap.getEvent().getBackButton().addBackButtonPressedHandler(new BackButtonPressedHandler() {

            @Override
            public void onBackButtonPressed(BackButtonPressedEvent event) {
                String res = "Back Button Pressed: " + new Date();
                addText(res);
            }
        });

        phoneGap.getEvent().getOffLineHandler().addOfflineHandler(new OffLineHandler() {

            @Override
            public void onOffLine(OffLineEvent event) {
                String res = "Offline Event: " + new Date();
                addText(res);

            }
        });

        phoneGap.getEvent().getOnlineHandler().addOnlineHandler(new OnlineHandler() {

            @Override
            public void onOnlineEvent(OnlineEvent event) {
                String res = "Online Event: " + new Date();
                addText(res);

            }
        });

        phoneGap.getEvent().getPauseHandler().addPauseHandler(new PauseHandler() {

            @Override
            public void onPause(PauseEvent event) {
                String res = "Pause Event: " + new Date();
                addText(res);

            }
        });

        phoneGap.getEvent().getResumeHandler().addResumeHandler(new ResumeHandler() {

            @Override
            public void onResumeEvent(ResumeEvent event) {
                String res = "Resume Event: " + new Date();
                addText(res);

            }
        });

        phoneGap.getEvent().getSearchButton().addSearchButtonHandler(new SearchButtonPressedHandler() {

            @Override
            public void onSearchButtonPressed(SearchButtonPressedEvent event) {
                String res = "Search Button Pressed Event: " + new Date();
                addText(res);

            }
        });

        phoneGap.getEvent().getMenuButton().addMenuButtonPressedHandler(new MenuButtonPressedHandler() {

            @Override
            public void onMenuButtonPressed(MenuButtonPressedEvent event) {
                String res = "Menu Button Pressed Event: " + new Date();
                addText(res);

            }
        });

    }

    private LinkedList<EventDemo> list = new LinkedList<EventDemo>();

    private void addText(String text) {
        list.add(new EventDemo(text));
        display.render(list);

    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        display.setPresenter(this);

        panel.setWidget(display);
    }

}
