package com.me.sample.network;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

// 上面的代码中完成了对OkHttp的优化，OkHttp负责网络访问，使用Retrofit发起网络请求，使用RxJava处理返回结果，
// 在上面只是做了线程的切换和错误码的处理，所以还需要的返回做一个处理
/**
 * 自定义Observer: 自定义一个BaseObserver类，继承自rxjava的Observer
 * @author llw
 */
public abstract class BaseObserver<T> implements Observer<T> {

    // 开始
    @Override
        public void onSubscribe(Disposable d) {}
    
    // 继续
    @Override
        public void onNext(T t) {
        onSuccess(t);
    }

    // 异常
    @Override
        public void onError(Throwable e) {
        onFailure(e);
    }

    // 完成
    @Override
        public void onComplete() {}

    // 成功
    public abstract void onSuccess(T t);

    // 失败
    public abstract void onFailure(Throwable e);
}