package com.robertkoziej.exchangerates.fragments.views;

import com.robertkoziej.exchangerates.list.RecyclerItem;

import java.util.List;

public interface ListFragmentView {
    void setView(List<RecyclerItem> dataList);
    void updateView(List<RecyclerItem> dataList);
    void showErrorDialog(int textId);
    void dismissErrorDialog();
}
