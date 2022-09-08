package com.me.sample;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.me.sample.network.NetworkApi;
import com.me.sample.network.NetworkRequiredInfo;

/**
 * 自定义 Application
 * @author llw
 */
public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
        public static Context context;

    @Override
        public void onCreate() {
        super.onCreate();
        // 初始化: 这里是在应用创建的时候就初始化网络请求的初始化配置
        // 能够提速网络请求结果的返回速度，会延缓应用的启动吗？是在主线程吗？好像不是，应该是在ApplicationThread里执行 ？
        NetworkApi.init(new NetworkRequiredInfo(this));
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}