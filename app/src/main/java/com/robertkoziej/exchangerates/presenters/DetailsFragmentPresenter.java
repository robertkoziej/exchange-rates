package com.robertkoziej.exchangerates.presenters;

import android.content.Context;
import android.util.Log;

import com.robertkoziej.exchangerates.fragments.views.DetailsFragmentView;
import com.robertkoziej.exchangerates.list.RateItem;
import com.robertkoziej.exchangerates.network.ExchangeRatesApi;
import com.robertkoziej.exchangerates.network.ExchangeRatesApiInstance;
import com.robertkoziej.exchangerates.tools.RxBus;

import io.reactivex.functions.Consumer;

public class DetailsFragmentPresenter extends AbstractPresenter<DetailsFragmentView> {
    private final String TAG = DetailsFragmentPresenter.class.getSimpleName();

    private ExchangeRatesApi exchangeRatesApi;

    public DetailsFragmentPresenter(Context context, DetailsFragmentView view) {
        attachActivityContext(context);
        attachView(view);
    }

    public void onCreate() {
        exchangeRatesApi = ExchangeRatesApiInstance.getApiInstance().create(ExchangeRatesApi.class);
    }

    public void onActivityCreated() {
        rxSubscribe();
    }

    private void loadItem(RateItem item) {
        view.setView(item);
    }

    private void rxSubscribe() {
        RxBus.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (o instanceof RateItem) {
                    RateItem item = (RateItem) o;
                    Log.d(TAG, item.toString());
                    loadItem(item);
                }
            }
        });
    }
}
