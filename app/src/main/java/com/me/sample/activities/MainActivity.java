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

        // 返回数据时更新ViewModel，ViewModel更新则xml更新 ???
        dataBinding.setViewModel(mMainActivityViewModel);

        mMainActivityViewModel.getEmployees();
        
        mAdapter = new RecyclerAdapter(new ArrayList<Employee>(0));
//        {
//                @Override
//                public void onPostClick(long id) {
//                    Toast.makeText(MainActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
//                }
//            });
        initRecyclerView();
        dataBinding.rv.setAdapter(mAdapter);
        dataBinding.rv.setHasFixedSize(true);
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
//        dataBinding.rv.addItemDecoration(itemDecoration);
        
        mMainActivityViewModel.failed.observe(this, failed -> dismissLoading());

        dataBinding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) { // 需要改写成网络申请刷新数据
                    mMainActivityViewModel.getEmployees();
                }
            });

        mMainActivityViewModel.mEmpList.observe(this, empListResponse -> {
                dataBinding.rv.setAdapter(new RecyclerAdapter(empListResponse));
                // initRecyclerView();
                // dataBinding.pb.setVisibility(INVISIBLE);
            });
    }

    private void loadData() {
        ApiService apiService = NetworkApi.createService(ApiService.class);
        apiService.getEmployees().enqueue(new Callback<List<Employee>>() {
                @Override
                    public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                    boolean valid = response.isSuccessful();
                    Log.d(TAG, "valid: " + valid);
                    // if (response.isSuccessful()) {
                    if (valid) {
                       mAdapter.updateAnswers(response.body());
//                        employees.setValue(response.body());
                        Log.d("MainActivity", "posts loaded from API");
                    } else {
                        int statusCode  = response.code();
                        // handle request errors depending on status code
                    }
                }

                @Override
                    public void onFailure(Call<List<Employee>> call, Throwable t) {
                    // showErrorMessage();
                    Log.d("MainActivity", "error loading from API");
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