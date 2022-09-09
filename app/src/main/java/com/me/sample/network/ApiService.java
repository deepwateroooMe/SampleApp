package com.me.sample.network;

import com.me.sample.model.EmployeeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService { 
    
    // 下载所有员工数据的接口
    @GET("employees.json")
    Observable<EmployeeResponse> getEmployees();
}
