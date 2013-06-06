package com.bikefunfinder.client.shared.widgets;

import java.util.*;

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.theme.base.ListCss;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;

/**
 * A grouping cell list renders a number of groups with their children
 *
 * @author Daniel Kurka
 *
 * @param <G> the type of the model for the header
 * @param <T> the type if the model for the content
 */
public class MyGroupingCellList<G, T> extends MyCellList<T> implements HasSelectionHandlers<T> {

    private final ColoredCell<G> header;
    private final Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    private final Map<Integer, T> modelMap = new HashMap<Integer, T>();


    public static class StandardCellGroup<G, T> implements GroupingCellList.CellGroup<G, T> {

        protected final String key;
        protected final G group;
        protected final List<T> member;


        public StandardCellGroup(String key, G group, List<T> member) {
            this.key = key;
            this.group = group;
            this.member = member;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public G getGroup() {
            return group;
        }

        @Override
        public List<T> getMember() {
            return member;
        }

    }

    interface HeaderTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<li class=\"{1}\" id=\"bullshit_{2}\" >{0}</li>")
        SafeHtml li(SafeHtml cellContents, String classes, int idNumber);

    }

    protected static final HeaderTemplate HEADER_LI_TEMPLATE = GWT.create(HeaderTemplate.class);

    /**
     * Construct a cell list with a given cell for content and for the header
     *
     * @param cell the cell for content
     * @param header the cell for the headers
     */
    public MyGroupingCellList(ColoredCell<T> cell, ColoredCell<G> header) {
        this(cell, header, MGWTStyle.getTheme().getMGWTClientBundle().getListCss());
    }

    /**
     * Construct a cell list with a given cell for content, for the header and a given css
     *
     * @param cell the cell for content
     * @param header the cell for the headers
     * @param css the css to use
     */
    public MyGroupingCellList(ColoredCell<T> cell, ColoredCell<G> header, ListCss css) {
        super(cell, css);
        this.header = header;

        setGroup(false);
    }

    /**
     * render a given set of models
     *
     * @param groups the model to render
     */
    public void renderGroup(List<GroupingCellList.CellGroup<G, T>> groups) {
        SafeHtmlBuilder sb = new SafeHtmlBuilder();

        int count = 0;

        map.clear();
        modelMap.clear();


        int groupCounter = 0;
        int renderedGroups = 0;
        int idNumber = 0;

        for (GroupingCellList.CellGroup<G, T> cellGroup : groups) {

            if (cellGroup.getMember().isEmpty()) {
                groupCounter++;
                continue;
            }

            // render header of group
            SafeHtmlBuilder headerBuilder = new SafeHtmlBuilder();
            header.render(headerBuilder, cellGroup.getGroup());

            final SafeHtml headerLi = HEADER_LI_TEMPLATE.li(headerBuilder.toSafeHtml(), css.listHeadElement(), idNumber++);
            sb.append(headerLi);

            // render members of group
            List<T> models = cellGroup.getMember();
            for (T model : models) {
                SafeHtmlBuilder cellBuilder = new SafeHtmlBuilder();

                String clazz = "";
                if (cell.canBeSelected(model)) {
                    clazz = css.canbeSelected() + " ";
                }

                if (isGroup()) {
                    clazz = css.group() + " ";
                }

                if(clazz.isEmpty()) {
                    clazz = cell.getColorCss();
                } else {
                    clazz += " " + cell.getColorCss();
                }

                cell.render(cellBuilder, model);

                sb.append(LI_TEMPLATE.li(count, clazz, cellBuilder.toSafeHtml()));

                modelMap.put(count, model);

                count++;
            }

            map.put(renderedGroups, groupCounter);

            renderedGroups++;
            groupCounter++;

        }

        final String html = sb.toSafeHtml().asString();

        getElement().setInnerHTML(html);

        if (count > 0) {
            String innerHTML = getElement().getInnerHTML();
            if ("".equals(innerHTML.trim())) {
                fixBug(html);
            }
        }
    }

    /**
     * get the css selector for header elements
     *
     * @return the css selector for header elements
     */
    public String getHeaderSelector() {
        return "li." + css.listHeadElement();
    }


    public ListCss getListCss() {
        return css;

    }

    /**
     * get the mapping of index to content
     *
     * @return the mapping of index to content
     */
    public Map<Integer, Integer> getMapping() {
        return map;
    }

    /**
     * render a header and return the value as html
     *
     * @param group the header to render
     * @return the string value
     */
    public String renderGroupHeader(G group) {
        SafeHtmlBuilder headerBuilder = new SafeHtmlBuilder();
        header.render(headerBuilder, group);
        return headerBuilder.toSafeHtml().asString();
    }

    @Override
    protected void fireSelectionAtIndex(int index, Element targetElement) {
        T t = modelMap.get(index);
        if (t != null) {
            SelectionEvent.fire(this, t);
        }
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<T> handler) {
        return addHandler(handler, SelectionEvent.getType());
    }
}
