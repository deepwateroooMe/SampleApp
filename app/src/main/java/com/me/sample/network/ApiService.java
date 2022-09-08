package com.me.sample.network;

import android.media.Image;

import com.me.sample.db.Employee;
import com.me.sample.model.EmployeeResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService { // interface methods
    
    // 员工数据：地址
    // String BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/employees.json";
    // 下面是图片地址的基网
//    String BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/";
    // String BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/photos/";
    
    // 下载所有员工数据的接口
    @GET("/employees.json")
    Observable<EmployeeResponse> getEmployees();

    // 下载每个员工头像图片的接口
    // @GET
    // Observable<Image> getImage(@Url String imgUrl);
}
