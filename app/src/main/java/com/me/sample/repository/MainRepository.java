package com.me.sample.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.me.sample.BaseApplication;
import com.me.sample.db.beans.Image;
import com.me.sample.model.EmployeeResponse;
import com.me.sample.network.ApiService;
import com.me.sample.network.BaseObserver;
import com.me.sample.network.NetworkApi;
import com.me.sample.network.utils.DateUtil;
import com.me.sample.network.utils.KLog;
import com.me.sample.utils.Constant;
import com.me.sample.utils.MVUtils;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Main存储库 用于对数据进行处理
 * @author llw
 */
public class MainRepository {
    private static final String TAG = MainRepository.class.getSimpleName();

    // 对 ApiService里定义的网络接口进行请求，然后返回LiveData
    // 为什么要单独建一个包来管理页面的数据获取，其实你可以将这里的代码写到MainViewModel中，
    // 但是你得保证唯一性，因为假如你一个接口在多个地方会使用，你每一个都写到对应的ViewModel中，是不是就会有很多的重复代码？
    // 现在也只是获取网络数据，实际中App的数据还有多个来源，本地数据库、本地缓存。都是可以拿数据的
    // 这些环节如果要写的话，都是要写在这个Repository中的，如果你放到ViewModel中，会导致里面的代码量很大，因为你一个ViewModel中可能有多个网络请求，这很正常。
    @SuppressLint("CheckResult")
    public MutableLiveData<EmployeeResponse> getEmployees() { 
        final MutableLiveData<EmployeeResponse> employees = new MutableLiveData<>();
        
        ApiService apiService = NetworkApi.createService(ApiService.class);
        apiService.getEmployees().compose(NetworkApi.applySchedulers(new BaseObserver<EmployeeResponse>() {
                    @Override
                        public void onSuccess(EmployeeResponse biYingImgResponse) {
                        KLog.d(new Gson().toJson(biYingImgResponse));
                        employees.setValue(biYingImgResponse);
                    }
                    @Override
                        public void onFailure(Throwable e) {
                        KLog.e("Employees Error: " + e.toString());
                    }
                }));
        return employees;
    }

    final MutableLiveData<Image> image = new MutableLiveData<>();
    /**
     * 保存数据
     */
    private void saveImageData(Image image) {
        // 记录今日已请求
        MVUtils.put(Constant.IS_TODAY_REQUEST,true);
        // 记录此次请求的时最晚有效时间戳
        MVUtils.put(Constant.REQUEST_TIMESTAMP, DateUtil.getMillisNextEarlyMorning());

        // 保存到数据库
        Completable insert = BaseApplication.getDb().imageDao().insertAll(image);
        CustomDisposable.addDisposable(insert, ()-> Log.d(TAG, "saveImageData: 插入图片数据成功"));
        // new Thread(() -> BaseApplication.getDb().imageDao().insertAll(image)).start();
        //                // new Image(1,bean.getUrl(),bean.getUrlbase(),bean.getCopyright(),
        //                //           bean.getCopyrightlink(), bean.getTitle()))).start();
    }

    /**
     * 从网络上请求数据
     */
    @SuppressLint("CheckResult")
    private void requestNetworkApi() {
        Log.d(TAG, "requestNetworkApi: 从网络获取");
        ApiService apiService = NetworkApi.createService(ApiService.class);
        apiService.getEmployees().compose(NetworkApi.applySchedulers(new BaseObserver<EmployeeResponse>() {
                    @Override
                        public void onSuccess(EmployeeResponse imgResponse) {
                        // 存储到本地数据库中，并记录今日已请求了数据
                        saveImageData(imgResponse);
                        image.setValue(imgResponse);
                    }
                    @Override
                        public void onFailure(Throwable e) {
                        KLog.e("BiYing Error: " + e.toString());
                    }
                }));
    }

    /**
     * 从本地数据库获取: 这个方法这里不实用；不用存和取员工数据；而数片需要根据员工名字来读取
     */
    private void getLocalDB() {
        Log.d(TAG, "getLocalDB: 从本地数据库获取");
        EmployeeResponse imgResponse = new EmployeeResponse();
        Flowable<Image> imgFlowable = BaseApplication.getDb().imageDao().queryByName("fdkj");
        CustomDisposable.addDisposable(imgFlowable, img -> {
                EmployeeResponse.ImagesBean imagesBean = new EmployeeResponse.ImagesBean();
                imagesBean.setUrl(image.getUrl());
                List<EmployeeResponse.ImagesBean> imagesBeanList = new ArrayList<>();
                imagesBeanList.add(imagesBean);
                imgResponse.setImages(imagesBeanList);
                biyingImage.postValue(imgResponse);
            });
        // new Thread(() -> {
        //         // 从数据库获取
        //         Image image = BaseApplication.getDb().imageDao().queryByName(1);
        //         EmployeeResponse.ImagesBean imagesBean = new EmployeeResponse.ImagesBean();
        //         imagesBean.setUrl(image.getUrl());
        //         List<EmployeeResponse.ImagesBean> imagesBeanList = new ArrayList<>();
        //         imagesBeanList.add(imagesBean);
        //         imgResponse.setImages(imagesBeanList);
        //         biyingImage.postValue(imgResponse);
        //     }).start();
    }

    // 下面的这个思路，可台稍微参考一下
    public MutableLiveData<EmployeeResponse> getBiYing() {
        // 今日此接口是否已请求
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP)){
                // 当前时间未超过次日0点，从本地获取
                getLocalDB();
            } else {
                // 大于则数据需要更新，从网络获取
                requestNetworkApi();
            }
        } else {
            // 没有请求过接口 或 当前时间，从网络获取
            requestNetworkApi();
        }
        return biyingImage;
    }
}
