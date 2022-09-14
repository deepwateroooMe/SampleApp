package com.me.sample.activities;

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

        // 以Activity为例，在Activity中使用bindToLifecycle()方法，完成Observable发布的事件和当前的组件绑定，实现生命周期同步。
        // 从而实现当前组件生命周期结束时，自动取消对Observable订阅
        // 当执行onDestory()时， 自动解除订阅
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
        
        // if (savedInstanceState != null) {
        // }
        // UI界面屏幕显示，优先处理，不能留白
        showLoading();

        // 数据绑定视图
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        dataBinding.setLifecycleOwner(this); // 绑定生命周期 ？？？ 怎么用呢？

        mMainActivityViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(MainActivityViewModel.class);
        mMainActivityViewModel.init();
// OkHttp Call for fetching data：网络申请有延迟，希望早点儿下发请求
        // mMainActivityViewModel.getEmployees();

// 应该是不需要每次都重新设置viewModel，也不需要每次都重启RecyclerView
        // 返回数据时更新ViewModel，ViewModel更新则xml更新 
        dataBinding.setViewModel(mMainActivityViewModel);
        initRecyclerView(); 

// 感觉这里每次都New 一个新的 RecyclerAdapter也不是很好，暂时用不优雅的公用API的方式重置数据；
// 因为涉及几个不同状态的切换，所以还是移到里面，应该差别也不是很大               
        mMainActivityViewModel.mEmpList.observe((LifecycleOwner) this, empListResponse -> {
                // 觉得这里的数据分类的各要的办法：是在ViewModel中定义三个不同的状态，直接传三个最简单的状态到视图层，但是暂时先这样
                if (empListResponse.getEmployees() == null || empListResponse.getEmployees().size() == 0 || !isValidData(empListResponse)) {
                    Toast toast = Toast.makeText(this, "The Employee list is Empty or contained invalide data.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    dataBinding.rv.setAdapter(new RecyclerAdapter(empListResponse.getEmployees()));
                    // mAdapter.updateEmpList(empListResponse.getEmployees());
                }
                dismissLoading();
            });
        
        mMainActivityViewModel.failed.observe((LifecycleOwner) this, failed -> dismissLoading());

        dataBinding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) { // 网络申请刷新数据: Ui Event向viewModel下传回调
                    mMainActivityViewModel.getEmployees();
                }
            });
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
        // 与上面onCreate()中的方法，两者选择一个即可。问题是：两种方法，有区别吗，区别是什么呢？
        // Observable.interval(1, TimeUnit.SECONDS)
        //     .doOnDispose(new Action() {
        //             @Override
        //             public void run() throws Exception {
        //                 Log.i(TAG, "Unsubscribing subscription from onResume()");
        //             }
        //         })
        //     //bindUntilEvent()，内部传入指定生命周期参数
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
    
// onRestoreInstanceState()会在onStart()和onResume()之间执行或者在onCreate()方法中判断
    // 只有在activity销毁重建的时候,才会调用
    @Override
        protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState() ");
    }
 
    // 会在 onStop()方法调用之前 调用该方法,保存当前状态
    // 当用户主动销毁activity，如按back键，或直接执行finish(),不会执行
    // 遇到意外情况（内存不足;用户直接按home键）由系统直接销毁一个Activity时，就会调用
    @Override
        public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG, "onSaveInstanceState() ");
// 该保存哪些数据呢？如何恢复呢？怎么模拟测试内存不足的情况？
    }

   // @Override // 因为这里横竖屏的布局完全一样，所以可以不必覆写这个方法
   //     public void onConfigurationChanged(Configuration newConfig) {
   //     super.onConfigurationChanged(newConfig);
   //     // Checks the orientation of the screen
   //     if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) 
   //         Toast.makeText(this, "横屏模式", Toast.LENGTH_SHORT).show();
   //     else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
   //         Toast.makeText(this, "竖屏模式", Toast.LENGTH_SHORT).show();
   // }

    @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState()");
// 保存数据： 链表数据，三种状态，图片(我应该是可以不用管的)
        // savedInstanceState.putInt();
    }
    
    // 这部分对数据的处理，更希望放到ViewModel中去定义出三种不同的状态，暂时放这里
    private boolean isValidData(EmployeeResponse l) {
        if (l.getEmployees() == null) return false;
        for (Employee e : l.getEmployees()) {
            if (!isValidEmployee(e)) 
                return false;
        }
        return true;
    }
    private boolean isValidEmployee(Employee e) {
        return e.getUuid() != null && e.getFullName() != null && e.getEmailAddress() != null
            && e.getTeam() != null && e.getEmployeeType() != null;
    }
    
    private void initRecyclerView(){
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this); 
        dataBinding.rv.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}