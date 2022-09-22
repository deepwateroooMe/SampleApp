package com.me.sample.activities;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.RequiresApi;

import com.me.sample.application.BaseApplication;
import com.me.sample.utils.PermissionUtils;
import com.me.sample.view.dialog.LoadingDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class BaseActivity extends RxAppCompatActivity {
    private final String TAG = "BaseActivity";
    
    protected BaseActivity context;
    private LoadingDialog loadingDialog;

    protected static final int SELECT_PHOTO_CODE = 2000;

    protected static final int TAKE_PHOTO_CODE = 2001;

    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        Log.d(TAG, "onCreate() ");
        BaseApplication.getActivityManager().addActivity(this);
        PermissionUtils.getInstance();
    }

    protected void showMsg(CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongMsg(CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    protected void jumpActivity(final Class<?> clazz) {
        startActivity(new Intent(context, clazz));
    }

    protected void jumpActivityFinish(final Class<?> clazz) {
        startActivity(new Intent(context, clazz));
        finish();
    }

    protected void back(Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    protected void backAndFinish(Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    protected void setStatusBar(boolean dark) {
        View decor = getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    protected void exitTheProgram() {
        BaseApplication.getActivityManager().finishAllActivity();
    }

    protected void showLoading() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }

    protected void showLoading(boolean isClose) {
        loadingDialog = new LoadingDialog(this, isClose);
        loadingDialog.show();
    }

    protected void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    protected boolean isAndroid11() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    protected boolean isAndroid10() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    protected boolean isAndroid7() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    protected boolean isAndroid6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    protected boolean isStorageManager() {
        return Environment.isExternalStorageManager();
    }

    protected boolean hasPermission(String permissionName) {
        return PermissionUtils.hasPermission(this, permissionName);
    }

    protected void requestPermission(String permissionName) {
        PermissionUtils.requestPermission(this, permissionName);
    }

    protected void requestManageExternalStorage(ActivityResultLauncher<Intent> intentActivityResultLauncher) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intentActivityResultLauncher.launch(intent);
    }

    protected boolean isNight() {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
        return uiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES;
    }
}
