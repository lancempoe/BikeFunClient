package com.bikefunfinder.client.shared.widgets;

import java.util.Iterator;
import java.util.List;

import com.bikefunfinder.client.client.places.homescreen.HomeRefreshPullHandler;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList;
import com.googlecode.mgwt.ui.client.widget.HeaderList;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowHeader;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel.PullWidget.PullState;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollMoveEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollRefreshEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollStartEvent;
import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;

public class PullGroupPanel<G, T> extends Composite implements HasRefresh {


    protected FlowPanel main;

    protected ScrollPanel scrollPanel;

    protected PullPanel.PullWidget header;
    protected PullPanel.PullWidget footer;
//    protected FlowPanel container;

    protected PullState headerState = PullState.NORMAL;

    protected PullPanel.Pullhandler headerPullhandler;

    protected PullPanel.Pullhandler footerPullhandler;
    protected PullState footerState = PullState.NORMAL;
    private HeaderListWithPullPanel<G, T> groupingCellList;

    public PullGroupPanel(HeaderListWithPullPanel<G, T> groupingCellList,
                          HomeScreenDisplay.Presenter presenter) {

        this.groupingCellList = groupingCellList;
        main = new FlowPanel();


        LayoutPanel lp = new LayoutPanel();
        lp.add(main);
        lp.add(groupingCellList);
        initWidget(lp);

    }

    public void setupScrollPanel() {

        scrollPanel = groupingCellList.getScrollPanel();
        if(scrollPanel==null) Window.alert("Oh noes it's null");

        if(!scrollPanel.isAttached()) {
            Window.alert("Oh noes it's not attached");
            return;
        }

        scrollPanel.addScrollRefreshHandler(new ScrollRefreshEvent.Handler() {

            @Override
            public void onScrollRefresh(ScrollRefreshEvent event) {
                if(scrollPanel ==null || event == null) return;
                if (header != null) {

                    headerState = PullState.NORMAL;

                }

                if (footer != null) {
                    footerState = PullState.NORMAL;

                }
            }
        });

        scrollPanel.addScrollStartHandler(new ScrollStartEvent.Handler() {
            @Override
            public void onScrollStart(ScrollStartEvent event) {
                if(scrollPanel ==null || event == null) return;
                if (header != null && headerPullhandler != null) {
                    headerState = PullState.NORMAL;
                    headerPullhandler.onPullStateChanged(header, headerState);
                }

                if (footer != null && footerPullhandler != null) {
                    footerState = PullState.NORMAL;
                    footerPullhandler.onPullStateChanged(footer, footerState);
                }
            }
        });

        scrollPanel.addScrollMoveHandler(new ScrollMoveEvent.Handler() {

            @Override
            public void onScrollMove(ScrollMoveEvent event) {

                if(scrollPanel ==null || event == null) return;
                int y = scrollPanel.getY();

                if (header != null) {

                    if (y > header.getStateSwitchPosition() && headerState != PullState.PULLED) {
                        headerState = PullState.PULLED;
                        scrollPanel.setMinScrollY(0);

                        if (headerPullhandler != null)
                            headerPullhandler.onPullStateChanged(header, headerState);

                    } else {
                        if (y <= header.getStateSwitchPosition() && headerState != PullState.NORMAL) {
                            headerState = PullState.NORMAL;
                            scrollPanel.setMinScrollY(-header.getHeight());

                            if (headerPullhandler != null)
                                headerPullhandler.onPullStateChanged(header, headerState);
                        }

                    }
                    header.onScroll(y);

                }

                int y_off = y;

                // footer
                if (footer != null && y < -footer.getHeight()) {

                    if (footerState == PullState.PULLED) {
                        y_off = y_off - footer.getHeight();
                    }

                    if (footerState == PullState.NORMAL) {
                        y_off = y_off + footer.getHeight();
                    }

                    if (y_off < (scrollPanel.getMaxScrollY() - footer.getStateSwitchPosition()) && footerState != PullState.PULLED) {
                        footerState = PullState.PULLED;

                        scrollPanel.setMaxScrollY(scrollPanel.getMaxScrollY() - footer.getHeight());

                        if (footerPullhandler != null) {
                            footerPullhandler.onPullStateChanged(footer, footerState);
                        }
                    } else {
                        if (y_off > (scrollPanel.getMaxScrollY() - footer.getStateSwitchPosition()) && footerState != PullState.NORMAL) {
                            footerState = PullState.NORMAL;
                            scrollPanel.setMaxScrollY(scrollPanel.getMaxScrollY() + footer.getHeight());
                            if (footerPullhandler != null) {
                                footerPullhandler.onPullStateChanged(footer, footerState);
                            }
                        }
                    }

                    footer.onScroll(y_off - scrollPanel.getMaxScrollY());
                }

            }
        });

        scrollPanel.addScrollEndHandler(new ScrollEndEvent.Handler() {

            @Override
            public void onScrollEnd(ScrollEndEvent event) {
                if(scrollPanel ==null || event == null) return;
                if (header != null) {
                    if (headerState == PullState.PULLED) {
                        headerState = PullState.NORMAL;
                        if (headerPullhandler != null)
                            headerPullhandler.onPullAction(header);
                    }
                }

                if (footer != null) {
                    if (footerState == PullState.PULLED) {
                        footerState = PullState.NORMAL;

                        if (footerPullhandler != null)
                            footerPullhandler.onPullAction(footer);
                    }
                }
            }
        });

    }

    public void setHeader(PullPanel.PullWidget header) {

        if (this.header != null) {
            this.main.remove(this.header);
        }
        this.header = header;
        if (this.header != null) {
            main.insert(this.header, 0);
            scrollPanel.setOffSetY(this.header.getHeight());
        } else {
            scrollPanel.setOffSetY(0);
        }

        //scrollPanel.refresh();
    }

    public void setFooter(PullPanel.PullWidget footer) {

        if (this.footer != null) {
            this.main.remove(this.footer);
        }
        this.footer = footer;
        if (this.footer != null) {
            main.insert(this.footer, main.getWidgetCount());
            scrollPanel.setOffSetMaxY(this.footer.getHeight());
        } else {
            scrollPanel.setOffSetMaxY(0);
        }

        scrollPanel.refresh();
    }

    public void refresh() {
        scrollPanel.refresh();

    }

    @Override
    protected void onAttach() {
        super.onAttach();
        setupScrollPanel();
    }

    public void setHeaderPullHandler(PullPanel.Pullhandler headerPullhandler) {
        this.headerPullhandler = headerPullhandler;
    }

    @Deprecated
    public void setHeaderPullhandler(PullPanel.Pullhandler headerPullhandler) {
        setHeaderPullHandler(headerPullhandler);
    }

    public void setFooterPullHandler(PullPanel.Pullhandler headerPullhandler) {
        this.footerPullhandler = headerPullhandler;
    }

    public void render(List<GroupingCellList.CellGroup<G, T>> list) {
        groupingCellList.render(list);

        if(scrollPanel!=null &&scrollPanel.isAttached())
            scrollPanel.refresh();

    }
}
