package com.robertkoziej.exchangerates.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;
import com.robertkoziej.exchangerates.R;
import com.robertkoziej.exchangerates.list.RateItem;
import com.robertkoziej.exchangerates.panemode.SinglePaneModeDetails;
import com.robertkoziej.exchangerates.panemode.SinglePaneModeList;
import com.robertkoziej.exchangerates.panemode.SinglePaneModeNoInternet;
import com.robertkoziej.exchangerates.panemode.TwoPaneMode;
import com.robertkoziej.exchangerates.panemode.ViewModeStrategy;
import com.robertkoziej.exchangerates.activities.view.MainActivityView;
import com.robertkoziej.exchangerates.presenters.MainActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.noInternetFragment)
    View noInternetFragment;

    @BindView(R.id.listFragment)
    View listFragment;

    @BindView(R.id.detailsFragment)
    View detailsFragment;

    private AppCompatActivity activityContext;
    public MainActivityPresenter presenter;

    private ViewModeStrategy paneMode;
    private TwoPaneMode twoPaneMode = new TwoPaneMode();
    private SinglePaneModeList singlePaneModeList = new SinglePaneModeList();
    private SinglePaneModeDetails singlePaneModeDetails = new SinglePaneModeDetails();
    private SinglePaneModeNoInternet singlePaneModeNoInternet = new SinglePaneModeNoInternet();
    private boolean areDetailsAvailable;
    private final String DETAILS_AVAILABLE = "DETAILS_AVAILABLE";

    private RateItem currentRateItem;
    private final String CURRENT_RATE_ITEM_KEY = "CURRENT_RATE_ITEM_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityContext = this;
        ButterKnife.bind(this);

        initPresenter();
    }

    private void initPresenter() {
        presenter = new MainActivityPresenter(this, this, getApplicationContext());
        presenter.onCreate();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        setSinglePaneListView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
                setTwoPaneView();
            } else {
                if (currentRateItem == null)
                    setSinglePaneListView();
            }
        }

        presenter.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private ViewModeStrategy selectPaneMode(ViewModeStrategy currentMode, ViewModeStrategy newMode) {
        /* NoInternetFragment is always displayed in single pane mode */
        if (newMode instanceof SinglePaneModeNoInternet)
            return newMode;
            /* ListFragment and DetailsFragment are both displayed in two pane mode */
        else
            return currentMode instanceof TwoPaneMode ? twoPaneMode : newMode;
    }

    @Override
    public void updatePaneMode(ViewModeStrategy mode) {
        paneMode = selectPaneMode(paneMode, mode);
        paneMode.handleVisibility(this, noInternetFragment, listFragment, detailsFragment);
    }

    @Override
    public void setTwoPaneView() {
        updatePaneMode(twoPaneMode);
    }

    @Override
    public void setSinglePaneListView() {
        updatePaneMode(singlePaneModeList);
    }

    @Override
    public void setSinglePaneDetailsView() {
        updatePaneMode(singlePaneModeDetails);
    }

    @Override
    public void setSinglePaneNoInternetView() {
        updatePaneMode(singlePaneModeNoInternet);
    }

    @Override
    public void setOnSaveInstanceStateData(RateItem rateItem) {
        currentRateItem = rateItem;
    }

    @Override
    public void updateSinglePaneView() {
        if (areDetailsAvailable)
            updatePaneMode(singlePaneModeDetails);
        else
            updatePaneMode(singlePaneModeList);
    }

    public ViewModeStrategy getPaneMode() {
        return paneMode;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (currentRateItem != null) {
            Gson gson = new Gson();
            String rateItemInJson = gson.toJson(currentRateItem);
            outState.putString(CURRENT_RATE_ITEM_KEY, rateItemInJson);

            if (paneMode instanceof SinglePaneModeDetails)
                areDetailsAvailable = true;
            outState.putBoolean(DETAILS_AVAILABLE, areDetailsAvailable);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Gson gson = new Gson();
        currentRateItem = gson.fromJson(savedInstanceState.getString(CURRENT_RATE_ITEM_KEY), RateItem.class);
        if (currentRateItem != null) {
            presenter.onRestoreInstanceState(currentRateItem);
        }
        areDetailsAvailable = savedInstanceState.getBoolean(DETAILS_AVAILABLE);
    }

}