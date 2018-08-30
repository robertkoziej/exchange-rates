package com.robertkoziej.exchangerates.panemode;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SinglePaneModeDetails implements ViewModeStrategy {
    @Override
    public void handleVisibility(AppCompatActivity context, View noInternetFragment, View listFragment, View detailsFragment) {
        noInternetFragment.setVisibility(View.GONE);
        listFragment.setVisibility(View.GONE);
        detailsFragment.setVisibility(View.VISIBLE);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
