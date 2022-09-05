package com.me.sample;

import androidx.lifecycle.ViewModel;

import com.me.sample.db.Employee;

public class MainActivityViewModel extends ViewModel {
    private final String TAG = "MainActivityViewModel";

    private MutableLiveData<List<Employee>> mEmpList;

}
