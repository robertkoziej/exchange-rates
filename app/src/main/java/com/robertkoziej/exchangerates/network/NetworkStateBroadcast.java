package com.robertkoziej.exchangerates.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

public class NetworkStateBroadcast {

    private static final String TAG = NetworkStateBroadcast.class.getSimpleName();

    public final static String ACTION = "INTERNET_CONNECTION";
    public static IntentFilter filter = new IntentFilter(ACTION);

    Handler handler = new Handler();

    public void registerBroadcast(final Context context, final int delayMillis) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isConnected(context);
                registerBroadcast(context, delayMillis);
            }
        }, delayMillis);
    }

    public void unregisterBroadcast() {
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
    }

    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isOnline = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (isOnline) {
                Intent intent = new Intent();
                intent.setAction(ACTION);
                context.sendBroadcast(intent);
            }
            return isOnline;
        }
        Log.e(TAG, "Context lost");
        return false;
    }
}
