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

// 上面的代码中完成了对OkHttp的优化，OkHttp负责网络访问，使用Retrofit发起网络请求，使用RxJava处理返回结果，
// 在上面只是做了线程的切换和错误码的处理，所以还需要的返回做一个处理
public class NetworkApi {
    private final String TAG = "NetworkApi"; 
    
    // 获取APP运行状态及版本信息，用于日志打印
    private static INetworkRequiredInfo iNetworkRequiredInfo;

    // API访问地址
    // https://s3.amazonaws.com/sq-mobile-interview/employees.json
    private static String BASE_URL = "https://s3.amazonaws.com";

    // OkHttp客户端
    private static OkHttpClient okHttpClient;

    // retrofitHashMap
// 这里final的只是map的reference, 里面的内容当然可以变的
    private static final HashMap<String, Retrofit> retrofitHashMap = new HashMap<>(); 

    // 初始化: 提取应用的最基本配置信息
    public static void init(INetworkRequiredInfo networkRequiredInfo) {
        iNetworkRequiredInfo = networkRequiredInfo;
    }

    // 创建serviceClass的实例
    public static <T> T createService(Class<T> serviceClass) {
        return getRetrofit(serviceClass).create(serviceClass);
    }
    
    /**
     * 配置OkHttp: 定制的OkHttpClient, 或者说根据自己的兴趣订制的个性化的OkHttp客户端
     *
     * @return OkHttpClient
     */
    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient != null) return okHttpClient; // 不为空则说明已经配置过了，直接返回即可。

        // OkHttp构建器: 这里用的是OkHttpClient不是用的Retrofit的框架。。。
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置缓存大小
        int cacheSize = 100 * 1024 * 1024;
        // 设置OkHttp网络缓存
        builder.cache(new Cache(iNetworkRequiredInfo.getApplicationContext().getCacheDir(), cacheSize));

        builder.readTimeout(Constant.DEFAULT_TIME, TimeUnit.SECONDS);    // 设置读取超时时间
        // 设置网络请求超时时长，这里设置为6s
        builder.connectTimeout(6, TimeUnit.SECONDS);                     // 设置请求超时时间
        // builder.connectTimeout(Constant.DEFAULT_TIME, TimeUnit.SECONDS); 
        builder.writeTimeout(Constant.DEFAULT_TIME,TimeUnit.SECONDS);    // 设置写入超时时间

        // 添加请求拦截器，如果接口有请求头的话，可以放在这个拦截器里面
        builder.addInterceptor(new RequestInterceptor(iNetworkRequiredInfo));
        // 添加返回拦截器，可用于查看接口的请求耗时，对于网络优化有帮助
        builder.addInterceptor(new ResponseInterceptor());

        // 当程序在debug过程中则打印数据日志，方便调试用。
        if (iNetworkRequiredInfo != null && iNetworkRequiredInfo.isDebug()){
            // iNetworkRequiredInfo不为空且处于debug状态下则初始化日志拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            // HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            //         @Override
            //         public void log(String message) {
            //             Log.i("Http请求参数：", message);
            //         }
            //     });
            // 设置要打印日志的内容等级，BODY为主要内容，还有BASIC、HEADERS、NONE。
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            // 将拦截器添加到OkHttp构建器中
            builder.addInterceptor(httpLoggingInterceptor);
        }

        // OkHttp配置完成
        okHttpClient = builder.build();
        return okHttpClient;
    }

    /**
     * 配置Retrofit
     *
     * @param serviceClass 服务类
     * @return Retrofit
     */
    private static Retrofit getRetrofit(Class serviceClass) {
        if (retrofitHashMap.get(BASE_URL + serviceClass.getName()) != null) {
            // 刚才上面定义的Map中键是String，值是Retrofit，当键不为空时，必然有值，有值则直接返回。
            return retrofitHashMap.get(BASE_URL + serviceClass.getName());
        }
        // 初始化Retrofit  Retrofit是对OKHttp的封装，通常是对网络请求做处理，也可以处理返回数据。
        // Retrofit构建器
        Retrofit.Builder builder = new Retrofit.Builder();
        // 设置访问地址
        builder.baseUrl(BASE_URL); //  基础的地址是在这里设置的
        // 设置OkHttp客户端，传入上面写好的方法即可获得配置后的OkHttp客户端。
        builder.client(getOkHttpClient());
        // 设置数据解析器: 会自动把请求返回的结果（json字符串）通过Gson转化工厂自动转化成与其结构相符的实体Bean
        builder.addConverterFactory(GsonConverterFactory.create());
        // 设置请求回调，使用RxJava 对网络返回进行处理
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create()); // 支持RxJava2
        // retrofit配置完成
        Retrofit retrofit = builder.build();
        // 放入Map中
        retrofitHashMap.put(BASE_URL + serviceClass.getName(), retrofit);
        // 最后返回即可
        return retrofit;
    }

    /**
     * 配置RxJava 完成线程的切换; 如果是Kotlin中完全可以直接使用协程
     *
     * @param observer 这个observer要注意不要使用lifecycle中的Observer
     * @param <T>      泛型
     * @return Observable
     */
