package com.googlecode.gwtphonegap.showcase.client.widgets;

import com.googlecode.gwtphonegap.showcase.client.model.SearchResult;

/**
 * Created with IntelliJ IDEA.
 * User: nathanfrost
 * Date: 3/16/13
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicCellSearchDetailImpl extends BasicCell<SearchResult> {

    @Override
    public String getDisplayString(SearchResult model) {
        String html = "<h1>" + model.getName() + "</h1><h2>" + model.getTime() + "</h2><h2>" + model.getLocation() + "</h2>";
        return html;
    }

    @Override
    public boolean canBeSelected(SearchResult model) {
        return true;
    }
}
