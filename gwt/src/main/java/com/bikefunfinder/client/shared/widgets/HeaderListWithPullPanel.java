package com.bikefunfinder.client.shared.widgets;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.collection.shared.LightArrayInt;
import com.googlecode.mgwt.dom.client.event.touch.*;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.theme.base.GroupingList;
import com.googlecode.mgwt.ui.client.theme.base.ListCss;
import com.googlecode.mgwt.ui.client.util.CssUtil;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.CellGroup;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollAnimationMoveEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollMoveEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollRefreshEvent;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This widget uses a {@link GroupingCellList} to render children within groups.
 * On top of that it provides a fast selection through a bar on the right by
 * displaying symbols for group names.
 *
 * It also animates a header group so that the user can always see which group
 * he is currently scrolling in.
 *
 * @author Daniel Kurka
 *
 * @param <G> type of the group
 * @param <T> type of the children
 */
public class HeaderListWithPullPanel<G, T> extends Composite {
    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    private static class SelectionBar<G, T> extends TouchWidget implements TouchHandler, HasSelectionHandlers<Integer> {

        private int selectedIndex;
        private int renderedEntries;
        private final GroupingList css;

        public SelectionBar(GroupingList css) {
            this.css = css;
            setElement(Document.get().createULElement());
            //getElement().addClassName(Resources.INSTANCE.css().colorYellow());


            addStyleName(css.selectionBar());

            selectedIndex = 0;

            mapping = new HashMap<Integer, Integer>();

            addTouchHandler(this);
        }

        @Override
        public void onTouchStart(TouchStartEvent event) {
            calculateSelection(event.getTouches().get(0).getPageY());
            addStyleName(css.selectionBarActive());

        }

        @Override
        public void onTouchMove(TouchMoveEvent event) {
            calculateSelection(event.getTouches().get(0).getPageY());
            if (MGWT.getOsDetection().isAndroid()) {
                event.preventDefault();
            }
        }

        @Override
        public void onTouchEnd(TouchEndEvent event) {
            removeStyleName(css.selectionBarActive());

        }

        @Override
        public void onTouchCanceled(TouchCancelEvent event) {
            removeStyleName(css.selectionBarActive());

        }

        protected void calculateSelection(int y) {

            int absoluteTop = getElement().getAbsoluteTop();
            int absoluteBottom = getElement().getAbsoluteBottom();

            int normalized_y = y - absoluteTop;
            int height = absoluteBottom - absoluteTop;

            int index = (normalized_y * renderedEntries) / height;

            if (index != selectedIndex) {
                SelectionEvent.fire(this, mapping.get(index));
            }

            selectedIndex = index;

        }

        @Override
        public HandlerRegistration addSelectionHandler(SelectionHandler<Integer> handler) {
            return addHandler(handler, SelectionEvent.getType());
        }

        public void render(List<CellGroup<G, T>> list) {

            mapping.clear();
            StringBuffer buffer = new StringBuffer();

            renderedEntries = 0;
            int target = 0;
            for (CellGroup<?, ?> cellGroup : list) {
                buffer.append("<li>" + cellGroup.getKey() + "</li>");

                mapping.put(renderedEntries, target);

                if (!cellGroup.getMember().isEmpty()) {
                    target++;
                }

                renderedEntries++;

            }

            getElement().setInnerHTML(buffer.toString());
        }

        private Map<Integer, Integer> mapping;

    }

    private static class MovingHeader extends Widget {

        public MovingHeader(ListCss listCss, GroupingList css) {
            setElement(DOM.createDiv());
            addStyleName(listCss.listHeadElement());
            addStyleName(css.movingHeader());
        }

        public void setHTML(String string) {
            getElement().setInnerHTML(string);

        }
    }

    private LayoutPanel main;
    private ScrollPanel scrollPanel;
    private MovingHeader movingHeader;
    private LightArrayInt pagesY;

    private int currentPage;
    private final MyGroupingCellList<G, T> cellList;

    private boolean needReset = false;

    private int lastPage = -1;
    private SelectionBar<G, T> selectionBar;
    private List<CellGroup<G, T>> list;

    private boolean headerVisible = true;

    /**
     * Construct a HeaderList
     *
     * @param cellList the cell list that renders its children inside this
     *            widget.
     */
    public HeaderListWithPullPanel(MyGroupingCellList<G, T> cellList) {
        this(cellList, MGWTStyle.getTheme().getMGWTClientBundle().getGroupingList());
    }

