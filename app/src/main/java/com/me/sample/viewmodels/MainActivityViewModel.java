package com.me.sample.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.me.sample.db.bean.Employee;
import com.me.sample.model.EmployeeResponse;
import com.me.sample.repository.MainRepository;

public class MainActivityViewModel extends BaseViewModel {
    private final String TAG = "MainActivityViewModel";

    public LiveData<EmployeeResponse> mEmpList;

    private MainRepository mainRepository;

    // @ViewModelInject
    // MainActivityViewModel(MainRepository mainRepository) {
    //     this.mainRepository = mainRepository;
    // }
    public void init() { // init: 无法区分 是系统恢复应用或是活动，还是新创建应用和活动
        if (mEmpList != null) return;
        mainRepository = MainRepository.getInstance();

        // failed = mainRepository.failed;
        // mEmpList = mainRepository.getEmployees(); // 这里自动从网络读是不对的
    }

    public void retrieveEmployees() {
        mEmpList = mainRepository.retrieveEmployees();
    }

    public void getEmployees() {
        failed = mainRepository.failed;
        mEmpList = mainRepository.getEmployees();
// walk around: 暂时还是把逻辑放在MainActivity UI中鉴定过滤分类        
// 还没有想明白：为什么我在viewmodel里过滤数据，就不对？根上面 getEmployees() 的结果是发布在主线程引起的吗？
// 还是说，网络请示的时候，这里的变化太多太快，过滤时的结果与时间都卡不准，过滤不好数据而已呢？
        // LiveData<EmployeeResponse> tmp = mainRepository.getEmployees();
        // if (tmp.getValue() != null && isValidData(tmp.getValue())) // mal_formed works
        //     mEmpList = tmp;
        // else { // 含有无效数据
        //     EmployeeResponse esp = new EmployeeResponse();
        //     mEmpList = new MutableLiveData<>(esp);
        // }
    }
    // private boolean isValidData(EmployeeResponse l) {
    //     if (l == null || l.getEmployees() == null) return false;
    //     // if (l.getEmployees() == null) return false;
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