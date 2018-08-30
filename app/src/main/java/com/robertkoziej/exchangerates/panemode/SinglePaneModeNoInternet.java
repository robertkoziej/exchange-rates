package com.robertkoziej.exchangerates.panemode;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SinglePaneModeNoInternet implements ViewModeStrategy {
    @Override
    public void handleVisibility(AppCompatActivity context, View noInternetFragment, View listFragment, View detailsFragment) {
        noInternetFragment.setVisibility(View.VISIBLE);
        listFragment.setVisibility(View.GONE);
        detailsFragment.setVisibility(View.GONE);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        context.getSupportActionBar().setDisplayShowHomeEnabled(false);
    }
}
