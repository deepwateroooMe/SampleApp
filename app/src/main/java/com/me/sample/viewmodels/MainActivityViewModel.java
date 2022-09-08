package com.me.sample.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.me.sample.db.AppDatabase;
import com.me.sample.model.EmployeeResponse;
import com.me.sample.repository.MainRepository;

import io.reactivex.annotations.NonNull;

public class MainActivityViewModel extends ViewModel {
    private final String TAG = "MainActivityViewModel";

    private AppDatabase mEmployeeDatabase; // need to have an intance reference here in viewModel 是哪里初始化比较好呢 ？
    
    private LiveData<EmployeeResponse> mEmpList;

    // 定义一个产状态来标记是否缓存有员工数据: 用于调整UI层的加载
    private LiveData<Boolean> hasCachedList;
    
    // private boolean isRefreshing;
    // public boolean getIsRefreshing() {
    //     return isRefreshing;
    // }
    // public void setIsRefreshing(boolean isRefreshing) {
    //     this.isRefreshing = isRefreshing;
    //     // 如果一点击就要刷新，那么是否需要监听，刷新结束之后，重新设置为false呢，是应该的，感觉这里把逻辑弄复杂了
    // }

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        // 初始化数据
        mEmployeeDatabase = AppDatabase.getInstance(application);
//        mEmpList = mEmployeeDatabase.getEmployeeDao().getEmployees(); // 这里的返回数据类型要再检查定义一下
    }

    public void fetchEmployeesList() { // 原理：以中介桥梁仓库为中转获取网络数据
        mEmpList = new MainRepository.getEmployees();
    }

    public void refreshData() { // GET
        // 注释1：动态获取IWanAndroidService对象, 这里可以在应用启动的某个地方初始化，并优化启动速度与效率，是否需要屏纸等还是用进展圈
        // Apis service = NetworkApi.getInstance(). // 都是私有函数，要怎么调用呢？
        // IWanAndroidService service = NetworkApi.getInstance().initRetrofit().create(IWanAndroidService.class);
        // // 注释2：网络请求
        // service.homeBannerpp().enqueue(new Callback<ResponseData<List<HomeBanner>>>() {
        //         @Override
        //             public void onResponse(Call<ResponseData<List<HomeBanner>>> call, Response<ResponseData<List<HomeBanner>>> response) {
        //             if (response.body().getData() != null) {
        //                 MLog.e(response.body().getData().get(0).toString());
        //                 binding.loginTvContent.setText(response.body().getData().get(0).toString());
        //             }
        //         }
        //         @Override
        //             public void onFailure(Call<ResponseData<List<HomeBanner>>> call, Throwable t) {
        //             MLog.e(t.getMessage());
        //         }
        //     });
    }
}
