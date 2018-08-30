package com.robertkoziej.exchangerates.network;

import com.robertkoziej.exchangerates.list.Item;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ExchangeRatesApi {

    @GET("latest?")
    Call<Item> getLatestItems();

    @GET("{date}")
    Call<Item> getAllItems(@Path("date") String date);
}
