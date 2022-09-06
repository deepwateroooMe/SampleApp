package com.me.sample.network;

import io.reactivex.Observable;

public class Apis { // interface methods
    
    // 员工数据：地址
    // String BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/employees.json";
    // 下面是图片地址的基网
    String BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/";
    // String BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/photos/";
    
    // 下面这个是需要的，因为需要从网络上去下载各个员工的图片
    @GET
    // Call<ResponseData<List<Employee>>> getEmployees();
    //OkHttp+Retrofit
    //OkHttp+Retrofit+RxJava
    Observable<ResponseData<List<Employee>>> getEmployees();

    @GET
    Observable<Image> getImage(@Url String imgUrl);
}
