package com.me.sample.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.SerializedName;
import com.me.sample.model.Employee;
import com.me.sample.model.EmployeeResponse;
import com.me.sample.repository.MainRepository;

import java.util.List;

public class MainActivityViewModel extends BaseViewModel {
    private final String TAG = "MainViewModel";

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

    private boolean isValidData(EmployeeResponse l) {
        // if (l == null || l.getEmployees() == null) return false;
        if (l.getEmployees() == null) return false;
        Log.d(TAG, "isValidData() ");
        for (Employee e : l.getEmployees()) {
            if (!isValidEmployee(e)) 
                return false;
        }
        return true;
    }
    private boolean isValidEmployee(Employee e) {
        // e.toString();
        return e.getUuid() != null && e.getFullName() != null && e.getEmailAddress() != null
            && e.getTeam() != null && e.getEmployeeType() != null;
    }
}

// private String uuid;
// @SerializedName("full_name")
// private String fullName;
// @SerializedName("phone_number")
// private String phoneNumber;
    
// @SerializedName("email_address")
// private String emailAddress;
    
// @SerializedName("biography")
// private String biography;
    
// @SerializedName("photo_url_small")
// private String photoUrlSmall;
    
// @SerializedName("photo_url_large")
// private String photoUrlLarge;
    
// @SerializedName("team")
// private String team;
    
// @SerializedName("employee_type")
// private String employeeType;
