package com.robertkoziej.exchangerates.presenters;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.robertkoziej.exchangerates.R;
import com.robertkoziej.exchangerates.fragments.views.ListFragmentView;
import com.robertkoziej.exchangerates.list.DateItem;
import com.robertkoziej.exchangerates.list.Item;
import com.robertkoziej.exchangerates.list.LoadMoreItem;
import com.robertkoziej.exchangerates.list.RateItem;
import com.robertkoziej.exchangerates.list.RecyclerItem;
import com.robertkoziej.exchangerates.network.ExchangeRatesApi;
import com.robertkoziej.exchangerates.network.ExchangeRatesApiInstance;
import com.robertkoziej.exchangerates.network.NetworkStateBroadcast;
import com.robertkoziej.exchangerates.tools.RxBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragmentPresenter extends AbstractPresenter<ListFragmentView> {
    private static final String TAG = ListFragmentPresenter.class.getSimpleName();
    private ExchangeRatesApi exchangeRatesApi;
    public static final String UPDATE_COMMAND = "UPDATE_COMMAND";
    public static final String LOAD_NEXT_COMMAND = "LOAD_NEXT_COMMAND";

    private int daysDiff;
    Call<Item> restCall;

    ReentrantLock lock = new ReentrantLock();

    private List<RecyclerItem> listData = new ArrayList<>();

    public ListFragmentPresenter(Context context, ListFragmentView view) {
        attachActivityContext(context);
        attachView(view);
    }

    public void onCreate() {
        exchangeRatesApi = ExchangeRatesApiInstance.getApiInstance().create(ExchangeRatesApi.class);
    }

    public void onActivityCreated() {
        rxSubscribe();
    }

    private void loadAllItems(final String date) {
        /* This trylock() protects listData from being modified concurrently. */
        if (lock.tryLock()) {
            addLoadingItem();
            restCall = exchangeRatesApi.getAllItems(date);
            restCall.enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {
                    if (response.isSuccessful()) {
                        removeLoadingItem();
                        if (response.body() != null) {

                            Item item = response.body();
                            listData.add(new DateItem(date));

                            Object o = new Gson().fromJson(item.getRates().toString(), Object.class);
                            com.google.gson.internal.LinkedTreeMap<String, Double> map = (com.google.gson.internal.LinkedTreeMap) o;

                            for (com.google.gson.internal.LinkedTreeMap.Entry<String, Double> entry : map.entrySet()) {
                                listData.add(new RateItem(item.getDate(), entry.getKey(), entry.getValue()));
                            }
                            Log.d(TAG, response.body().toString());
                        }
                    } else {
                        view.showErrorDialog(R.string.content_not_available);
                    }
                    lock.unlock();
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {
                    t.printStackTrace();

                    if (!NetworkStateBroadcast.isConnected(activityContext)) {
                        RxBus.publish(NetworkStateBroadcast.ACTION);
                        lock.unlock();
                        return;
                    }
                    view.showErrorDialog(R.string.host_not_available);
                }
            });
        }
    }


    private void rxSubscribe() {
        RxBus.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (o instanceof String) {
                    String command = (String) o;
                    if (command.equals(UPDATE_COMMAND)) {
                        // Online: need to remove load indicator (listData last element)
                        if (listData.size() > 0) {
                            removeLoadingItem();
                            loadAllItems(getDiffDate(daysDiff));
                        }
                        // First use
                        else
                            loadAllItems(getDiffDate(0));
                    }
                    else if (command.equals(LOAD_NEXT_COMMAND)) {
                        if (!lock.isLocked()) {
                            daysDiff--;
                            loadAllItems(getDiffDate(daysDiff));
                        }
                    }
                }
            }
        });
    }

    private String getDiffDate(int daysDiff) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, daysDiff);
        Date newDate = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(newDate);
    }

    private void addLoadingItem() {
        listData.add(new LoadMoreItem());
        view.setView(listData);
        view.updateView(listData);
    }

    private void removeLoadingItem() {
        if (listData.get(listData.size() - 1) instanceof LoadMoreItem) {
            listData.remove(listData.size() - 1);
            view.updateView(listData);
        }
    }

}
