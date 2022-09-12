package com.me.sample.activities;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import com.me.sample.R;
import com.me.sample.adapters.RecyclerAdapter;
import com.me.sample.databinding.ActivityMainBinding;
import com.me.sample.model.Employee;
import com.me.sample.model.EmployeeResponse;
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
import retrofit2.http.HEAD;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity"; 

    private ActivityMainBinding dataBinding;
    private MainActivityViewModel mMainActivityViewModel;
    private RecyclerAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // UI界面屏幕显示，优先处理，不能留白
        showLoading();

        // 数据绑定视图
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
// OkHttp Call for fetching data：网络申请有延迟，希望早点儿下发请求
        mMainActivityViewModel.getEmployees();

// 应该是不需要每次都重新设置viewModel，也不需要每次都重启RecyclerView
        // 返回数据时更新ViewModel，ViewModel更新则xml更新 ???
        dataBinding.setViewModel(mMainActivityViewModel);
        initRecyclerView(); 

// 感觉这里每次都New 一个新的 RecyclerAdapter也不是很好，暂时用不优雅的公用API的方式重置数据               
        // mAdapter = new RecyclerAdapter(new ArrayList<Employee>(0));
        // dataBinding.rv.setAdapter(mAdapter);
        // dataBinding.rv.setHasFixedSize(true);
        mMainActivityViewModel.mEmpList.observe(this, empListResponse -> {
                // if (empListResponse == null || empListResponse.getEmployees() == null) {
                //     Toast toast = Toast.makeText(this, "The Employee list contains INVALID DATA, invalidated to be empty.", Toast.LENGTH_LONG);
                //     toast.show();
                // } else if (empListResponse.getEmployees().size() == 0) {
                // if (empListResponse == null || empListResponse.getEmployees() == null || empListResponse.getEmployees().size() == 0) {
                if (empListResponse.getEmployees() == null || empListResponse.getEmployees().size() == 0 || !isValidData(empListResponse)) {
                    Toast toast = Toast.makeText(this, "The Employee list is Empty or contained invalide data.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    dataBinding.rv.setAdapter(new RecyclerAdapter(empListResponse.getEmployees()));
                    // mAdapter.updateEmpList(empListResponse.getEmployees());
                }
                dismissLoading();
            });
        
        mMainActivityViewModel.failed.observe(this, failed -> dismissLoading());

        dataBinding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) { // 网络申请刷新数据: Ui Event向viewModel下传回调
                    mMainActivityViewModel.getEmployees();
                }
            });
    }

    private boolean isValidData(EmployeeResponse l) {
        if (l.getEmployees() == null) return false;
        Log.d(TAG, "isValidData() ");
        for (Employee e : l.getEmployees()) {
            if (!isValidEmployee(e)) 
                return false;
        }
        return true;
    }
    private boolean isValidEmployee(Employee e) {
        // e.toString();
        return e.getUuid() != null && e.getFullName() != null && e.getEmailAddress() != null
            && e.getTeam() != null && e.getEmployeeType() != null;
    }
    
    private void initRecyclerView(){
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this); 
        dataBinding.rv.setLayoutManager(linearLayoutManager);
    }
}