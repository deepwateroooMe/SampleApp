package com.me.sample.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 基础返回类: 在通过网络请求返回数据时，先进行一个数据解析，得到结果码和错误信息
 * @author llw
 */
public class BaseResponse {

    // 返回码
    @SerializedName("res_code")
    @Expose
    public Integer responseCode;

    // 返回的错误信息
    @SerializedName("res_error")
    @Expose
    public String responseError;
}

// public class BaseResponse {
//     // 返回码
//     @SerializedName("res_code")
//     @Expose
//     public Integer responseCode;
//     // 返回的错误信息
//     @SerializedName("res_error")
//     @Expose
//     public String responseError;
// }