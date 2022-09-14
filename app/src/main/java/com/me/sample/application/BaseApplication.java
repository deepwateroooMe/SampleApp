package com.me.sample.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.me.sample.activities.ActivityManager;
import com.me.sample.db.AppDatabase;
import com.me.sample.network.NetworkApi;
import com.me.sample.utils.MVUtils;
import com.tencent.mmkv.MMKV;
/**
 * 自定义 Application
 * @author llw
 */
// @HiltAndroidApp
public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
        public static Context context;

    // 数据库
    public static AppDatabase db;

    @Override
        public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        // 初始化: 这里是在应用创建的时候就初始化网络请求的初始化配置
        // 能够提速网络请求结果的返回速度，会延缓应用的启动吗？是在主线程吗？好像不是，应该是在ApplicationThread里执行 ？
        // 初始化网络框架
        NetworkApi.init(new NetworkRequiredInfo(this));

        // 创建本地数据库
        db = AppDatabase.getInstance(this);

        // MMKV初始化
        MMKV.initialize(this);
        // 工具类的初始化: 是否可以延后处理，寻找更合适的时机来初始化不是完全必要的工具类 ？
        MVUtils.getInstance();
    }

    public static Context getContext() {
        return context;
    }

    public static AppDatabase getDb(){
        return db;
    }

    public static ActivityManager getActivityManager() {
        return ActivityManager.getInstance();
    }
}