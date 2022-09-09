package com.me.sample.repository;

import android.annotation.SuppressLint;
import android.media.Image;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.me.sample.model.EmployeeResponse;
import com.me.sample.network.ApiService;
import com.me.sample.network.BaseObserver;
import com.me.sample.network.NetworkApi;
import com.me.sample.network.utils.KLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.EntryPointAccessors;
import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Main存储库 用于对数据进行处理
 * @author llw
 */
@SuppressLint("CheckResult")
public class MainRepository {
    private static final String TAG = MainRepository.class.getSimpleName();

    /**
     * 员工链表数据
     */
    final MutableLiveData<EmployeeResponse> employees = new MutableLiveData<>();
    // final MutableLiveData<EmployeeResponse> empResponse = new MutableLiveData<>();
    public final MutableLiveData<String> failed = new MutableLiveData<>();

    @Inject
    public MainRepository() {
        // 获取mvUtils: 我这里都没有用到，暂时先这样
//        MVUtilsEntryPoint entryPoint =
//            EntryPointAccessors.fromApplication(getContext(), MVUtilsEntryPoint.class);
//        mvUtils = entryPoint.getMVUtils();
    }

    // // for debugging propose only
    // private List<EmployeeResponse.EmployeesBean> dataSet = new ArrayList<>();
    // public MutableLiveData<EmployeeResponse> getEmployees(){
    //     EmployeeResponse.EmployeesBean tmp = new EmployeeResponse.EmployeesBean();
    //     tmp.setUuid("0d8fcc12-4d0c-425c-8355-390b312b909c");
    //     tmp.setName("Justine Mason");
    //     tmp.setImgUrlSmall("https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg");
    //     dataSet.add(tmp);
    //     EmployeeResponse tmptwo = new EmployeeResponse();
    //     tmptwo.setEmployees(dataSet);
    //     return new MutableLiveData<>(tmptwo);
    // }

    // 对 ApiService里定义的网络接口进行请求，然后返回LiveData
    // 为什么要单独建一个包来管理页面的数据获取，其实你可以将这里的代码写到MainViewModel中，
    // 但是你得保证唯一性，因为假如你一个接口在多个地方会使用，你每一个都写到对应的ViewModel中，是不是就会有很多的重复代码？
    // 现在也只是获取网络数据，实际中App的数据还有多个来源，本地数据库、本地缓存。都是可以拿数据的
    // 这些环节如果要写的话，都是要写在这个Repository中的，如果你放到ViewModel中，会导致里面的代码量很大，因为你一个ViewModel中可能有多个网络请求，这很正常。
    @SuppressLint("CheckResult")
    public MutableLiveData<EmployeeResponse> getEmployees() { 
        ApiService apiService = NetworkApi.createService(ApiService.class);
        apiService.getEmployees().compose(NetworkApi.applySchedulers(new BaseObserver<EmployeeResponse>() {
                    @Override // 这里有问题，拿到的数据是空的
                        public void onSuccess(EmployeeResponse empsResponse) {
                        if (empsResponse != null && empsResponse.getEmployees() != null) {
                            KLog.d(new Gson().toJson(empsResponse));
                            employees.setValue(empsResponse);
                        } else
                            failed.postValue("the employees list is empty.");
                    }
                    @Override
                        public void onFailure(Throwable e) {
                        KLog.e("Employees Error: " + e.toString());
                    }
                }));
        return employees;
    }
}
