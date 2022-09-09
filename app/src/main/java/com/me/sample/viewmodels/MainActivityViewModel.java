package com.me.sample.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.me.sample.model.EmployeeResponse;
import com.me.sample.repository.MainRepository;

import io.reactivex.annotations.NonNull;

public class MainActivityViewModel extends ViewModel {
    private final String TAG = "MainActivityViewModel";

    private MutableLiveData<EmployeeResponse> mEmpList;
    private MainRepository mRepo;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    
    public void init(){
        if (mEmpList != null){
            return;
        }
        mRepo = MainRepository.getInstance();
        mEmpList = mRepo.getEmployees();
    }

    // public boolean hasCachedList() {
    //     return !(mEmpList == null);
    // }
    
    // 刷新数据
    public LiveData<EmployeeResponse> fetchEmployeesList() { // 原理：以中介桥梁仓库为中转获取网络数据
        return mEmpList = mRepo.getEmployees();
    }
}
