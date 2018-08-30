package com.robertkoziej.exchangerates.fragments.views;

import com.robertkoziej.exchangerates.list.RateItem;

public interface DetailsFragmentView {
    void setView(RateItem dataList);
    void showErrorDialog(int textId);
    void dismissErrorDialog();
}
