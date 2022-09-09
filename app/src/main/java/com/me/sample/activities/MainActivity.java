package com.me.sample.activities;

import static android.view.View.INVISIBLE;
import android.os.Bundle;

import com.me.sample.R;
import com.me.sample.adapters.RecyclerAdapter;
import com.me.sample.databinding.ActivityMainBinding;
import com.me.sample.viewmodels.MainActivityViewModel;

import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity"; 

    private ActivityMainBinding dataBinding;
    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // showLoading();

        // 数据绑定视图
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        initRecyclerView();

        mMainActivityViewModel.getEmployees();
        mMainActivityViewModel.mEmpList.observe(this, empListResponse -> {
                // 返回数据时更新ViewModel，ViewModel更新则xml更新
                dataBinding.setViewModel(mMainActivityViewModel);
                dataBinding.rv.setAdapter(new RecyclerAdapter(empListResponse.getEmployees()));
                dataBinding.pb.setVisibility(INVISIBLE);
            });
        mMainActivityViewModel.failed.observe(this, failed -> dismissLoading());
        
        dataBinding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) { // 需要改写成网络申请刷新数据
                    mMainActivityViewModel.getEmployees();
                }
            });
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView() ");
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this); // 就用纵向排列的就可以了
        Log.d(TAG, "(dataBinding.rv == null): " + (dataBinding.rv == null));
        dataBinding.rv.setLayoutManager(linearLayoutManager);
    }
}