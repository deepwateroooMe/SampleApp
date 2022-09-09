package com.me.sample.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.me.sample.model.EmployeeResponse;
import com.me.sample.network.ApiService;
import com.me.sample.network.BaseObserver;
import com.me.sample.network.NetworkApi;
import com.me.sample.network.utils.KLog;
import com.me.sample.repository.MainRepository;

import io.reactivex.annotations.NonNull;

public class MainActivityViewModel extends ViewModel {
    private final String TAG = "MainActivityViewModel";

    private MutableLiveData<EmployeeResponse> mEmpList = new MutableLiveData<>();
    private MainRepository mRepo;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    
    public void init(){
        Log.d(TAG, "init() ");
        if (mEmpList != null)
            return;
        // mRepo = MainRepository.getInstance();
        // mEmpList = mRepo.getEmployees(); // 这里可能存在一个异步的问题，网络请求总是存在延迟；换另一个思路来写
        mRepo = new MainRepository();
        mEmpList.postValue(mRepo.getEmployees().getValue());

        Log.d(TAG, "(mEmpList.getValue().getEmployees() != null): " + (mEmpList.getValue().getEmployees() != null));
        if (mEmpList.getValue().getEmployees() != null) {
            int cnt = mEmpList.getValue().getEmployees().size();
            Log.d(TAG, "cnt: " + cnt);
        }
    }

    // 刷新数据
    public LiveData<EmployeeResponse> fetchEmployeesList() {
        // mEmpList.postValue(mRepo.getEmployees().getValue());
        // return mEmpList;

        // @SuppressLint("CheckResult")
        //     public MutableLiveData<EmployeeResponse> getEmployees() { 
        final MutableLiveData<EmployeeResponse> employees = new MutableLiveData<>();
        ApiService apiService = NetworkApi.createService(ApiService.class);
// 为什么这里会出问题呢？不是自动进行了线程的切换吗？        
        apiService.getEmployees().compose(NetworkApi.applySchedulers(new BaseObserver<EmployeeResponse>() {
                    @Override // 这里有问题，拿到的数据是空的
                        public void onSuccess(EmployeeResponse empsResponse) {
                        KLog.d(new Gson().toJson(empsResponse));
                        employees.setValue(empsResponse);
                    }
                    @Override
                        public void onFailure(Throwable e) {
                        KLog.e("Employees Error: " + e.toString());
                    }
                }));
        return employees;
        // }
    }
}
