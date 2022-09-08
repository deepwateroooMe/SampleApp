package com.me.sample;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.me.sample.adapters.RecyclerAdapter;
import com.me.sample.viewmodels.MainActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.me.sample.databinding.ActivityMainBinding;

import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity"; 

    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private MainActivityViewModel mMainActivityViewModel;

    //网络环境
    public static final String NETWORK_ENVIRONMENT = "network_environment";
    //当前网络环境
    private static String mCurrentNetworkEnvironment = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // // 这个后面对应的是几个不同fragment来实现不同测试环境的监听与回调，因为不是当前项目的重点，留下标记，改天有机会再实现测试一遍
        // // https://cloud.tencent.com/developer/article/1773165
        // setContentView(R.layout.activity_network_environment);

        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.rv);
        mProgressBar = findViewById(R.id.pb);

//         mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
//        mMainActivityViewModel.init();

        
        // //数据绑定视图
        // dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // //网络请求
        // mainViewModel.getBiying();
        // //返回数据时更新ViewModel，ViewModel更新则xml更新
        // mainViewModel.biying.observe(this, biYingImgResponse -> dataBinding.setViewModel(mainViewModel));

        
        // // 观察者模式：UI 观察数据的变化 
        // mMainActivityViewModel.getNicePlaces().observe(this, new Observer<List<Employee>>() {
        //         @Override
        //             public void onChanged(@Nullable List<Employee> nicePlaces) {
        //             mAdapter.notifyDataSetChanged();
        //         }
        //     });
        // // 这里先简单改写为请求刷新员工数据，下发请求到viewmodel
        // mMainActivityViewModel.getIsRefreshing().observe(this, new Observer<Boolean>() { // 是否正在刷新
        //         @Override
        //             public void onChanged(@Nullable Boolean aBoolean) {
        //             if (aBoolean){
        //                 showProgressBar();
        //             } else{
        //                 hideProgressBar();
        //                 // mRecyclerView.smoothScrollToPosition(mMainActivityViewModel.getNicePlaces().getValue().size()-1); // <<<<<<<<<< 
        //             }
        //         }
        //     });

        mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) { // 需要改写成网络申请刷新数据
                    mMainActivityViewModel.setIsRefreshing(true);
                }
            });
        initRecyclerView();
    }

    private void initRecyclerView(){
        mAdapter = new RecyclerAdapter(this, mMainActivityViewModel.getEmployeeList().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this); // 就用纵向排列的就可以了
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }
}