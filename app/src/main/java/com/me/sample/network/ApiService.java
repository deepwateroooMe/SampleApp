package com.me.sample.network;

import com.me.sample.model.Employee;
import com.me.sample.model.EmployeeResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService { 
    
    // 下载所有员工数据的接口
    @GET("/employees.json")
    // Call<ResponseBody> getEmployees();
    Call<List<Employee>> getEmployees();
    // Observable<ResponseBody> getEmployees();
    // Observable<EmployeeResponse> getEmployees();
} 
