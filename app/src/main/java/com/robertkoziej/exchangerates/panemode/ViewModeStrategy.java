package com.robertkoziej.exchangerates.panemode;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public interface ViewModeStrategy {
    void handleVisibility(AppCompatActivity context, View noInternetFragment, View listFragment, View detailsFragment);
}
