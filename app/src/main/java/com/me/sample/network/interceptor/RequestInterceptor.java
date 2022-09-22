package com.me.sample.network.interceptor;


import com.me.sample.network.INetworkRequiredInfo;
import com.me.sample.network.utils.DateUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    private INetworkRequiredInfo iNetworkRequiredInfo;

    public RequestInterceptor(INetworkRequiredInfo iNetworkRequiredInfo){
        this.iNetworkRequiredInfo = iNetworkRequiredInfo;
    }

    @Override
        public Response intercept(Chain chain) throws IOException {
        String nowDateTime = DateUtil.getDateTime();
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os","android");
        builder.addHeader("appVersionCode",this.iNetworkRequiredInfo.getAppVersionCode());
        builder.addHeader("appVersionName",this.iNetworkRequiredInfo.getAppVersionName());
        builder.addHeader("datetime", nowDateTime);
        return chain.proceed(builder.build());
    }
}