// 什么意思呢？当有多个Api接口的时候，如果每个接口都这么写的话，代码量太多，而且不优雅。
// 这里是提取公用部分浓缩在一个方法里，可供每个相同流程的api调用实现线程的自动切换以及结果的观察订阅
// RxJava 的好处是帮我处理线程之间的切换，我们可以在指定订阅的在哪个线程，观察在哪个线程。我们可以通过操作符进行数据变换。整个过程都是链式的，简化逻辑。
    public static <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return upstream -> {
            Observable<T> observable = upstream
                .subscribeOn(Schedulers.io())// 线程订阅
                .observeOn(AndroidSchedulers.mainThread())// 观察Android主线程

                // 绑定生命周期: 防止内存泄露;这里暂时还不知道该如何实现，暂时先放一下
                // 与lifecycleOwner结合，网络请求可以根据lifecyclerOwner生命周期选择执行请求或是自动取消请求
// 这里没有修改好：Hilt 与RxLifeCycle看起来还不能够很好地合作
// 这里可能需要其它的实现方法：相对于高大上的Hilt自动注入，先解决问题内存泄露的问题                
//                .compose(bindUntilEvent(ActivityEvent.DESTROY)) // 或者这里写的地方不对 ？
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)) // 不可以写在static方法这里，感知生命周期，呵呵
                    
                .map(NetworkApi.getAppErrorHandler())// 判断有没有500的错误，有则进入getAppErrorHandler
                .onErrorResumeNext(new HttpErrorHandler<>());// 判断有没有400的错误
            // 订阅观察者
            observable.subscribe(observer);
            return observable;
        };
        // return new ObservableTransformer<T, T>() {
        //     @Override
        //         public ObservableSource<T> apply(Observable<T> upstream) {
        //         Observable<T> observable = upstream
        //             .subscribeOn(Schedulers.io()) // 线程订阅: 在子线程中进行http访问
        //             .observeOn(AndroidSchedulers.mainThread()) // 观察者：Android主线程，处理返回接口
        //             .map(NetworkApi.<T>getAppErrorHandler())   // 判断有没有500的错误，有则进入getAppErrorHandler
        //             .onErrorResumeNext(new HttpErrorHandler<T>());// 判断有没有400的错误
        //         // 订阅观察者
        //         observable.subscribe(observer);
        //         return observable;
        //     }
        // };
    }
    
    /**
     * 错误码处理
     */
    protected static <T> Function<T, T> getAppErrorHandler() {
        return response -> {
            // 当response返回出现500之类的错误时
            if (response instanceof BaseResponse && ((BaseResponse) response).responseCode >= 500) {
                // 通过这个异常处理，得到用户可以知道的原因
                ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                exception.code = ((BaseResponse) response).responseCode;
                exception.message = ((BaseResponse) response).responseError != null ? ((BaseResponse) response).responseError : "";
                throw exception;
            }
            return response;
        };
        // return new Function<T, T>() {
        //     @Override public T apply(T response) throws Exception {
        //         // 当response返回出现500之类的错误时
        //         if (response instanceof BaseResponse && ((BaseResponse) response).responseCode >= 500) {
        //             // 通过这个异常处理，得到用户可以知道的原因
        //             ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
        //             exception.code = ((BaseResponse) response).responseCode;
        //             exception.message = ((BaseResponse) response).responseError != null ? ((BaseResponse) response).responseError : "";
        //             throw exception;
        //         }
        //         return response;
        //     }
        // };
    }
}
