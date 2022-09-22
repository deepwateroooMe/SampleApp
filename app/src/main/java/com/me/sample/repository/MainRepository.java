package com.me.sample.repository;

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
import com.me.sample.utils.MVUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@SuppressLint("CheckResult")
public class MainRepository {
    private static final String TAG = MainRepository.class.getSimpleName();
    private static MVUtils mvUtils;

    final MutableLiveData<EmployeeResponse> employees = new MutableLiveData<>();
    final MutableLiveData<List<Image>> imgs = new MutableLiveData<>();
    public final MutableLiveData<String> failed = new MutableLiveData<>();

//    @Inject
    // public MainRepository() { }
    private static MainRepository instance;
    public static MainRepository getInstance() {
        if (instance == null)
            instance = new MainRepository();

        return instance;
    }
    
    public MutableLiveData<EmployeeResponse> getEmployees() {
        Log.d(TAG, "getEmployees() ");
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
    
    public MutableLiveData<EmployeeResponse> retrieveEmployees() {
        Log.d(TAG, "retrieveEmployees() ");
        EmployeeResponse emp = new EmployeeResponse();
        List<Employee> l = new ArrayList<>();
        Flowable<List<Employee>> listFlowable = BaseApplication.getDb().employeeDao().getAll();
        CustomDisposable.addDisposable(listFlowable, empList -> {
                for (Employee employee : empList) {
                    Employee one = new Employee(employee.getUuid(), employee.getFullName(),
                                                employee.getEmailAddress(), employee.getTeam(),
                                                employee.getEmployeeType(), employee.getPhotoUrlSmall());
                    l.add(one);
                }
                emp.setEmployees(l);
                employees.postValue(emp);
            });
        return employees;
    }

    private void saveEmployees(EmployeeResponse employeesResponse) {
        Log.d(TAG, "saveEmployees() ");
        Completable deleteAll = BaseApplication.getDb().employeeDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, () -> {
                List<Employee> employeesList = new ArrayList<>();
                for (Employee employee : employeesResponse.getEmployees()) 
                    employeesList.add(employee);
                Completable insertAll = BaseApplication.getDb().employeeDao().insertAll(employeesList);
                Log.d(TAG, "saveEmployees: Inseert Employees list data Succeed：" + employeesList.size() + "items");
                CustomDisposable.addDisposable(insertAll, () -> Log.d(TAG, "saveEmployees: Employees List data Succeed："));
            });
        saveImageData(employeesResponse); 
    }

    private void saveImageData(EmployeeResponse employeeResponse) {
        Log.d(TAG, "saveImageData() ");
        Completable deleteAll = BaseApplication.getDb().imageDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, () -> {
                List<Image> l = new ArrayList<>();
                for (Employee employee : employeeResponse.getEmployees())
                    l.add(new Image(employee.getFullName(), employee.getPhotoUrlSmall()));
                Completable insertAll = BaseApplication.getDb().imageDao().insertAll(l);
                CustomDisposable.addDisposable(insertAll, () -> Log.d(TAG, "saveImageData: Inseerted employee Image Succeed"));
            });
    }
}
