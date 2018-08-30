package com.robertkoziej.exchangerates.fragments;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.robertkoziej.exchangerates.R;
import com.robertkoziej.exchangerates.network.NetworkStateBroadcast;
import com.robertkoziej.exchangerates.tools.RxBus;

public class NoInternetFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.no_internet_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button internetButton = getActivity().findViewById(R.id.internetButton);
        internetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager wifiManager = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (wifiManager != null) {
                    wifiManager.setWifiEnabled(true);
                    RxBus.publish(NetworkStateBroadcast.ACTION);
                }
            }
        });
    }
}
