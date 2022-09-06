package com.me.sample;

import androidx.lifecycle.ViewModel;

import com.me.sample.db.Employee;

public class MainActivityViewModel extends ViewModel {
    private final String TAG = "MainActivityViewModel";

    private MutableLiveData<List<Employee>> mEmpList;

    private boolean isRefreshing;
    public boolean getIsRefreshing() {
        return isRefreshing;
    }
    public void setIsRefreshing(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
        // 如果一点击就要刷新，那么是否需要监听，刷新结束之后，重新设置为false呢，是应该的，感觉这里把逻辑弄复杂了
    }

    public void refreshData() { // GET
        // 注释1：动态获取IWanAndroidService对象, 这里可以在应用启动的某个地方初始化，并优化启动速度与效率，是否需要屏纸等还是用进展圈
        // IWanAndroidService service = NetworkApi.getInstance().initRetrofit().create(IWanAndroidService.class);
        // // 注释2：网络请求
        // service.homeBanner().enqueue(new Callback<ResponseData<List<HomeBanner>>>() {
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