    /**
     * Construct a cell list with a given cell list and css
     *
     * @param cellList the cell list that renders its children inside this
     *            widget
     * @param css the css to use
     */
    public HeaderListWithPullPanel(MyGroupingCellList<G, T> cellList, GroupingList css) {
//        super(cellList, css);
        this.cellList = cellList;
//        this.cellList.addStyleName(Resources.INSTANCE.css().colorGreen());

        css.ensureInjected();
        main = new LayoutPanel();
        initWidget(main);

        main.addStyleName(css.groupingHeaderList());

        currentPage = 0;

        selectionBar = new SelectionBar<G, T>(css);
        main.add(selectionBar);

        scrollPanel = new ScrollPanel();
        scrollPanel.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getLayoutCss().fillPanelExpandChild());

        scrollPanel.setWidget(cellList);
        scrollPanel.setSnapSelector(cellList.getHeaderSelector());

        scrollPanel.setShowScrollBarX(false);

        scrollPanel.setUsePos(MGWT.getOsDetection().isAndroid());
//        scrollPanel.addStyleName(Resources.INSTANCE.css().colorBlue());

        main.add(scrollPanel);
        movingHeader = new MovingHeader(cellList.getListCss(), css);
        main.add(movingHeader);

        scrollPanel.addScrollRefreshHandler(new ScrollRefreshEvent.Handler() {

            @Override
            public void onScrollRefresh(ScrollRefreshEvent event) {
                pagesY = scrollPanel.getPagesY();
            }
        });

        selectionBar.addSelectionHandler(new SelectionHandler<Integer>() {

            @Override
            public void onSelection(SelectionEvent<Integer> event) {

                scrollPanel.scrollToPage(0, event.getSelectedItem(), 0);
                currentPage = event.getSelectedItem();
                updateCurrentPage(scrollPanel.getY());
                updateHeaderPositionAndTitle(scrollPanel.getY());

            }
        });

        scrollPanel.addScrollMoveHandler(new ScrollMoveEvent.Handler() {

            @Override
            public void onScrollMove(ScrollMoveEvent event) {
                updateCurrentPage(scrollPanel.getY());
                updateHeaderPositionAndTitle(scrollPanel.getY());

            }
        });

        scrollPanel.addScrollAnimationMoveHandler(new ScrollAnimationMoveEvent.Handler() {

            @Override
            public void onScrollAnimationMove(ScrollAnimationMoveEvent event) {
                updateCurrentPage(scrollPanel.getY());
                updateHeaderPositionAndTitle(scrollPanel.getY());

            }
        });
    }

    /**
     * Render the list of models
     *
     * @param list the models to render
     */

    public void render(List<CellGroup<G, T>> list) {
        this.list = list;
        selectionBar.render(list);
        cellList.renderGroup(list);

        scrollPanel.refresh();

        if(list.size()<=0) {
	        movingHeader.setHTML("");
        }

    }

    private void updateCurrentPage(int y) {
        if(pagesY==null) return;
        int i;
        for (i = 0; i < pagesY.length(); i++) {
            int page_start = pagesY.get(i);
            if (page_start < y) {
                break;
            }
        }

        currentPage = i - 1;

        if (currentPage < 0)
            currentPage = 0;
        if (currentPage > pagesY.length() - 1) {
            currentPage = pagesY.length() - 1;
        }
    }

    /**
     * Print the header at the top every time the user moves the screen.
     * @param y
     */
    private void updateHeaderPositionAndTitle(int y) {
        if(pagesY==null) return;
        if(cellList==null) return;

        if(movingHeader==null) return;
        int headerHeight = movingHeader.getOffsetHeight();

        if (lastPage != currentPage) {
            lastPage = currentPage;
            final Map<Integer, Integer> mapping = cellList.getMapping();

            if(mapping!=null) {
                int modelIndex;

                try {
                    modelIndex = mapping.get(currentPage);
                } catch (Exception e) {
                    return;
                }

                final CellGroup<G, T> gtCellGroup = list.get(modelIndex);
                if(gtCellGroup!=null) {
                    final G group = gtCellGroup.getGroup();
                    if(group!=null) {
                        movingHeader.setHTML(cellList.renderGroupHeader(group));
                    }
                }
            }
        }

        if (y > 0) {
            if (headerVisible) {
                headerVisible = false;
                movingHeader.setVisible(false);
            }

            return;
        } else {
            if (!headerVisible) {
                headerVisible = true;
                movingHeader.setVisible(true);
            }

        }

        if (currentPage < pagesY.length()) {
            int nextHeader = pagesY.get(currentPage + 1);

            if (nextHeader + headerHeight - y > 0) {

                int move = -(nextHeader + headerHeight - y);
                final Element element = movingHeader.getElement();
                if(element!=null) {
                    CssUtil.translate(element, 0, move);
                }

                needReset = true;
            } else {
                if (needReset) {
                    needReset = false;
                    final Element element = movingHeader.getElement();
                    if(element!=null) {
                        CssUtil.translate(element, 0, 0);
                    }
                }

            }
        }
    }

    public ScrollPanel getScrollPanel() {
        return scrollPanel;
    }
}