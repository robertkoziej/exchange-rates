package com.robertkoziej.exchangerates.presenters;

import android.content.Context;

public abstract class AbstractPresenter<V> implements Presenter<V> {
    protected Context activityContext;
    protected V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void attachActivityContext(Context context) {
        this.activityContext = context;
    }

    @Override
    public void detachActivityContext(Context context) {
        this.activityContext = null;
    }

    @Override
    public boolean isActivityContextAttached() {
        return activityContext != null;
    }

    @Override
    public void detachAll() {
        this.activityContext = null;
        this.view = null;
    }
}