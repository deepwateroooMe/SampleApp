package com.me.sample.application;

import android.app.Application;

import com.me.sample.BuildConfig;
import com.me.sample.network.INetworkRequiredInfo;

public class NetworkRequiredInfo implements INetworkRequiredInfo {

    private final Application application;

    public NetworkRequiredInfo(Application application){
        this.application = application;
    }

    @Override
        public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }
    @Override
        public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
        public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
        public Application getApplicationContext() {
        return application;
    }
}
