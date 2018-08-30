package com.robertkoziej.exchangerates.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.robertkoziej.exchangerates.list.RateItem;
import com.robertkoziej.exchangerates.network.NetworkStateBroadcast;
import com.robertkoziej.exchangerates.activities.view.MainActivityView;
import com.robertkoziej.exchangerates.tools.RxBus;

import io.reactivex.functions.Consumer;

public class MainActivityPresenter extends AbstractPresenter<MainActivityView> {

    private Context applicationContext;

    public MainActivityPresenter(Context context, MainActivityView view, Context applicationContext) {
        attachActivityContext(context);
        attachView(view);
        this.applicationContext = applicationContext;
    }

    public void onCreate() {
        registerBroadcasts();
    }

    public void onStart() {
        if (!NetworkStateBroadcast.isConnected(activityContext)) {
            RxBus.publish(NetworkStateBroadcast.ACTION);
            view.setSinglePaneNoInternetView();
        }
        RxBus.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (o instanceof RateItem) {
                    view.setSinglePaneDetailsView();
                    view.setOnSaveInstanceStateData((RateItem) o);
                }
                else if (o instanceof String) {
                    String action = (String) o;
                    if (action.equals(NetworkStateBroadcast.ACTION)) {
                        view.setSinglePaneNoInternetView();
                        registerBroadcasts();
                    }
                }
            }
        });
    }

    public void onDestroy() {
        unregisterBroadcasts();
    }

    private NetworkStateBroadcast networkStateBroadcast = new NetworkStateBroadcast();
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NetworkStateBroadcast.ACTION)) {
                unregisterBroadcasts();
                view.updateSinglePaneView();
                RxBus.publish(ListFragmentPresenter.UPDATE_COMMAND);
            }
        }
    };

    private void registerBroadcasts() {
        networkStateBroadcast.registerBroadcast(applicationContext, 1000);
        activityContext.registerReceiver(broadcastReceiver, NetworkStateBroadcast.filter);
    }

    private void unregisterBroadcasts() {
        networkStateBroadcast.unregisterBroadcast();
        try {
            activityContext.unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    public void onRestoreInstanceState(RateItem rateItem) {
        RxBus.publish(rateItem);
    }
}
