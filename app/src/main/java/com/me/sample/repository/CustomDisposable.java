package com.me.sample.repository;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

// 这里就是通过compositeDisposable去安排线程的切换
// 更主要的：CustomDisposable 具备防内存泄露的功能: 但是这里只是说在往数据库写数据的时候防止内存泄露；网络申请的仍有问题！！！
// RxJava通过Disposable（RxJava1中是Subscription）在适当的时机取消订阅、停止数据流的发射。
// 这在Android等具有Lifecycle概念的场景中非常重要，避免造成一些不必要bug以及对象泄露。
public class CustomDisposable { 

    private static final CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * Flowable
     * @param flowable
     * @param consumer
     * @param <T>
     */
    public static <T> void addDisposable(Flowable<T> flowable, Consumer<T> consumer) {
        compositeDisposable.add(flowable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(consumer));
    }

    /**
     * Completable
     * @param completable
     * @param action
     * @param <T>
     */
    public static <T> void addDisposable(Completable completable, Action action) {
        compositeDisposable.add(completable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(action));
    }
}