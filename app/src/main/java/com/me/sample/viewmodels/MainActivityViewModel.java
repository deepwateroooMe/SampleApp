package com.me.sample.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.me.sample.model.EmployeeResponse;
import com.me.sample.repository.MainRepository;

public class MainActivityViewModel extends BaseViewModel {
    private final String TAG = "MainViewModel";

    public LiveData<EmployeeResponse> mEmpList; // 改成是公用的，数据得到更新了，应该就不会丢失了
    private final MainRepository mainRepository;

    // private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    @ViewModelInject
        MainActivityViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public void getEmployees() {
        failed = mainRepository.failed;
        mEmpList = mainRepository.getEmployees();
    }
}
