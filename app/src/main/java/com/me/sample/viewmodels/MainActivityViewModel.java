package com.me.sample.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;

import com.me.sample.model.EmployeeResponse;
import com.me.sample.repository.MainRepository;

public class MainActivityViewModel extends BaseViewModel {
    private final String TAG = "MainActivityViewModel";

    public LiveData<EmployeeResponse> mEmpList;

    private final MainRepository mainRepository;

    @ViewModelInject
        MainActivityViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public void getEmployees() {
        failed = mainRepository.failed;
        mEmpList = mainRepository.getEmployees();
        // LiveData<EmployeeResponse> tmp = mainRepository.getEmployees();
        // if (tmp.getValue() != null && isValidData(tmp.getValue())) // mal_formed works
        //     mEmpList = tmp;
        // else {
        //     EmployeeResponse esp = new EmployeeResponse();
        //     mEmpList = new MutableLiveData<>(esp);
        // }
    }

    // private boolean isValidData(EmployeeResponse l) {
    //     // if (l == null || l.getEmployees() == null) return false;
    //     if (l.getEmployees() == null) return false;
    //     Log.d(TAG, "isValidData() ");
    //     for (Employee e : l.getEmployees()) {
    //         if (!isValidEmployee(e)) 
    //             return false;
    //     }
    //     return true;
    // }
    // private boolean isValidEmployee(Employee e) {
    //     return e.getUuid() != null && e.getFullName() != null && e.getEmailAddress() != null
    //         && e.getTeam() != null && e.getEmployeeType() != null;
    // }
}