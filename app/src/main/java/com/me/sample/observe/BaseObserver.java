package com.me.sample.observe;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 自定义Observer
 * @author llw
 */
public abstract class BaseObserver<T> implements Observer<T> {

    //开始
    @Override
        public void onSubscribe(Disposable d) {

    }
    
    //继续
    @Override
        public void onNext(T t) {
        onSuccess(t);
    }
}