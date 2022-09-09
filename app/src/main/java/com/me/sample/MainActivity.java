package com.me.sample;

import static android.view.View.VISIBLE;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.me.sample.adapters.RecyclerAdapter;
import com.me.sample.model.EmployeeResponse;
import com.me.sample.viewmodels.MainActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ProgressBar;

import io.reactivex.annotations.Nullable;

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

        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mMainActivityViewModel.init();
        
        // 观察者模式：UI 观察数据的变化 
        mMainActivityViewModel.fetchEmployeesList().observe(this, new Observer<EmployeeResponse>() {
                @Override
                    public void onChanged(@Nullable EmployeeResponse mEmpList) {
                    Log.d(TAG, "onChanged() ");
                    if (mEmpList != null) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        // mRecyclerView.setVisibility(VISIBLE);
                        initRecyclerView();
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });

        mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) { // 需要改写成网络申请刷新数据
                    mMainActivityViewModel.fetchEmployeesList();
                }
            });
        initRecyclerView();
    }

    private void initRecyclerView(){
        mAdapter = new RecyclerAdapter(this, mMainActivityViewModel.fetchEmployeesList().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this); // 就用纵向排列的就可以了
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(VISIBLE);
    }
    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }
}