package com.me.sample;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.me.sample.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

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
        // setContentView(R.layout.activity_network_environment);


        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);
        
        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mMainActivityViewModel.init();

        // 观察者模式：UI 观察数据的变化 
        mMainActivityViewModel.getNicePlaces().observe(this, new Observer<List<NicePlace>>() {
                @Override
                    public void onChanged(@Nullable List<NicePlace> nicePlaces) {
                    mAdapter.notifyDataSetChanged();
                }
            });

        // 这里先简单改写为请求刷新员工数据，下发请求到viewmodel
        mMainActivityViewModel.getIsRefreshing().observe(this, new Observer<Boolean>() { // 是否正在刷新
                @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean){
                        showProgressBar();
                    } else{
                        hideProgressBar();
                        // mRecyclerView.smoothScrollToPosition(mMainActivityViewModel.getNicePlaces().getValue().size()-1); // <<<<<<<<<< 
                    }
                }
            });

        mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) { // 需要改写成网络申请刷新数据
                    // mMainActivityViewModel.addNewValue(
                    //     new NicePlace(
                    //         "https://i.imgur.com/ZcLLrkY.jpg",
                    //         "Washington"
                    //         )
                    //     );
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