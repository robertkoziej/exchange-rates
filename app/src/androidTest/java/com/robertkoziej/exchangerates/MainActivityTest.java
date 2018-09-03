package com.robertkoziej.exchangerates;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.robertkoziej.exchangerates.activities.MainActivity;
import com.robertkoziej.exchangerates.panemode.SinglePaneModeDetails;
import com.robertkoziej.exchangerates.panemode.SinglePaneModeList;
import com.robertkoziej.exchangerates.panemode.SinglePaneModeNoInternet;
import com.robertkoziej.exchangerates.panemode.TwoPaneMode;
import com.robertkoziej.exchangerates.panemode.ViewModeStrategy;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        activity = activityRule.getActivity();
        Assert.assertNotNull(activity);
    }

    @Test
    @UiThreadTest
    public void setTwoPaneView() {
        activity.setTwoPaneView();
        Assert.assertThat(activity.getPaneMode(), CoreMatchers.<ViewModeStrategy>instanceOf(TwoPaneMode.class));
    }

    @Test
    @UiThreadTest
    public void setSinglePaneListView() {
        activity.setSinglePaneListView();
        Assert.assertThat(activity.getPaneMode(), CoreMatchers.<ViewModeStrategy>instanceOf(SinglePaneModeList.class));
    }

    @Test
    @UiThreadTest
    public void setSinglePaneDetailsView() {
        activity.setSinglePaneDetailsView();
        Assert.assertThat(activity.getPaneMode(), CoreMatchers.<ViewModeStrategy>instanceOf(SinglePaneModeDetails.class));
    }

    @Test
    @UiThreadTest
    public void setSinglePaneNoInternetView() {
        activity.setSinglePaneNoInternetView();
        Assert.assertThat(activity.getPaneMode(), CoreMatchers.<ViewModeStrategy>instanceOf(SinglePaneModeNoInternet.class));
    }

}
