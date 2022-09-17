package com.me.sample.repository;

import static com.me.sample.application.BaseApplication.getContext;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.me.sample.application.BaseApplication;
import com.me.sample.db.bean.Employee;
import com.me.sample.db.bean.Image;
import com.me.sample.model.EmployeeResponse;
import com.me.sample.network.ApiService;
import com.me.sample.network.BaseObserver;
import com.me.sample.network.NetworkApi;
import com.me.sample.network.utils.KLog;
import com.me.sample.utils.Constant;
import com.me.sample.utils.MVUtils;
import com.me.sample.utils.MVUtilsEntryPoint;

import java.util.ArrayList;
import java.util.List;

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
    private static MVUtils mvUtils;

    /**
     * 员工链表数据
     */
    final MutableLiveData<EmployeeResponse> employees = new MutableLiveData<>();
    final MutableLiveData<List<Image>> imgs = new MutableLiveData<>();
    public final MutableLiveData<String> failed = new MutableLiveData<>();

//    @Inject
    // public MainRepository() { }
    private static MainRepository instance;
    public static MainRepository getInstance() {
        if (instance == null)
            instance = new MainRepository();

        // // 获取mvUtils: 写在这里面，是否会逻辑混乱 ？
        // MVUtilsEntryPoint entryPoint =
        //     EntryPointAccessors.fromApplication(getContext(), MVUtilsEntryPoint.class);
        // mvUtils = entryPoint.getMVUtils();

        return instance;
    }
    
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
                        saveEmployees(empsResponse);
                    }
                    @Override
                        public void onFailure(Throwable e) {
                        KLog.e("Employees Error: " + e.toString());
                    }
                }));
        return employees;
    }
    
    /**
     * 从本地数据库获取员工链表
     */
// 提供一个数据库读取数据的公用方法供使用
    public MutableLiveData<EmployeeResponse> retrieveEmployees() {
        Log.d(TAG, "retrieveEmployees() ");
        EmployeeResponse emp = new EmployeeResponse();
        List<Employee> l = new ArrayList<>();
        Flowable<List<Employee>> listFlowable = BaseApplication.getDb().employeeDao().getAll();
        CustomDisposable.addDisposable(listFlowable, empList -> {
                for (Employee employee : empList) {
                    Employee one = new Employee();
                    one.setFullName(employee.getFullName());
                    one.setPhotoUrlSmall(employee.getPhotoUrlSmall());
                    one.setTeam(employee.getTeam());
                    l.add(one);
                }
                Log.d(TAG, "l.size(): " + l.size());
                emp.setEmployees(l);
                employees.postValue(emp);
            });
        return employees;
    }

/**
     * 保存员工链表数据
     */
    private void saveEmployees(EmployeeResponse employeesResponse) {
        // // 记录数据库里已经存有员工链表数据
        // mvUtils.put(Constant.HAS_LIST, true);
        Completable deleteAll = BaseApplication.getDb().employeeDao().deleteAll();
        
        CustomDisposable.addDisposable(deleteAll, () -> {
                Log.d(TAG, "saveEmployees: 删除员工链表数据成功");
                List<Employee> employeesList = new ArrayList<>();
                // 这里，不光是把每个员工加入链表；把把每个员工的头像也加入到头像的表格中去
                // 简单的办法是；把整个员工链表作为参数传给另一个方法
                for (Employee employee : employeesResponse.getEmployees()) 
                    employeesList.add(employee);
                // 保存到数据库
                Completable insertAll = BaseApplication.getDb().employeeDao().insertAll(employeesList);
                Log.d(TAG, "saveEmployees: 插入员工链表数据：" + employeesList.size() + "条");
                // RxJava处理Room数据存储
                CustomDisposable.addDisposable(insertAll, () -> Log.d(TAG, "saveEmployees: 员工数据保存成功"));
            });
        saveImageData(employeesResponse); // 同时保存员工头像数据
    }

    /**
     * 保存员工大小图像: 保存每个员工的头像图片，一次只能保存一个
     */
    private void saveImageData(EmployeeResponse employeeResponse) {
        // // 记录数据库里已经存有员工头像数据
        // mvUtils.put(Constant.HAS_IMGS, true);
        // 员工头像表：需要删除所有以前(上一次)所存过的员工头像数据；再一个一个地加进来
        // 需要删除所有以前(上一次)所存过的员工头像数据；再一个一个地加进来
        Completable deleteAll = BaseApplication.getDb().imageDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, () -> {
                Log.d(TAG, "saveImageData: 删除员工头像数据成功");
                List<Image> l = new ArrayList<>();
                for (Employee employee : employeeResponse.getEmployees())
                    l.add(new Image(employee.getFullName(), employee.getPhotoUrlSmall()));
                // 保存到数据库
                Completable insertAll = BaseApplication.getDb().imageDao().insertAll(l);
                Log.d(TAG, "saveImageData: 插入员工头像数据：" + l.size() + "条");
                // RxJava处理Room数据存储
                CustomDisposable.addDisposable(insertAll, () -> Log.d(TAG, "saveImageData: 员工头像数据保存成功"));
            });
    }
}
