package com.me.sample.repository;

import android.annotation.SuppressLint;
import android.media.Image;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.me.sample.application.BaseApplication;
import com.me.sample.db.bean.Employee;
import com.me.sample.model.EmployeeResponse;
import com.me.sample.network.ApiService;
import com.me.sample.network.BaseObserver;
import com.me.sample.network.NetworkApi;
import com.me.sample.network.utils.KLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

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
    public final MutableLiveData<String> failed = new MutableLiveData<>();

    @Inject
    public MainRepository() { }

    // 对 ApiService里定义的网络接口进行请求，然后返回LiveData
    // 为什么要单独建一个包来管理页面的数据获取，其实你可以将这里的代码写到MainViewModel中，
    // 但是你得保证唯一性，因为假如你一个接口在多个地方会使用，你每一个都写到对应的ViewModel中，是不是就会有很多的重复代码？
    // 现在也只是获取网络数据，实际中App的数据还有多个来源，本地数据库、本地缓存。都是可以拿数据的
    // 这些环节如果要写的话，都是要写在这个Repository中的，如果你放到ViewModel中，会导致里面的代码量很大，因为你一个ViewModel中可能有多个网络请求，这很正常。
    public MutableLiveData<EmployeeResponse> getEmployees() {
        ApiService apiService = NetworkApi.createService(ApiService.class);
        apiService.getEmployees().compose(NetworkApi.applySchedulers(new BaseObserver<EmployeeResponse>() {
                    @Override 
                        public void onSuccess(EmployeeResponse empsResponse) {
                        // KLog.d(new Gson().toJson(empsResponse));
                        employees.setValue(empsResponse);
                    }
                    @Override
                        public void onFailure(Throwable e) {
                        KLog.e("Employees Error: " + e.toString());
                    }
                }));
        return employees;
    }

    /**
     * 保存员工链表数据
     */
    private void saveEmployees(EmployeeResponse employeesResponse) {
        Completable deleteAll = BaseApplication.getDb().employeeDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, () -> {
                Log.d(TAG, "saveEmployees: 删除数据成功");
                List<Employee> employeesList = new ArrayList<>();
                for (Employee employee : employeesResponse.getEmployees()) {
                    employeesList.add(employee);
                }
                // 保存到数据库
                Completable insertAll = BaseApplication.getDb().employeeDao().insertAll(employeesList);
                Log.d(TAG, "saveEmployees: 插入数据：" + employeesList.size() + "条");
                // RxJava处理Room数据存储
                CustomDisposable.addDisposable(insertAll, () -> Log.d(TAG, "saveEmployees: 热门天气数据保存成功"));
            });
    }

    /**
     * 保存员工大小图像
     */
    private void saveImageData(EmployeeResponse employeeResponse) {
//        Employee bean = employeeResponse.getEmployees().get(0);
//        // 保存到数据库
//        Completable insert = BaseApplication.getDb().imageDao().insertAll(
//            new Image(1, bean.getUrl(), bean.getUrlbase(), bean.getCopyright(),
//                      bean.getCopyrightlink(), bean.getTitle()));
        // RxJava处理Room数据存储
//        CustomDisposable.addDisposable(insert, () -> Log.d(TAG, "saveImageData: 必应数据保存成功"));
    }
}
