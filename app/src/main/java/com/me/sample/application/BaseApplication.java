package com.me.sample.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.me.sample.activities.ActivityManager;
import com.me.sample.db.AppDatabase;
import com.me.sample.network.NetworkApi;
import com.me.sample.utils.MVUtils;
import com.tencent.mmkv.MMKV;

// @HiltAndroidApp
public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
        public static Context context;

    public static AppDatabase db;

    @Override
        public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        NetworkApi.init(new NetworkRequiredInfo(this));

        db = AppDatabase.getInstance(this);

        MMKV.initialize(this);
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