package com.me.sample.activities;

import static android.view.View.INVISIBLE;
import android.os.Bundle;

import com.me.sample.R;
import com.me.sample.adapters.RecyclerAdapter;
import com.me.sample.databinding.ActivityMainBinding;
import com.me.sample.model.Employee;
import com.me.sample.network.ApiService;
import com.me.sample.network.NetworkApi;
import com.me.sample.viewmodels.MainActivityViewModel;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity"; 

    private ActivityMainBinding dataBinding;
    private MainActivityViewModel mMainActivityViewModel;
    private RecyclerAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // showLoading();

        // 数据绑定视图
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

// OkHttp Call for fetching data
        mMainActivityViewModel.getEmployees();
        
// 应该是不需要每次都重新设置viewModel，也不需要每次都重启RecyclerView
        // 返回数据时更新ViewModel，ViewModel更新则xml更新 ???
        dataBinding.setViewModel(mMainActivityViewModel);
        initRecyclerView(); 
// 感觉这里每次都New 一个新的 RecyclerAdapter也不是很好，暂时用不优雅的公用API的方式重置数据               
        mAdapter = new RecyclerAdapter(new ArrayList<Employee>(0));
        dataBinding.rv.setAdapter(mAdapter);
        dataBinding.rv.setHasFixedSize(true);

        mMainActivityViewModel.mEmpList.observe(this, empListResponse -> {
                // dataBinding.rv.setAdapter(new RecyclerAdapter(empListResponse.getEmployees()));
                mAdapter.updateEmpList(empListResponse.getEmployees());

//                dataBinding.pb.setVisibility(INVISIBLE);
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
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this); // 就用纵向排列的就可以了
        dataBinding.rv.setLayoutManager(linearLayoutManager);
    }
}