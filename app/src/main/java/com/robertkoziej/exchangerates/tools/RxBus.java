package com.robertkoziej.exchangerates.tools;

import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public final class RxBus {

    private static PublishSubject<Object> sSubject = PublishSubject.create();

    private RxBus() {}

    public static Disposable subscribe(@NonNull Consumer<Object> action) {
        return sSubject.subscribe(action);
    }

    public static void publish(@NonNull Object message) {
        sSubject.onNext(message);
    }
}
