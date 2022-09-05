package com.me.sample.network.interceptor;


import com.me.sample.network.INetworkRequiredInfo;

import okhttp3.Interceptor;

/**
 * 请求拦截器
 * @author llw
 */
public class RequestInterceptor implements Interceptor {
    /**
     * 网络请求信息
     */
    private INetworkRequiredInfo iNetworkRequiredInfo;

    public RequestInterceptor(INetworkRequiredInfo iNetworkRequiredInfo){
        this.iNetworkRequiredInfo = iNetworkRequiredInfo;
    }
}
