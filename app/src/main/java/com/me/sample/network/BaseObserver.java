package com.me.sample.network;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {

    @Override
        public void onSubscribe(Disposable d) {}
    
    @Override
        public void onNext(T t) {
        try {
            onSuccess(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
        public void onError(Throwable e) {
        onFailure(e);
    }

    @Override
        public void onComplete() {}

    public abstract void onSuccess(T t) throws IOException;

    public abstract void onFailure(Throwable e);
}