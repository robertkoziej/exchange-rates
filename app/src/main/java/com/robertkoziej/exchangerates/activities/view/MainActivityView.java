package com.robertkoziej.exchangerates.activities.view;

import com.robertkoziej.exchangerates.list.RateItem;
import com.robertkoziej.exchangerates.panemode.ViewModeStrategy;

public interface MainActivityView {
    void updatePaneMode(ViewModeStrategy mode);
    void setTwoPaneView();
    void setSinglePaneListView();
    void setSinglePaneDetailsView();
    void setSinglePaneNoInternetView();
    void updateSinglePaneView();
    void setOnSaveInstanceStateData(RateItem rateItem);
}
