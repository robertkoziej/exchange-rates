package com.robertkoziej.exchangerates.panemode;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TwoPaneMode implements ViewModeStrategy {
    @Override
    public void handleVisibility(AppCompatActivity context, View noInternetFragment, View listFragment, View detailsFragment) {
        noInternetFragment.setVisibility(View.GONE);
        listFragment.setVisibility(View.VISIBLE);
        detailsFragment.setVisibility(View.VISIBLE);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        context.getSupportActionBar().setDisplayShowHomeEnabled(false);
    }
}
