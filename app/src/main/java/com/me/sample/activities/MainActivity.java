package com.me.sample.activities;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import com.me.sample.R;
import com.me.sample.adapters.RecyclerAdapter;
import com.me.sample.databinding.ActivityMainBinding;
import com.me.sample.db.bean.Employee;
import com.me.sample.model.EmployeeResponse;
import com.me.sample.viewmodels.MainActivityViewModel;
import com.trello.rxlifecycle2.android.ActivityEvent;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

// @AndroidEntryPoint
public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity"; 

    private ActivityMainBinding dataBinding;
    private MainActivityViewModel mMainActivityViewModel;
    private RecyclerAdapter mAdapter;
    
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() ");

        Observable.interval(1, TimeUnit.SECONDS)
            .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i(TAG, "Unsubscribing subscription from onCreate()");
                    }
                })
            .compose(this.<Long>bindToLifecycle())
            .subscribe(new Consumer<Long>() {
                    @Override
                        public void accept(Long num) throws Exception {
                        Log.i(TAG, "Started in onCreate(), running until onDestory(): " + num);
                    }
                });

        showLoading();

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mMainActivityViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(MainActivityViewModel.class);
        mMainActivityViewModel.init(); 

        dataBinding.setViewModel(mMainActivityViewModel);
        initRecyclerView(); 

        if (savedInstanceState != null) {
            Log.d(TAG, "(savedInstanceState != null): " + (savedInstanceState != null));
            mMainActivityViewModel.retrieveEmployees();
        } else {
            Log.d(TAG, "(savedInstanceState == null): " + (savedInstanceState == null));
            mMainActivityViewModel.getEmployees();
        }
        
        mMainActivityViewModel.mEmpList.observe((LifecycleOwner) this, empListResponse -> {
                // if (empListResponse.getEmployees() == null) {
                //     dataBinding.rv.setVisibility(INVISIBLE);
                //     Toast toast = Toast.makeText(this, "The Employee list contains invalid data, invalidated list.", Toast.LENGTH_LONG);
                //     toast.show();
                // } else if (empListResponse.getEmployees().size() == 0) {
                //     dataBinding.rv.setVisibility(INVISIBLE);
                //     Toast toast = Toast.makeText(this, "The Employee list is Empty.", Toast.LENGTH_LONG);
                //     toast.show();
                if (empListResponse.getEmployees() == null || empListResponse.getEmployees().size() == 0 || !isValidData(empListResponse)) {
                    dataBinding.rv.setVisibility(INVISIBLE);
                    Toast toast = Toast.makeText(this, "The Employee list is Empty or contained invalide data.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    dataBinding.rv.setAdapter(new RecyclerAdapter(empListResponse.getEmployees()));
                    // mAdapter.updateEmpList(empListResponse.getEmployees());
                    dataBinding.rv.setVisibility(VISIBLE);
                }
                dismissLoading();
            });
        
        // mMainActivityViewModel.failed.observe((LifecycleOwner)this, failed -> dismissLoading());

        dataBinding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) { // 网络申请刷新数据: Ui Event向viewModel下传回调
                    mMainActivityViewModel.getEmployees();
                }
            });
    }

    private boolean isValidData(EmployeeResponse l) {
        // if (l.getEmployees() == null) return false;
        for (Employee e : l.getEmployees()) {
            if (!isValidEmployee(e)) {
                Log.d(TAG, "e.toString(): " + e.toString());
                return false;
            }
        }
        return true;
    }
    private boolean isValidEmployee(Employee e) {
        return e.getUuid() != null && e.getFullName() != null && e.getEmailAddress() != null
            && e.getTeam() != null && e.getEmployeeType() != null;
    }

    @Override
        public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        // super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG, "onSaveInstanceState() ");
    }

    @Override
        protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // super.onRestoreInstanceState(savedInstanceState); 
        Log.d(TAG, "onRestoreInstanceState() ");
    }

    @Override
        public void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() ");
    }
    @Override
        public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() ");
    }
    @Override
        public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() ");
    }
    @Override
        public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() ");
        // Observable.interval(1, TimeUnit.SECONDS)
        //     .doOnDispose(new Action() {
        //             @Override
        //             public void run() throws Exception {
        //                 Log.i(TAG, "Unsubscribing subscription from onResume()");
        //             }
        //         })
        //     // bindUntilEvent()，
        //     .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
        //     .subscribe(new Consumer<Long>() {
        //             @Override
        //                 public void accept(Long num) throws Exception {
        //                 Log.i(TAG, "Started in onResume(), running until in onDestroy(): " + num);
        //             }
        //         });
    }
    @Override
        public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() ");
    }
    @Override
        public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() ");
    }
    
   // @Override 
   //     public void onConfigurationChanged(Configuration newConfig) {
   //     super.onConfigurationChanged(newConfig);
   //     // Checks the orientation of the screen
   //     if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) 
   //         Toast.makeText(this, "横屏模式", Toast.LENGTH_SHORT).show();
   //     else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
   //         Toast.makeText(this, "竖屏模式", Toast.LENGTH_SHORT).show();
   // }
    
    private void initRecyclerView(){
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this); 
        dataBinding.rv.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}