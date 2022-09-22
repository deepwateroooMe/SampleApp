package com.me.sample.network;

import com.me.sample.network.errorhandler.ExceptionHandle;
import com.me.sample.network.errorhandler.HttpErrorHandler;
import com.me.sample.network.interceptor.RequestInterceptor;
import com.me.sample.network.interceptor.ResponseInterceptor;
import com.me.sample.utils.Constant;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkApi {
    private final String TAG = "NetworkApi"; 
    
    private static INetworkRequiredInfo iNetworkRequiredInfo;

    private static String BASE_URL = "https://s3.amazonaws.com";

    private static OkHttpClient okHttpClient;

    private static final HashMap<String, Retrofit> retrofitHashMap = new HashMap<>(); 

    public static void init(INetworkRequiredInfo networkRequiredInfo) {
        iNetworkRequiredInfo = networkRequiredInfo;
    }

    public static <T> T createService(Class<T> serviceClass) {
        return getRetrofit(serviceClass).create(serviceClass);
    }
    
    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient != null) return okHttpClient; 

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        int cacheSize = 100 * 1024 * 1024;
        builder.cache(new Cache(iNetworkRequiredInfo.getApplicationContext().getCacheDir(), cacheSize));

        builder.readTimeout(Constant.DEFAULT_TIME, TimeUnit.SECONDS);  
        builder.connectTimeout(6, TimeUnit.SECONDS);                   
        builder.writeTimeout(Constant.DEFAULT_TIME,TimeUnit.SECONDS);  

        builder.addInterceptor(new RequestInterceptor(iNetworkRequiredInfo));
        builder.addInterceptor(new ResponseInterceptor());

        if (iNetworkRequiredInfo != null && iNetworkRequiredInfo.isDebug()){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            // HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            //         @Override
            //         public void log(String message) {
            //             Log.i("Http Request Parameters：", message);
            //         }
            //     });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        okHttpClient = builder.build();
        return okHttpClient;
    }

    private static Retrofit getRetrofit(Class serviceClass) {
        if (retrofitHashMap.get(BASE_URL + serviceClass.getName()) != null) {
            return retrofitHashMap.get(BASE_URL + serviceClass.getName());
        }
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL); //  基础的地址是在这里设置的
        builder.client(getOkHttpClient());
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create()); // 支持RxJava2
        Retrofit retrofit = builder.build();
        retrofitHashMap.put(BASE_URL + serviceClass.getName(), retrofit);
        return retrofit;
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return upstream -> {
            Observable<T> observable = upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

//                .compose(bindUntilEvent(ActivityEvent.DESTROY)) 
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this))
                    
                .map(NetworkApi.getAppErrorHandler())        
                .onErrorResumeNext(new HttpErrorHandler<>());
            observable.subscribe(observer);
            return observable;
        };
    }
    
    protected static <T> Function<T, T> getAppErrorHandler() {
        return response -> {
            if (response instanceof BaseResponse && ((BaseResponse) response).responseCode >= 500) {
                ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                exception.code = ((BaseResponse) response).responseCode;
                exception.message = ((BaseResponse) response).responseError != null ? ((BaseResponse) response).responseError : "";
                throw exception;
            }
            return response;
        };
    }
}
