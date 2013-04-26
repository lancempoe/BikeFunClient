package com.bikefunfinder.client.client.places.searchscreen;
/*
 * @author: tneuwerth
 * @created 4/10/13 11:31 AM
 */

import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.Query;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.WidgetList;

import java.util.List;

public class SearchScreenDisplayGwtImpl extends Composite implements SearchScreenDisplay {

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, SearchScreenDisplayGwtImpl> {
    }

    private Presenter presenter;

    @UiField(provided = true) WidgetList widgetList;
    MTextBox mTextBoxSearch = new MTextBox();
    MListBox mListBoxTargetAudience = new MListBox();
    MTextBox mTextBoxCity = new MTextBox();

    @UiHandler("searchRide")
    protected void onSearchButtonPressed(TapEvent event) {
        if (presenter != null) {
            Query query = GWT.create(Query.class);
            query.setQuery(mTextBoxSearch.getValue());
            query.setTargetAudience(mListBoxTargetAudience.getValue(
                    mListBoxTargetAudience.getSelectedIndex()));
//            query.setCity(mTextBoxCity.getValue());
            presenter.searchRideButtonSelected(query);
        }
    }

    @Override
    public void setUserPreferences() {
        //TODO save the current settings so we can repopulate
    }


    public SearchScreenDisplayGwtImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        //Build the form.
        widgetList = new WidgetList();

        mTextBoxSearch.setPlaceHolder("Search");
        widgetList.add(mTextBoxSearch);

        for(ScreenConstants.TargetAudience enumVal: ScreenConstants.TargetAudience.values()) {
            mListBoxTargetAudience.addItem(enumVal.getDisplayName());
        }
        widgetList.add(mListBoxTargetAudience);

//        mTextBoxCity.setPlaceHolder("City");
//        widgetList.add(mTextBoxCity);

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
