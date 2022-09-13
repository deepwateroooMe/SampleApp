package com.me.sample.network;

import com.me.sample.model.EmployeeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService { 
    
    // 下载所有员工数据的接口
// https://s3.amazonaws.com/sq-mobile-interview/employees.json
// https://s3.amazonaws.com/sq-mobile-interview/employees_malformed.json
// https://s3.amazonaws.com/sq-mobile-interview/employees_empty.json

    @GET("/sq-mobile-interview/employees.json")
    // @GET("/sq-mobile-interview/employees_malformed.json")
    // @GET("/sq-mobile-interview/employees_empty.json")
    Observable<EmployeeResponse> getEmployees(); 
} 
