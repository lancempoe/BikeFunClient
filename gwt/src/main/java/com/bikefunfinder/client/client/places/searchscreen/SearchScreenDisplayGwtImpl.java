package com.bikefunfinder.client.client.places.searchscreen;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:31 AM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.Query;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.WidgetList;

public class SearchScreenDisplayGwtImpl extends Composite implements SearchScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, SearchScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    @UiField(provided = true) WidgetList widgetList;
    FormListEntry formListEntry;

    MTextBox search = new MTextBox();
    MListBox targetAudience = new MListBox();
    MTextBox city = new MTextBox();

    @UiHandler("searchRide")
    protected void onSearchButtonPressed(TapEvent event) {
        if (presenter != null) {
            Query query = GWT.create(Query.class);
            query.setQuery(search.getValue());
            query.setTargetAudience(targetAudience.getValue(
                    targetAudience.getSelectedIndex()));
            query.setCity(city.getValue());
            presenter.searchRideButtonSelected(query);
        }
    }

    @Override
    public void setUserPreferences() {
        //TODO save the current settings so we can repopulate
    }


    public SearchScreenDisplayGwtImpl() {

        //Build the form.
        widgetList = new WidgetList();

        formListEntry = new FormListEntry();
        formListEntry.setText("Search:");
        formListEntry.add(search);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("Target Audience:");
        targetAudience.addItem(ScreenConstants.TargetAudienceLabel);
        for(ScreenConstants.TargetAudience enumVal: ScreenConstants.TargetAudience.values()) {
            targetAudience.addItem(enumVal.getDisplayName());
        }
        formListEntry.add(targetAudience);
        widgetList.add(formListEntry);

        formListEntry = new FormListEntry();
        formListEntry.setText("City:");
        formListEntry.add(city);
        widgetList.add(formListEntry);

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("backButton")
    protected void onBackButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.backButtonSelected();
        }
    }
}
