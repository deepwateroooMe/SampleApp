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
    // 定义一个产状态来标记是否缓存有员工数据: 用于调整UI层的加载
    private MutableLiveData<Boolean> hasCachedList;
    
    public void init(){
        if (mEmpList != null){
            hasCachedList.setValue(true);
            return;
        }
        mRepo = MainRepository.getInstance();
        mEmpList = mRepo.getEmployees();
    }
    // public MainActivityViewModel(@NonNull Application application) {
    //     super(application);
    //     hasCachedList = false;
    //     // // 初始化数据
    //     // mEmployeeDatabase = AppDatabase.getInstance(application);
    // }

    // 刷新数据
    public LiveData<EmployeeResponse> fetchEmployeesList() { // 原理：以中介桥梁仓库为中转获取网络数据
        return mEmpList = mRepo.getEmployees();
    }

    // 是否有缓存数据
    public LiveData<Boolean> getHasCachedList() {
        return hasCachedList;
    }
    
    // 是否刷新员工链表数据
    public void setIsUpdating(boolean update) {
        mIsUpdating.setValue(update);
    }
    public LiveData<Boolean> getIsUpdating() {
        return mIsUpdating;
    }
    
    // public void refreshData() { // GET
    //     // 注释1：动态获取IWanAndroidService对象, 这里可以在应用启动的某个地方初始化，并优化启动速度与效率，是否需要屏纸等还是用进展圈
    //     // Apis service = NetworkApi.getInstance(). // 都是私有函数，要怎么调用呢？
    //     // IWanAndroidService service = NetworkApi.getInstance().initRetrofit().create(IWanAndroidService.class);
    //     // // 注释2：网络请求
    //     // service.homeBannerpp().enqueue(new Callback<ResponseData<List<HomeBanner>>>() {
    //     //         @Override
    //     //             public void onResponse(Call<ResponseData<List<HomeBanner>>> call, Response<ResponseData<List<HomeBanner>>> response) {
    //     //             if (response.body().getData() != null) {
    //     //                 MLog.e(response.body().getData().get(0).toString());
    //     //                 binding.loginTvContent.setText(response.body().getData().get(0).toString());
    //     //             }
    //     //         }
    //     //         @Override
    //     //             public void onFailure(Call<ResponseData<List<HomeBanner>>> call, Throwable t) {
    //     //             MLog.e(t.getMessage());
    //     //         }
    //     //     });
    // }
}
