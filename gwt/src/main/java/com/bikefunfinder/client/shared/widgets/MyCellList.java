package com.bikefunfinder.client.shared.widgets;
/*
 * @author: tneuwerth
 * @created 6/3/13 7:45 PM
 */


import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.theme.base.ListCss;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;

import java.util.List;

public class MyCellList<T> extends CellList<T> {

    /**
     * Construct a CellList
     *
     * @param cell the cell to use
     */
    public MyCellList(Cell<T> cell) {
        this(cell, MGWTStyle.getTheme().getMGWTClientBundle().getListCss());
    }

    /**
     * Construct a celllist with a given cell and css
     *
     * @param cell the cell to use
     * @param css the css to use
     */
    public MyCellList(Cell<T> cell, ListCss css) {
        super(cell, css);
    }

    /**
     * Render a List of models in this cell list
     *
     * @param models the list of models to render
     */
    @Override
    public void render(List<T> models) {
        boolean group = isGroup();

        SafeHtmlBuilder sb = new SafeHtmlBuilder();

        for (int i = 0; i < models.size(); i++) {

            T model = models.get(i);

            SafeHtmlBuilder cellBuilder = new SafeHtmlBuilder();

            String clazz = "";
            if (cell.canBeSelected(model)) {
                clazz = css.canbeSelected() + " ";
            }

            if (group) {
                clazz += css.group() + " ";
            }

            if (i == 0) {
                clazz += css.first() + " ";
            }

            if (models.size() - 1 == i) {
                clazz += css.last() + " ";
            }

            cell.render(cellBuilder, model);

            sb.append(LI_TEMPLATE.li(i, clazz, cellBuilder.toSafeHtml()));
        }

        final String html = sb.toSafeHtml().asString();

        getElement().setInnerHTML(html);

        if (models.size() > 0) {
            String innerHTML = getElement().getInnerHTML();
            if ("".equals(innerHTML.trim())) {
                fixBug(html);
            }
        }

    }
}