package com.robertkoziej.exchangerates.presenters;

import android.content.Context;

public interface Presenter<V> {
    void attachView(V view);
    void detachView();
    boolean isViewAttached();
    void attachActivityContext(Context context);
    void detachActivityContext(Context context);
    boolean isActivityContextAttached();
    void detachAll();
}